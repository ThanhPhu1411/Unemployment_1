function fmtDate(iso) {
      if (!iso) return "";
      const d = new Date(iso);
      const dd = String(d.getDate()).padStart(2, "0");
      const mm = String(d.getMonth() + 1).padStart(2, "0");
      const yy = d.getFullYear();
      return `${dd}/${mm}/${yy}`;
    }
       function setText (id,text)
        {
        const el = document.getElementById(id);
        if(!el) return;
        el.textContent = text || "";
        }
          function setSrc(id, src) {
            const el = document.getElementById(id);
            if (!el) return;
            el.src = src || "";
          }
window.saveJob = function(jobId)
{
if(!localStorage.getItem("token")){
   alert("Vui lòng đăng nhập để lưu công việc");
    return;
}
fetch(API_BASE + "/savejob/save/" + jobId,{
method:"POST",
headers:{
  "Content-Type": "application/json",
      ...authHeader()
}
})
.then (function(res){
if(!res.ok){
  alert("Lưu thất bại");
      throw new Error("Lưu thất bại");
}
return res.json();
})
  .then(function(data) {
    console.log("Lưu thành công:", data);
    alert("Lưu thành công!");
  })
  .catch(function(err) {
    console.error(err);
  });
}
function fillSaveJobsList(jobs) {
  const container = document.querySelector(".jobs-list");
  const template = document.getElementById("job-template");

  container.innerHTML = "";
  if (jobs.length === 0) {
    document.getElementById("empty-saved-job").style.display = "block";
    return;
  } else {
    document.getElementById("empty-saved-job").style.display = "none";
  }

  jobs.forEach(job => {
    const clone = template.cloneNode(true);
    clone.style.display = "flex";
    clone.removeAttribute("id");

    // set dữ liệu
    clone.querySelector(".logo-img-detail").src = job.companyLogo || "/uploads/images/job3.jpg";
    clone.querySelector(".job-title").textContent = job.jobTitle;
    clone.querySelector(".company-name").textContent = job.companyName;
    clone.querySelector(".job-locate").textContent = job.locate;
    clone.querySelector(".job-salary").textContent = job.salary;
    clone.querySelector(".job-time").textContent = fmtDate(job.applicationDeadline); // hoặc job.jobType nếu đúng
    clone.querySelector(".job-type").textContent = job.jobType;

   clone.addEventListener("click", (e) => {
     if (!e.target.classList.contains("delete-btn")) {
       openJobDetail(job.jobId); // gọi JS load detail
     }
   });
      const deleteBtn = clone.querySelector(".delete-btn");
       if (deleteBtn) {
         deleteBtn.addEventListener("click", (e) => {
           e.stopPropagation(); // tránh trigger click mở chi tiết
           window.unSaveJob(job.jobId, clone); // gọi API xóa và remove element
         });
       }
    container.appendChild(clone);
  });
}

function loadSaveJobs(){
fetch(API_BASE + "/savejob",{
 method: "GET",
    headers: {
      "Content-Type": "application/json",
      ...authHeader()
    }
})
.then(function(res){
if(!res.ok){
  alert("Không thể lấy danh sách việc làm");
      throw new Error("Không thể lấy danh sách việc làm");
}
return res.json();
})
.then(function(data){
fillSaveJobsList(data)
})
 .catch(err => {
      console.error("Lỗi load job:", err);
    });
}
window.unSaveJob = function(jobId, element) {
  if (!localStorage.getItem("token")) {
    alert("Vui lòng đăng nhập để thực hiện thao tác này");
    return;
  }

  if (!confirm("Bạn có chắc chắn muốn hủy lưu công việc này?")) return;

  fetch(API_BASE + "/savejob/save/" + jobId, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
      ...authHeader()
    }
  })
    .then(res => {
      if (!res.ok) throw new Error("Xóa thất bại");

    })
    .then(data => {
      alert("Đã hủy lưu công việc");
      if (element) element.remove();
    })
    .catch(err => console.error(err));
};

