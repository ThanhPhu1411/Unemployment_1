///* =========================
//   GLOBALS (SPA SAFE)
//========================= */
//window.companyId = window.companyId || null;
//window.companyMap = window.companyMap || null;
//
///* =========================
//   INIT
//========================= */
//window.initAdminCompanyDetail = function () {
//  console.log("✅ initAdminCompanyDetail RUNNING...");
//    if (!document.getElementById("companyDetailTitle")) return;
//
//    if (!window.companyId) {
//        alert("Không tìm thấy công ty");
//        return;
//    }
//
//    loadCompanyDetail();
//};
//
//
//
///* =========================
//   LOAD DETAIL
//========================= */
//async function loadCompanyDetail() {
//    try {
//        console.log("🔥 Fetch companyId:", window.companyId);
//
//        const res = await fetch(`${API_BASE}/employer/${window.companyId}`, {
//            headers: authHeader()
//        });
//
//        console.log("🔥 Response status:", res.status);
//
//        if (!res.ok) {
//            const txt = await res.text();
//            console.log("❌ Response body:", txt);
//            throw new Error("Không thể tải chi tiết");
//        }
//
//        const c = await res.json();
//        console.log("✅ Company data:", c);
//
//        renderDetail(c);
//
//    } catch (e) {
//        console.error("❌ loadCompanyDetail error:", e);
//        alert("Lỗi tải chi tiết công ty");
//    }
//}
//
///* =========================
//   RENDER DETAIL
//========================= */
//function renderDetail(c) {
//
//    console.log("✅ renderDetail called:", c);
//
//    document.getElementById("detailCompanyName").innerText = c.companyName ?? "—";
//    document.getElementById("detailCompanyName2").innerText = c.companyName ?? "—";
//    document.getElementById("detailEmail").innerText = c.companyEmail ?? "—";
//    document.getElementById("detailSize").innerText = c.companySize ?? "—";
//    document.getElementById("detailAddress").innerText = c.companyAddress ?? "—";
//    document.getElementById("detailDescription").innerText = c.companyDescription ?? "—";
//    document.getElementById("detailLogo").src =
//        c.companyLogo || "/uploads/images/default-company.png";
//
//    renderStatus(c.status);
//    setupButtons(c.status);
//
//    if (c.licenseDocument) {
//        document.getElementById("detailLicenseLink").href = c.licenseDocument;
//    }
//
//    // Map
//    try {
//        renderMap(c.latitude, c.longitude, c.companyName);
//    } catch (err) {
//        console.warn("⚠️ renderMap lỗi:", err);
//    }
//}
//
///* =========================
//   STATUS
//========================= */
//function renderStatus(status) {
//    const badge = document.getElementById("detailStatus");
//    const text = document.getElementById("detailStatusText");
//
//    badge.className = "status-badge";
//
//    if (status === "Chờ duyệt") {
//        badge.classList.add("status-pending");
//        badge.innerText = "Chờ duyệt";
//    } else if (status === "Đã duyệt") {
//        badge.classList.add("status-approved");
//        badge.innerText = " Đã duyệt";
//    } else if (status === "Từ chối") {
//        badge.classList.add("status-rejected");
//        badge.innerText = " Từ chối";
//    }
//
//    text.innerText = badge.innerText;
//}
//
///* =========================
//   BUTTONS
//========================= */
//function setupButtons(status) {
//    const approveBtn = document.getElementById("btnApproveCompany");
//    const rejectBtn = document.getElementById("btnRejectCompany");
//
//    if (!approveBtn || !rejectBtn) return;
//
//    approveBtn.onclick = approveCompany;
//    rejectBtn.onclick = rejectCompany;
//
//    if (status !== "Chờ duyệt") {
//        approveBtn.style.display = "none";
//        rejectBtn.style.display = "none";
//    }
//}
//
//async function approveCompany() {
//    if (!confirm("Duyệt công ty này?")) return;
//
//    await fetch(`${API_BASE}/employer/${window.companyId}/approve`, {
//        method: "PUT",
//        headers: authHeader()
//    });
//
//    alert("Đã duyệt công ty");
//    loadCompanyDetail();
//}
//
//async function rejectCompany() {
//    if (!confirm("Từ chối công ty này?")) return;
//
//    await fetch(`${API_BASE}/employer/${window.companyId}/reject`, {
//        method: "PUT",
//        headers: authHeader()
//    });
//
//    alert("Đã từ chối công ty");
//    loadCompanyDetail();
//}
//
///* =========================
//   MAP (LEAFLET)
//========================= */
//function renderMap(lat, lng, name) {
//    const mapEl = document.getElementById("detailCompanyMap");
//
//    if (!lat || !lng) {
//        mapEl.innerHTML = "<p style='padding:10px'>Chưa có vị trí bản đồ</p>";
//        return;
//    }
//
//    if (window.companyMap) {
//        window.companyMap.remove();
//        window.companyMap = null;
//    }
//
//    window.companyMap = L.map("detailCompanyMap").setView([lat, lng], 15);
//
//    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
//        attribution: "&copy; OpenStreetMap"
//    }).addTo(window.companyMap);
//
//    L.marker([lat, lng])
//        .addTo(window.companyMap)
//        .bindPopup(`<b>${name}</b>`)
//        .openPopup();
//
//    setTimeout(() => window.companyMap.invalidateSize(), 200);
//}
//
///* =========================
//   NAV
//========================= */
//function goBackCompany() {
//    loadPage("/pages/admin-company.html");
//}

/* =========================
   GLOBALS
========================= */
window.companyMap = window.companyMap || null;

// Lấy companyId từ URL nếu có
const urlParams = new URLSearchParams(window.location.search);
window.companyId = urlParams.get("id") || window.companyId;

if (!window.companyId) {
    alert("Không tìm thấy công ty");
}

/* =========================
   INIT
========================= */
window.initAdminCompanyDetail = function () {
    console.log(" initAdminCompanyDetail RUNNING...", window.companyId);
    if (!window.companyId) return;
    loadCompanyDetail();
};

/* =========================
   LOAD DETAIL
========================= */
async function loadCompanyDetail() {
    try {
        console.log(" Fetch companyId:", window.companyId);

        const res = await fetch(`${API_BASE}/employer/${window.companyId}`, {
            headers: authHeader()
        });

        console.log(" Response status:", res.status);

        if (!res.ok) {
            const txt = await res.text();
            console.log(" Response body:", txt);
            throw new Error("Không thể tải chi tiết công ty");
        }

        const c = await res.json();
        console.log(" Company data:", c);

        renderDetail(c);

    } catch (e) {
        console.error(" loadCompanyDetail error:", e);
        alert("Lỗi tải chi tiết công ty");
    }
}

/* =========================
   RENDER DETAIL
========================= */
function renderDetail(c) {
    if (!c) return;

    // Thông tin cơ bản
    document.getElementById("detailCompanyName").innerText = c.companyName ?? "—";
    document.getElementById("detailCompanyName2").innerText = c.companyName ?? "—";
    document.getElementById("detailEmail").innerText = c.companyEmail ?? "—";
    document.getElementById("detailSize").innerText = c.companySize ?? "—";
    document.getElementById("detailAddress").innerText = c.companyAddress ?? "—";
    document.getElementById("detailDescription").innerText = c.companyDescription ?? "—";
    document.getElementById("detailPhone").innerText = c.companyPhone ?? "—";
    document.getElementById("detailLogo").src = c.companyLogo ?? "/uploads/images/default-company.png";

    // Status
    renderStatus(c.status);

    // Buttons
    setupButtons(c.status);

    // License
    if (c.licenseDocument) {
        document.getElementById("detailLicenseLink").href = c.licenseDocument;
    }

    // Map
    try {
        renderMap(c.latitude, c.longitude, c.companyName);
    } catch (err) {
        console.warn(" renderMap lỗi:", err);
    }
}

/* =========================
   STATUS
========================= */
function renderStatus(status) {
    const badge = document.getElementById("detailStatus");
    const text = document.getElementById("detailStatusText");

    badge.className = "status-badge";

    if (status === "Chờ duyệt") {
        badge.classList.add("status-pending");
        badge.innerText = "Chờ duyệt";
    } else if (status === "Đã duyệt") {
        badge.classList.add("status-approved");
        badge.innerText = "Đã duyệt";
    } else if (status === "Từ chối") {
        badge.classList.add("status-rejected");
        badge.innerText = "Từ chối";
    } else {
        badge.innerText = status ?? "—";
    }

    text.innerText = badge.innerText;
}

/* =========================
   BUTTONS
========================= */
function setupButtons(status) {
    const approveBtn = document.getElementById("btnApproveCompany");
    const rejectBtn = document.getElementById("btnRejectCompany");

    if (!approveBtn || !rejectBtn) return;

    approveBtn.onclick = approveCompany;
    rejectBtn.onclick = rejectCompany;

    if (status !== "Chờ duyệt") {
        approveBtn.style.display = "none";
        rejectBtn.style.display = "none";
    } else {
        approveBtn.style.display = "inline-block";
        rejectBtn.style.display = "inline-block";
    }
}

async function approveCompany() {
    if (!confirm("Duyệt công ty này?")) return;

    await fetch(`${API_BASE}/employer/${window.companyId}/approve`, {
        method: "PUT",
        headers: authHeader()
    });

    alert("Đã duyệt công ty");
    loadCompanyDetail();
}

async function rejectCompany() {
    if (!confirm("Từ chối công ty này?")) return;

    await fetch(`${API_BASE}/employer/${window.companyId}/reject`, {
        method: "PUT",
        headers: authHeader()
    });

    alert("Đã từ chối công ty");
    loadCompanyDetail();
}

/* =========================
   MAP (LEAFLET)
========================= */
function renderMap(lat, lng, name) {
    const mapEl = document.getElementById("detailCompanyMap");

    if (!lat || !lng) {
        mapEl.innerHTML = "<p style='padding:10px'>Chưa có vị trí bản đồ</p>";
        return;
    }

    if (window.companyMap) {
        window.companyMap.remove();
        window.companyMap = null;
    }

    window.companyMap = L.map("detailCompanyMap").setView([lat, lng], 15);

    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution: "&copy; OpenStreetMap"
    }).addTo(window.companyMap);

    L.marker([lat, lng])
        .addTo(window.companyMap)
        .bindPopup(`<b>${name}</b>`)
        .openPopup();

    setTimeout(() => window.companyMap.invalidateSize(), 200);
}

/* =========================
   NAV
========================= */
function goBackCompany() {
    loadPage("/pages/admin-company.html");
}

