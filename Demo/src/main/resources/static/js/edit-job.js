// =====================
// Utils + Maps
// =====================
window.categoryMap = {}; // name -> id
window.jobTypeMap = {};  // name -> id
window.currentJobData = null; //  lưu data full để PUT merge

async function loadCategories() {
  try {
    const res = await fetch(API_BASE + "/category/list", {
      headers: {
        "Content-Type": "application/json",
        ...authHeader()
      }
    });

    if (!res.ok) throw new Error("Không load được category");

    // ✅ THÊM DÒNG NÀY
    const data = await res.json();
    console.log("CATEGORY API:", data);

    const select = document.getElementById("categoryId");
    if (!select) return;

    select.innerHTML = "";

    data.forEach(c => {
      const opt = document.createElement("option");
      opt.value = c.id;
      opt.textContent = c.name;
      select.appendChild(opt);
    });

  } catch (err) {
    console.error("Lỗi load category", err);
  }
}


async function loadJobTypes() {
  try {
    const res = await fetch(API_BASE + "/JobType", {
      headers: {
        "Content-Type": "application/json",
        ...authHeader()
      }
    });

    if (!res.ok) throw new Error("Không load được jobtype");
    const data = await res.json();

    const select = document.getElementById("jobTypeId");
    if (!select) return;

    select.innerHTML = "";
    window.jobTypeMap = {};

    data.forEach(c => {
      window.jobTypeMap[c.name.trim()] = String(c.id);

      const opt = document.createElement("option");
      opt.value = String(c.id);
      opt.textContent = c.name;
      select.appendChild(opt);
    });

  } catch (err) {
    console.error("Lỗi load jobtype", err);
  }
}

function formatDate(dateTimeStr) {
  if (!dateTimeStr) return "";
  const date = new Date(dateTimeStr);
  if (isNaN(date.getTime())) return "";
  return date.toISOString().slice(0, 10);
}

// helper: set select by matching option text (name)
// helper: set select theo tên option (text)
function setSelectByText(selectEl, text) {
  if (!selectEl || !text) return;
  const target = text.trim().toLowerCase();
  for (const opt of selectEl.options) {
    if ((opt.textContent ?? "").trim().toLowerCase() === target) {
      selectEl.value = opt.value;
      return;
    }
  }
}

function fillEditForm(job) {
  document.getElementById("jobId").value = job.id ?? "";
  document.getElementById("jobTitle").value = job.jobTitle ?? "";
  document.getElementById("locate").value = job.locate ?? "";
  document.getElementById("salary").value = job.salary ?? "";
  document.getElementById("jobDescription").value = job.jobDescription ?? "";
  document.getElementById("requirements").value = job.requirements ?? "";
  document.getElementById("benefits").value = job.benefits ?? "";
  document.getElementById("applicationDeadline").value = formatDate(job.applicationDeadline);

  // ✅ chọn option theo text (vì API trả chuỗi)
  setSelectByText(document.getElementById("categoryId"), job.category);
  setSelectByText(document.getElementById("jobTypeId"), job.jobType);
}


// =====================
// Load dữ liệu job để edit
// =====================
async function initEditJob() {
  const jobId = window.currentJobId;
  if (!jobId) return console.warn("Không có jobId để load!");

  // ✅ CHỜ DOM CỦA editjob.html TỒN TẠI
  await waitForEditFormDOM();

  try {
    await Promise.all([loadCategories(), loadJobTypes()]);

    const res = await fetch(`${API_BASE}/jobs/${jobId}`, {
      headers: { ...authHeader() }
    });
    if (!res.ok) throw new Error("Không thể load công việc");

    const job = await res.json();
    window.currentJobData = job;
    fillEditForm(job);

  } catch (err) {
    console.error("Lỗi load job để edit:", err);
  }
}

// =====================
// Submit form SPA (PUT full object)
// =====================
function submitEditForm(e) {
  e.preventDefault();

  const jobId = document.getElementById("jobId")?.value;
  if (!jobId) return console.warn("Không có jobId để lưu!");

  if (!window.currentJobData) {
    return console.warn("Chưa load currentJobData, không thể PUT!");
  }

  const categorySelect = document.getElementById("categoryId");
  const jobTypeSelect  = document.getElementById("jobTypeId");

  const categoryId = categorySelect?.value || null;
  const jobTypeId  = jobTypeSelect?.value || null;

  const deadlineVal = document.getElementById("applicationDeadline")?.value;
  const deadlineApi = deadlineVal ? `${deadlineVal}T23:59:59` : null;

  const payload = {
    ...window.currentJobData,
    id: jobId,
    jobTitle: document.getElementById("jobTitle")?.value ?? "",
    locate: document.getElementById("locate")?.value ?? "",
    salary: document.getElementById("salary")?.value ?? "",
    jobDescription: document.getElementById("jobDescription")?.value ?? "",
    requirements: document.getElementById("requirements")?.value ?? "",
    benefits: document.getElementById("benefits")?.value ?? "",
    applicationDeadline: deadlineApi,

    //  FIX ở đây
    category: categoryId ? { id: categoryId } : null,
    jobType: jobTypeId ? { id: jobTypeId } : null
  };

  console.log("📤 PUT payload:", payload);

  fetch(`${API_BASE}/jobs/${jobId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      ...authHeader()
    },
    body: JSON.stringify(payload)
  })
  .then(async res => {
    const text = await res.text();
    console.log("PUT status:", res.status);
    console.log("PUT response:", text);

    if (!res.ok) throw new Error(text || "Cập nhật thất bại");
    return JSON.parse(text);
  })
  .then(updatedJob => {
    window.currentJobId = updatedJob.jobId;

    loadPage("/pages/detailjob.html").then(() => {
      if (typeof initJob === "function") initJob();
    });
  })
  .catch(err => {
    console.error("❌ PUT error:", err);
    alert("❌ Cập nhật thất bại: " + err.message);
  });
}


// =====================
// Quay lại detail
// =====================
function goBackToDetail() {
  loadPage("/pages/detailjob.html").then(() => {
    if (typeof initJob === "function") initJob();
  });
}

// =====================
// Init khi DOM sẵn sàng
// =====================
function waitForEditFormDOM() {
  return new Promise(resolve => {
    const check = () => {
      if (
        document.getElementById("categoryId") &&
        document.getElementById("jobTypeId")
      ) {
        resolve();
      } else {
        setTimeout(check, 50);
      }
    };
    check();
  });
}
