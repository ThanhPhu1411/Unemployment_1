function fillJobs(jobs)
{
const container = document.getElementById("jobList");
const template = document.getElementById("jobCardTemplate");
 container.innerHTML = "";
   jobs.forEach(job => {
       const clone = template.content.cloneNode(true);

       clone.querySelector(".job-title").textContent = job.jobTitle;
       clone.querySelector(".job-locate").textContent = job.locate;
       clone.querySelector(".job-salary").textContent = job.salary;
       clone.querySelector(".job-category").textContent = job.categoryName;
       clone.querySelector(".job-type").textContent = job.jobTypeName;
       clone.querySelector(".job-applicant-count").textContent = job.applicantCount ?? 0;

              let statusText = "❔ Không xác định";
              let badgeClass = "badge bg-secondary";

              switch (job.status?.toLowerCase()) {
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
               const badgeEl = clone.querySelector(".job-status");
                             badgeEl.textContent = statusText;
                             badgeEl.className = badgeClass;

const deleteBtn = clone.querySelector(".btn-outline-danger");
        if (deleteBtn) {
            deleteBtn.addEventListener("click", () => deleteJob(job.id));
        }
      const editBtn = clone.querySelector(".btn-outline-secondary");
           if (editBtn) {
               editBtn.addEventListener("click", e => {
                   e.preventDefault();
                   window.openDeatil(job.id);
               });
           }
       container.appendChild(clone);
     });
}
function loadMyJobs()
{
   fetch(API_BASE + "/jobs/me" ,{
        method:"GET",
              headers: {
                  "Content-Type": "application/json",
                  ...authHeader()
                }
    })
        .then(function(res){
            if(!res.ok)
            {
                alert("Không thể lấy danh sách công việc");
                  throw new Error("Không thể lấy công việc");
            }
            return res.json();
        })
           .then(function(data){
                fillJobs(data)
            })
              .catch(err => {
                      console.error("Lỗi load công việc:", err);
                  });
}
function initEmployerHome() {
  loadMyJobs();
  initSearch();
}
function loadEmployerJobs(keyword = "") {
  const url = keyword
    ? `${API_BASE}/jobs/employer/search?keyword=${encodeURIComponent(keyword)}`
    : `${API_BASE}/jobs/employer/search`;

  fetch(url, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      ...authHeader()
    }
  })
    .then(res => {
      if (!res.ok) throw new Error("Không thể load công việc");
      return res.json();
    })
    .then(data => fillJobs(data))
    .catch(err => {
      console.error("Lỗi load công việc:", err);
    });
}
function initSearch() {
  const searchInput = document.getElementById("searchJob");
  if (!searchInput) return;

  let searchTimeout = null;

  searchInput.addEventListener("input", () => {
    clearTimeout(searchTimeout);

    searchTimeout = setTimeout(() => {
      const keyword = searchInput.value.trim();
      loadEmployerJobs(keyword);
    }, 300);
  });
}


function deleteJob(jobId) {
  if (!confirm("Bạn có chắc chắn muốn xóa công việc này không?")) return;

   fetch(API_BASE + "/jobs/" + jobId, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
      ...authHeader()
    }
  })
    .then(res => {
      if (!res.ok) throw new Error("Xóa công việc thất bại");
      alert("Xóa công việc thành công");
      loadMyJobs();
    })
    .catch(err => {
      console.error("Lỗi xóa công việc:", err);
      alert("Không thể xóa công việc");
    });
}
window.openDeatil = function(jobId) {
    if (!jobId) return;
    window.currentJobId = jobId;

    loadPage("/pages/detailjob.html")
        .then(() => {
            if (typeof initJob === "function") {
                initJob(); // fetch chi tiết và fill
            }
        })
        .catch(err => console.error(err));
};

