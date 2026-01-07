function fmtDate(iso) {
      if (!iso) return "";
      const d = new Date(iso);
      const dd = String(d.getDate()).padStart(2, "0");
      const mm = String(d.getMonth() + 1).padStart(2, "0");
      const yy = d.getFullYear();
      return `${dd}/${mm}/${yy}`;
    }

function fillApplication(applications)
{
    const container = document.getElementById("applications-list");
    const template = document.getElementById("job-template");

    container.innerHTML = "";

     if (applications.length === 0) {
        document.getElementById("empty-applications").style.display = "block";
        return;
      } else {
        document.getElementById("empty-applications").style.display = "none";
      }

      applications.forEach( application =>{
       const clone = template.cloneNode(true);
        clone.style.display = "flex";
         clone.removeAttribute("id");
             clone.querySelector(".logo-img-detail").src = application.companyLogo || "/uploads/images/job3.jpg";
             clone.querySelector(".job-title").textContent = application.jobTitle;
             clone.querySelector(".job-name").textContent = application.companyName;
             clone.querySelector(".job-salary").textContent = application.salary;
             clone.querySelector(".job-locate").textContent = application.locate;
             clone.querySelector(".apply-date").textContent = fmtDate(application.applyDate);
             clone.querySelector(".job-type").textContent = application.jobType;

             let statusText = "❔ Không xác định";
             let badgeClass = "badge bg-secondary";

             switch (application.status?.toLowerCase()) {
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

               const badgeEl = clone.querySelector(".status-badge");
               badgeEl.textContent = statusText;
               badgeEl.className = badgeClass;

                 clone.addEventListener("click", () => {

                   openJobDetail(application.jobId);

               });
              container.appendChild(clone);
     });
     }
 function loadApplication()
 {
    fetch(API_BASE + "/application/my-applications" ,{
        method:"GET",
              headers: {
                  "Content-Type": "application/json",
                  ...authHeader()
                }
    })
    .then(function(res){
        if(!res.ok)
        {
            alert("Không thể lấy danh sách đơn ứng tuyển");
              throw new Error("Không thể lấy danh sách việc làm");
        }
        return res.json();
    })
    .then(function(data){
        fillApplication(data)
    })
     .catch(err => {
          console.error("Lỗi load đơn ứng tuyển:", err);
      });
 }

 function applyJob()
 {
   if (!localStorage.getItem("token")) {
     alert("Vui lòng đăng nhập để ứng tuyển");
     return;
     }
     if(!window. currentJobId)
     {
      alert("Không xác định được công việc");
         return;
     }
       fetch(API_BASE + "/application/apply/" +window.currentJobId ,{
             method:"POST",
                   headers: {
                       "Content-Type": "application/json",
                       ...authHeader()
                     }
         })
         .then(function(res){
         if(!res.ok)
         {
         alert("Không thể ứng tuyển");
                       throw new Error("Ứng tuyển thất ");
         }
         return res.json();
         })
          .then(() => {
               const btn = document.getElementById("applyBtn");
               btn.textContent = "Đã ứng tuyển";
               btn.disabled = true;
               btn.classList.remove("btn-success");
               btn.classList.add("btn-secondary");
             })
               .catch(() => {
                   alert("Bạn đã ứng tuyển công việc này rồi!");
               });
 }

