let companyMap = null;
let companyMarker = null;

/* ===========================
   INIT
=========================== */
function initCompanyProfile() {
  // 🔥 reset map khi SPA load lại page
  if (companyMap) {
    companyMap.remove();
    companyMap = null;
    companyMarker = null;
  }

  loadCompanyProfile();

  const btnSave = document.getElementById("btnSaveCompany");
  if (btnSave) btnSave.onclick = submitCompanyProfile;

  // Nhập địa chỉ → map tự nhảy
  const companyAddressInput = document.getElementById("companyAddress");
  if (companyAddressInput) {
    companyAddressInput.addEventListener("blur", jumpMapByCompanyAddress);
  }
  initImagePreview();
   initImagePicker();
}

/* ===========================
   LOAD COMPANY
=========================== */
function loadCompanyProfile() {
  fetch(`${API_BASE}/employer/me`, {
    headers: {
      "Content-Type": "application/json",
      ...authHeader()
    }
  })
    .then(res => {
      if (!res.ok) throw new Error();
      return res.json();
    })
    .then(data => {
      fillCompanyProfile(data);
      initCompanyMap(data.latitude, data.longitude);
    })
    .catch(err => console.error("Load company error", err));
}

/* ===========================
   FILL DATA
=========================== */
function fillCompanyProfile(c) {
  setValue("employerId", c.id);
  setValue("companyName", c.companyName);
  setValue("companyEmail", c.companyEmail);
  setValue("companySize", c.companySize);
  setValue("companyAddress", c.companyAddress);
  setValue("companyDescription", c.companyDescription);
  setValue("latitude", c.latitude);
  setValue("longitude", c.longitude);

  setText("companyNamePreview", c.companyName);
  setText("companyEmailPreview", c.companyEmail);
  setText("companySizePreview", c.companySize);
  setText("companyAddressPreview", c.companyAddress);

  const logo = document.getElementById("logoPreview");
  if (logo) logo.src = c.companyLogo || "";

  const license = document.getElementById("licensePreview");
  if (license && c.licenseDocument) {
    license.href = c.licenseDocument;
    license.classList.remove("d-none");
  }
}

/* ===========================
   LEAFLET MAP (FIX SPA)
=========================== */
function initCompanyMap(lat, lng) {
  if (lat == null || lng == null) {
    lat = 10.7769;
    lng = 106.7009;
  }

  if (companyMap) {
    setTimeout(() => {
      companyMap.invalidateSize();
      companyMap.setView([lat, lng], 15);
      companyMarker.setLatLng([lat, lng]);
    }, 200);
    return;
  }

  companyMap = L.map("companyMap", {
    center: [lat, lng],
    zoom: 15,
    dragging: false,
    scrollWheelZoom: false,
    doubleClickZoom: false,
    zoomControl: false
  });

  L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    attribution: "© OpenStreetMap"
  }).addTo(companyMap);

  companyMarker = L.marker([lat, lng], {
    interactive: false
  }).addTo(companyMap);

  // 🔥 BẮT BUỘC cho SPA
  setTimeout(() => {
    companyMap.invalidateSize();
  }, 300);
}

/* ===========================
   ADDRESS → MAP JUMP
=========================== */
function jumpMapByCompanyAddress() {
  const address = document.getElementById("companyAddress").value.trim();
  if (!address) return;

  fetch(
    `https://nominatim.openstreetmap.org/search?format=json&limit=1&q=${encodeURIComponent(address)}`,
    { headers: { Accept: "application/json" } }
  )
    .then(res => res.json())
    .then(data => {
      if (!data || data.length === 0) return;

      const lat = parseFloat(data[0].lat);
      const lng = parseFloat(data[0].lon);

      if (!companyMap) {
        initCompanyMap(lat, lng);
      } else {
        companyMap.setView([lat, lng], 16);
        companyMarker.setLatLng([lat, lng]);
      }

      setValue("latitude", lat);
      setValue("longitude", lng);
    })
    .catch(err => console.error("Map jump error", err));
}

/* ===========================
   SUBMIT UPDATE
=========================== */
//function submitCompanyProfile() {
//  const employerId = document.getElementById("employerId").value;
//
//  const employer = {
//    companyName: document.getElementById("companyName").value,
//    companyEmail: document.getElementById("companyEmail").value,
//    companySize: document.getElementById("companySize").value,
//    companyAddress: document.getElementById("companyAddress").value,
//    companyDescription: document.getElementById("companyDescription").value,
//    latitude: document.getElementById("latitude").value,
//    longitude: document.getElementById("longitude").value
//  };
//
//  const formData = new FormData();
//  formData.append(
//    "employer",
//    new Blob([JSON.stringify(employer)], { type: "application/json" })
//  );
//
//  const logoFile = document.getElementById("logoInput")?.files[0];
//  if (logoFile) formData.append("logo", logoFile);
//
//  const licenseFile = document.getElementById("licenseInput")?.files[0];
//  if (licenseFile) formData.append("license", licenseFile);
//
//  fetch(`${API_BASE}/employer/${employerId}`, {
//    method: "PUT",
//    headers: { ...authHeader() },
//    body: formData
//  })
//    .then(res => {
//      if (!res.ok) throw new Error();
//      return res.json();
//    })
//    .then(data => {
//      showCompanyMsg("Cập nhật hồ sơ công ty thành công", "success");
//      fillCompanyProfile(data);
//      initCompanyMap(data.latitude, data.longitude);
//    })
//    .catch(() => {
//      showCompanyMsg("Cập nhật thất bại", "error");
//    });
//}


function submitCompanyProfile() {
  const employerId = document.getElementById("employerId").value;

  // Chuyển latitude/longitude sang number để backend nhận
  const employer = {
    companyName: document.getElementById("companyName").value.trim(),
    companyEmail: document.getElementById("companyEmail").value.trim(),
    companySize: document.getElementById("companySize").value.trim(),
    companyAddress: document.getElementById("companyAddress").value.trim(),
    companyDescription: document.getElementById("companyDescription").value.trim(),
    latitude: parseFloat(document.getElementById("latitude").value) || 0,
    longitude: parseFloat(document.getElementById("longitude").value) || 0
  };

  // 🔹 gửi JSON trực tiếp, không dùng FormData
  fetch(`${API_BASE}/employer/${employerId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json", // bắt buộc để backend hiểu JSON
      ...authHeader() // chỉ gửi Authorization
    },
    body: JSON.stringify(employer)
  })
    .then(async res => {
      if (!res.ok) {
        let errMsg = "Cập nhật thất bại";
        try {
          const errData = await res.json();
          if (errData?.message) errMsg = errData.message;
        } catch (_) {}
        throw new Error(errMsg);
      }
      return res.json();
    })
    .then(data => {
      showCompanyMsg("Cập nhật hồ sơ công ty thành công", "success");
      fillCompanyProfile(data);
      initCompanyMap(data.latitude, data.longitude);
    })
    .catch(err => {
      showCompanyMsg(err.message, "error");
    });
}

/* ===========================
   UI HELPERS
=========================== */
function showCompanyMsg(msg, type) {
  const el = document.getElementById("companyMsg");
  if (!el) return;
  el.textContent = msg;
  el.className = type === "success" ? "success" : "error";
}

function setValue(id, value) {
  const el = document.getElementById(id);
  if (el) el.value = value ?? "";
}

function setText(id, value) {
  const el = document.getElementById(id);
  if (el) el.textContent = value || "—";
}
    function initImagePreview() {
      const logoInput = document.getElementById("logoInput");
      const logoPreview = document.getElementById("logoPreview");

      if (logoInput && logoPreview) {
        logoInput.addEventListener("change", () => {
          const file = logoInput.files[0];
          if (!file) return;

          const reader = new FileReader();
          reader.onload = e => {
            logoPreview.src = e.target.result;
          };
          reader.readAsDataURL(file);
        });
      }

      const licenseInput = document.getElementById("licenseInput");
      const licensePreview = document.getElementById("licensePreview");

      if (licenseInput && licensePreview) {
        licenseInput.addEventListener("change", () => {
          const file = licenseInput.files[0];
          if (!file) return;

          licensePreview.href = URL.createObjectURL(file);
          licensePreview.classList.remove("d-none");
          licensePreview.textContent = "Xem giấy phép mới";
        });
      }
    }
function initImagePicker() {
  const btnChangeLogo = document.getElementById("btnChangeLogo");
  const logoInput = document.getElementById("logoInput");

  if (btnChangeLogo && logoInput) {
    btnChangeLogo.addEventListener("click", () => {
      logoInput.click(); // 🔥 MỞ FILE PICKER
    });
  }
}
