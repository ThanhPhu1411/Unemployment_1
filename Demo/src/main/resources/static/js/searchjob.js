function fmtDate(iso) {
if (!iso) return "";
const d = new Date(iso);
const dd = String(d.getDate()).padStart(2, "0");
const mm = String(d.getMonth() + 1).padStart(2, "0");
const yy = d.getFullYear();
return `${dd}/${mm}/${yy}`;
}
function fillSearchJob(jobs) {
  const container = document.getElementById("jobs-grid-list");
  const template = document.getElementById("job-card-template");
  container.innerHTML = "";

  if (!jobs || jobs.length === 0) {
    document.getElementById("empty-jobs").style.display = "block";
    return;
  } else {
    document.getElementById("empty-jobs").style.display = "none";
  }

  jobs.forEach(job => {
    const clone = template.content.cloneNode(true);

    clone.querySelector(".logo-img-detail").src =
      job.companyLogo || "/images/default-logo.png";

    clone.querySelector(".job-title").textContent = job.jobTitle;
    clone.querySelector(".job-name").textContent = job.companyName || "";
    clone.querySelector(".job-salary").textContent = job.salary || "";
    clone.querySelector(".job-locate").textContent = job.locate || "";
    clone.querySelector(".job-type").textContent = job.jobType || "";

    clone.querySelector(".job-card").addEventListener("click", (e) => {
      if (!e.target.closest(".save-btn")) {
        openJobDetail(job.id);
      }
    });
    clone.querySelector(".save-btn").addEventListener("click", (e) => {
      e.stopPropagation();
      window.saveJob(job.id);
    });

    container.appendChild(clone);
  });
}

function loadSearchJob()
{
  console.log("👉 ĐÃ BẤM TÌM KIẾM");
const keyword = document.getElementById("searchKeyword").value.trim();
  const locate = document.getElementById("searchLocate").value.trim();
  const category = document.getElementById("searchCategory").value.trim();

  const params = new URLSearchParams();
  if (keyword) params.append("keyword", keyword);
  if (locate) params.append("locate", locate);
  if (category) params.append("categoryName", category);

   fetch(API_BASE + "/jobs/search?"  + params.toString() ,{
          method:"GET",
                headers: {
                    "Content-Type": "application/json",
                    ...authHeader()
                  }
      })
        .then(function(res){
              if(!res.ok)
              {
                  alert("Không tìm thấy công việc");
                    throw new Error("Không thể lấy danh sách việc làm");
              }
              return res.json();
        })
          .then(function(data){
               console.log("DATA:", data);
                fillSearchJob(data)
            })
              .catch(err => {
                      console.error("Lỗi load đơn ứng tuyển:", err);
                  });
}
 function initSearchJob() {
   const btn = document.getElementById("searchBtn");
   if (!btn) return;

   btn.addEventListener("click", loadSearchJob);
 }