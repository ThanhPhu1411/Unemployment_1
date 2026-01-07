
    function fmtDate(iso) {
      if (!iso) return "";
      const d = new Date(iso);
      const dd = String(d.getDate()).padStart(2, "0");
      const mm = String(d.getMonth() + 1).padStart(2, "0");
      const yy = d.getFullYear();
      return `${dd}/${mm}/${yy}`;
    }
    function initJobTabs() {
    const tabs = document.querySelectorAll(".tab");
    const content = document.querySelectorAll(".tab-content");

    tabs.forEach((tab, index) =>{
    tab.addEventListener('click' ,() =>{
    document.querySelector(".tab.active").classList.remove('active');
    document.querySelector(".tab-content.active").classList.remove('active');
        tab.classList.add('active');
        content[index].classList.add('active');
    });
    });
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
    function initGoogleDirections(lat, lng) {
      const link = document.getElementById("directions-link");
      if (!link || !lat || !lng) return;
      link.href = `https://www.google.com/maps/dir/?api=1&destination=${lat},${lng}`;
    }


function renderJobMap(lat, lng) {
  const map = document.getElementById("map");
  if (!map || !lat || !lng) return;

  map.innerHTML = `
    <iframe
      width="100%"
      height="300"
      style="border:0;border-radius:8px;"
      loading="lazy"
      src="https://www.google.com/maps?q=${lat},${lng}&z=15&output=embed">
    </iframe>
  `;
}


    function fillJobdetail(data)
    {
      setSrc("jdCompanyLogo", data.companyLogo || "/uploads/images/job3.jpg");
      setText("jdCompanyName", data.companyName);
      setText("jdCompanyName2", data.companyName);
      setText("jdJobTitle", data.jobTitle);

      setText("jdSalary", data.salary);
      setText("jdLocate", data.locate);
      setText("jdDeadline", fmtDate(data.applicationDeadline));
      setText("jdPostedDate", fmtDate(data.postedDate));
      setText("jdPostedDate2", fmtDate(data.postedDate));

      setText("jdJobType", data.jobType);
      setText("jdCategory", data.category);

      setText("jdJobDescription", data.jobDescription);
      setText("jdRequirements", data.requirements);
      setText("jdBenefits", data.benefits);

      setText("jdCompanyEmail", data.companyEmail);
      setText("jdCompanySize", data.companySize);
      setText("jdCompanyDescription", data.companyDescription);

     const license = document.getElementById("jdLicenseLink");
      if (license) {
        if (data.licenseDocument) {
          license.href = data.licenseDocument;
          license.style.pointerEvents = "auto";
          license.style.opacity = "1";
        } else {
          license.href = "#";
          license.style.pointerEvents = "none";
          license.style.opacity = "0.6";
        }
      }

    renderJobMap(data.latitude, data.longitude);

      initGoogleDirections(data.latitude, data.longitude);
      initJobTabs();
    }

window.openJobDetail = function (jobId) {

 window.currentJobId = jobId;
  loadPage("/pages/job-detail.html")
    .then(() => {
           return fetch(API_BASE + "/jobs/" + jobId, {
             headers: {
               ...authHeader()
             }
           });
         })
    .then(res => {
      if (!res.ok) throw new Error(res.status);
      return res.json();
    })
    .then(data => {
      fillJobdetail(data);

       const btn = document.getElementById("applyBtn");
            if (btn) {
              btn.onclick = applyJob;
            }

    })
    .catch(err => {
      console.error("Lỗi load job:", err);
    });
};


