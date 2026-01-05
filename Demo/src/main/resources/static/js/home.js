   function fmtDate(iso) {
      if (!iso) return "";
      const d = new Date(iso);
      const dd = String(d.getDate()).padStart(2, "0");
      const mm = String(d.getMonth() + 1).padStart(2, "0");
      const yy = d.getFullYear();
      return `${dd}/${mm}/${yy}`;
    }
function loadHomeData() {
  const jobList = document.getElementById("job-list");
  const categoryList = document.getElementById("category-list");

  if (!jobList || !categoryList) return;

  fetch(API_BASE + "/home/jobs")
    .then(res => res.json())
    .then(data => {
      jobList.innerHTML = "";
      data.forEach(job => {
        const logo = job.companyLogo || "/uploads/images/default-logo.png";

        jobList.innerHTML += `
          <div class="job-card">
            <div class="job-card-left">
              <div class="job-logo">
                <img src="${logo}">
              </div>
              <div class="job-content">
                <h4>${job.jobTitle ?? ""}</h4>
                <p>${job.jobDescription ?? ""}</p>
                <p><i class="fas fa-map-marker-alt"></i> ${job.locate ?? ""}</p>
                <p><i class="fas fa-dollar-sign"></i> ${job.salary ?? ""}</p>
                <p><i class="fas fa-clock"></i> ${fmtDate(job.applicationDeadline ?? "")}</p>
              </div>
            </div>

            <div class="job-card-right">
              <a href="#"
                 class="btn-apply"
                 onclick="openJobDetail('${job.id}'); event.preventDefault();">
                 Ứng tuyển ngay
              </a>
              <button type="button">Lưu công việc</button>
            </div>
          </div>
        `;
      });
    })
    .catch(err => console.error("Load jobs error", err));

  fetch(API_BASE + "/category/list")
    .then(res => res.json())
    .then(data => {
      categoryList.innerHTML = "";
      data.forEach(cat => {
        categoryList.innerHTML += `
          <div class="category-card">
            <p>${cat.name}</p>
          </div>
        `;
      });
    })
    .catch(err => console.error("Load categories error", err));
}
