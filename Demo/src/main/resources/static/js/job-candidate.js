
function fillCandidate(candidate)
{
    const container = document.getElementById("candidateList");
    const template = document.getElementById("candidateTemplate");
     container.innerHTML = "";
     candidate.forEach(candidate =>{
             const clone = template.content.cloneNode(true);
                 clone.querySelector(".candidate-name").textContent = candidate.fullName || "Ứng viên";
                 clone.querySelector(".candidate-email").textContent = candidate.email || "—";
                 clone.querySelector(".candidate-phone").textContent = candidate.phone || "—";
                    const avatarEl = clone.querySelector(".candidate-avatar");
                     avatarEl.src = candidate.userAvatar || "/uploads/images/default-avatar.png";
                    let statusText = "❔ Không xác định";
                              let badgeClass = "badge badge-pending";

                              switch (candidate.status?.toLowerCase()) {
                                case "đã duyệt":
                                  statusText = "Đã duyệt";
                                  badgeClass = "badge badge-approved";
                                  break;

                                case "đang chờ":
                                  statusText = "Đang chờ";
                                  badgeClass = "badge badge-pending";
                                  break;

                                case "từ chối":
                                  statusText = "Bị từ chối";
                                  badgeClass = "badge badge-rejected";
                                  break;
                              }

                                 const badgeEl = clone.querySelector(".candidate-status");
                                               badgeEl.textContent = statusText;
                                               badgeEl.className = badgeClass;
                               const btnViewCV = clone.querySelector(".btn-view-cv");
                          if (btnViewCV) {
                              btnViewCV.addEventListener("click", e => {
                                  e.preventDefault();
                                  window.openCandidateCV(candidate.applicationId);
                              });
                          }
        container.appendChild(clone);
     })
}
function loadApplicationJobs()
{
    const jobId = window.currentJobId;

    if (!jobId) {
        console.error(" Không có jobId để load ứng viên");
        return;
    }

    fetch(`${API_BASE}/employer/${jobId}/candidates`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            ...authHeader()
        }
    })
    .then(res => {
        if (!res.ok) {
            throw new Error("Không thể lấy đơn ứng tuyển");
        }
        return res.json();
    })
    .then(data => fillCandidate(data))
    .catch(err => {
        console.error("Lỗi load đơn ứng tuyển:", err);
    });
}

function initApplication()
{
    loadApplicationJobs()
}
 window.openCandidateCV = function(applicationId)
 {
     if (!applicationId) return;
      window.currentAppId = applicationId;
      loadPage("/pages/cv-form-viewer.html")
              .then(() => {
                  if (typeof initCandidateCV === "function") {
                      initCandidateCV(); // fetch chi tiết và fill
                  }
              })
              .catch(err => console.error(err));
 }