window.jobId = window.jobId || null;

window.initAdminJobDetail = function () {

    if (!document.getElementById("jobDetailTitle")) return;

    if (!window.jobId) {
        alert("Không tìm thấy công việc");
        return;
    }

    loadJobDetail();
};


async function loadJobDetail(){
    try {
        const res = await fetch(`${API_BASE}/jobs/${window.jobId}`, {
            headers: authHeader()
        });

        if (!res.ok) throw new Error("Không thể tải chi tiết");

           const job = await res.json();
           renderJobDetail(job);
    } catch (e) {
        console.error(e);
        alert("Lỗi tải chi tiết công ty");
    }
}

function renderJobDetail(j) {
    // Header
    document.getElementById("jobDetailTitle").innerText = j.jobTitle;
    document.getElementById("jobTitle").innerText = j.jobTitle;

    // Meta
    document.getElementById("jobLocate").innerText = j.locate ?? "—";
    document.getElementById("jobSalary").innerText = j.salary ?? "—";
    document.getElementById("jobType").innerText = j.jobType ?? "—";
    document.getElementById("jobCategory").innerText = j.category ?? "—";

    // Dates
    document.getElementById("postedDate").innerText = j.postedDate ?? "—";
    document.getElementById("deadlineDate").innerText = j.applicationDeadline ?? "—";

    // Content
    document.getElementById("jobDescription").innerText = j.jobDescription ?? "—";
    document.getElementById("jobRequirements").innerText = j.requirements ?? "—";
    document.getElementById("jobBenefits").innerText = j.benefits ?? "—";

    // Company
    document.getElementById("companyName").innerText = j.companyName;
    document.getElementById("companyEmail").innerText =
        "Email: " + (j.companyEmail ?? "—");
    document.getElementById("companyAddress").innerText =
        "Địa chỉ: " + (j.companyAddress ?? "—");
    document.getElementById("companySize").innerText =
        j.companySize ?? "—";

    document.getElementById("companyLogo").src =
        j.companyLogo || "/uploads/images/default-company.png";

    // Status + Buttons
    renderJobStatus(j.status);
    setupJobButtons(j.status);

    // View company detail
    document.getElementById("btnViewCompanyDetail").onclick = () => {
        window.companyId = j.companyId;
        loadPage("/pages/admin-company-detail.html");
    };
}

function renderJobStatus(status) {
    const badge = document.getElementById("jobStatusBadge");
    badge.className = "status-badge";

    const normalized = (status || "").trim();

    if (normalized === "Đang chờ") {
        badge.classList.add("status-pending");
        badge.textContent = "Đang chờ";
    }
    else if (normalized === "Đã duyệt") {
        badge.classList.add("status-approved");
        badge.textContent = "Đã duyệt";
    }
    else {
        badge.classList.add("status-rejected");
        badge.textContent = normalized || "Không xác định";
    }
}


function setupJobButtons(status) {
    const approveBtn = document.getElementById("btnApproveJob");
    const rejectBtn = document.getElementById("btnRejectJob");

    approveBtn.onclick = approveJob;
    rejectBtn.onclick = rejectJob;

    const normalized = (status || "").trim();

    if (normalized !== "Đang chờ") {
        approveBtn.style.display = "none";
        rejectBtn.style.display = "none";
    } else {
        approveBtn.style.display = "inline-block";
        rejectBtn.style.display = "inline-block";
    }
}

async function approveJob() {
    if (!confirm("Duyệt công việc này?")) return;

    await fetch(`${API_BASE}/jobs/${window.jobId}/approve`, {
        method: "PUT",
        headers: authHeader()
    });

    alert("Đã duyệt công việc");
    loadJobDetail();
}

async function rejectJob() {
    if (!confirm("Từ chối công việc này?")) return;

    await fetch(`${API_BASE}/jobs/${window.jobId}/reject`, {
        method: "PUT",
        headers: authHeader()
    });

    alert("Đã từ chối công việc");
    loadJobDetail();
}
function goBackJob() {
    loadPage("/pages/admin-job.html");
}