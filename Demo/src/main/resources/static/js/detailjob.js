// =====================
// Utils
// =====================
function formatDate(dateTimeStr) {
    if (!dateTimeStr) return "—";
    const date = new Date(dateTimeStr);
    if (isNaN(date.getTime())) return "—";
    return date.toLocaleDateString("vi-VN");
}
  function setSrc(id, src) {
    const el = document.getElementById(id);
    if (!el) return;
    el.src = src || "";
  }
// =====================
// Fill dữ liệu vào trang detail
// =====================
function fillJobsdetail(job) {
    const titleEl = document.getElementById("jobTitle");
    if (!titleEl) return; // DOM chưa sẵn sàng

    document.getElementById("jobTitle").textContent = job.jobTitle ?? "—";
    document.getElementById("jobLocate").textContent = job.locate ?? "—";
    document.getElementById("jobSalary").textContent = job.salary ?? "Thỏa thuận";

    document.getElementById("jobDescription").textContent = job.jobDescription ?? "Chưa có mô tả.";
    document.getElementById("jobRequirements").textContent = job.requirements ?? "Chưa có yêu cầu.";
    document.getElementById("jobBenefits").textContent = job.benefits ?? "Chưa có quyền lợi.";

        document.getElementById("infoLocate").textContent = job.locate ?? "—";
        document.getElementById("infoSalary").textContent = job.salary ?? "Thỏa thuận";
        document.getElementById("infoPostedDate").textContent = formatDate(job.postedDate);
         document.getElementById("infoDeadline").textContent = formatDate(job.applicationDeadline);
         document.getElementById("categoryId").textContent = job.category ?? "—";
        document.getElementById("jobTypeId").textContent = job.jobType ?? "Thỏa thuận";

    // Trạng thái
    const badgeEl = document.getElementById("jobStatus");
    if (badgeEl) {
        let statusText = "Không xác định";
        let badgeClass = "badge bg-secondary";
        switch ((job.status ?? "").toLowerCase()) {
            case "đã duyệt":
                statusText = "Đã duyệt";
                badgeClass = "badge bg-success";
                break;
            case "đang chờ":
                statusText = "Đang chờ";
                badgeClass = "badge bg-warning";
                break;
            case "từ chối":
                statusText = "Bị từ chối";
                badgeClass = "badge bg-danger";
                break;
        }
        badgeEl.textContent = statusText;
        badgeEl.className = badgeClass;
    }
    // Lưu jobId global để trang Edit dùng
    window.currentJobId = job.jobId;
        const candidateBtn = document.getElementById("btnCandidates");
        if(candidateBtn)
        {
            candidateBtn.addEventListener("click", e=>{
                e.preventDefault();
                window.openCandidate(window.currentJobId)
            })
        }

    // Bind nút sửa sau khi DOM sẵn sàng
    const interval = setInterval(() => {
        const btnEdit = document.getElementById("btnEdit");
        if (btnEdit) {
            clearInterval(interval);
            bindEditButton();
        }
    }, 50);
}

// =====================
// Load job detail từ API
// =====================
function initJob(jobId) {
    const id = jobId || window.currentJobId;
    if (!id) return console.warn("Không có jobId để load!");

    fetch(`${API_BASE}/jobs/employer/${id}`, {
        headers: { ...authHeader() }
    })
    .then(res => {
        if (!res.ok) throw new Error(res.status);
        return res.json();
    })
    .then(job => fillJobsdetail(job))
    .catch(err => console.error("Lỗi load job:", err));
}

// =====================
// Bind nút Edit SPA
// =====================
function bindEditButton() {
    const btnEdit = document.getElementById("btnEdit");
    if (!btnEdit) return;

    // tránh bind nhiều lần
    const newBtn = btnEdit.cloneNode(true);
    btnEdit.parentNode.replaceChild(newBtn, btnEdit);

    newBtn.addEventListener("click", e => {
        e.preventDefault();
        const jobId = window.currentJobId;
        if (!jobId) return console.warn(" Không có jobId để sửa!");

        // Load trang edit-job.html
        loadPage("/pages/editjob.html").then(() => {
            if (typeof initEditJob === "function") initEditJob();
        });
    });
}

window.openCandidate = function(jobId)
{
    if(!jobId) return;
    window.currentJobId=jobId;
    loadPage("/pages/job-candidate.html")
    .then(()=>{
           if (typeof initApplication  === "function") {
                    initApplication(); // fetch chi tiết và fill
           }
    })
    .catch(err => console.error(err));
}

