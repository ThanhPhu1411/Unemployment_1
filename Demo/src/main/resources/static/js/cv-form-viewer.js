function renderList(id, data) {
  const el = document.getElementById(id);
  el.innerHTML = "";
  if (!data) return;

  data.split(",").forEach(s => {
    const li = document.createElement("li");
    li.textContent = s.trim();
    el.appendChild(li);
  });
}

function renderTimeline(containerId, years, contents) {
  const box = document.getElementById(containerId);
  box.innerHTML = "";
  if (!years || !contents) return;

  const yearArr = years.split(",");
  const contentArr = contents.split(",");

  yearArr.forEach((y, i) => {
    const item = document.createElement("div");
    item.className = "timeline-item";
    item.innerHTML = `
      <div class="time">${y.trim()}</div>
      <div class="content">${contentArr[i] || ""}</div>
    `;
    box.appendChild(item);
  });
}

function renderPairList(id, years, contents) {
  const el = document.getElementById(id);
  el.innerHTML = "";
  if (!years || !contents) return;

  const yArr = years.split(",");
  const cArr = contents.split(",");

  yArr.forEach((y, i) => {
    const li = document.createElement("li");
    li.innerHTML = `<strong>${y.trim()}:</strong> ${cArr[i] || ""}`;
    el.appendChild(li);
  });
}

function formatDate(dateStr) {
  if (!dateStr) return "";
  const d = new Date(dateStr);
  return d.toLocaleDateString("vi-VN");
}
function fillCvcCandidate(candidate)
{
  // PROFILE
    document.getElementById("fullName").textContent = candidate.userName || "";
    document.getElementById("position").textContent = candidate.userPosition || "";
    document.getElementById("email").textContent = candidate.userEmail || "";
    document.getElementById("phone").textContent = candidate.userPhone || "";
    document.getElementById("address").textContent = candidate.userAddress || "";
    document.getElementById("birthDate").textContent = formatDate(candidate.userBirthDate);
    document.getElementById("avatar").src = candidate.userAvatar || "/uploads/images/default-avatar.png";
    document.getElementById("facebook").textContent = candidate.userFacebook;
    document.getElementById("careerObjective").textContent = candidate.careerObjective || "";
    document.getElementById("desiredJob").textContent = candidate.userDesiredJob || "";
    document.getElementById("desiredSalary").textContent = candidate.desiredSalary || "";
    document.getElementById("skills").textContent = candidate.softSkill || "";
    renderList("languages", candidate.language);
    renderTimeline("educationList", candidate.educationYear, candidate.education);
    renderTimeline("experienceList", candidate.experienceYear, candidate.experience);
    renderPairList("certificates", candidate.certificateYear, candidate.certificateName);
    renderPairList("prizes", candidate.prizeYear, candidate.prizeDesc);
      document.getElementById("interest").textContent = candidate.interest || "";
}
function loadCandidate()
{
    const applicationId = window.currentAppId
     if (!applicationId) {
            console.error(" Không có applicationId để load ứng viên");
            return;
        }
     fetch(`${API_BASE}/employer/detail/${applicationId}`, {
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
                .then(data => fillCvcCandidate(data))
                .catch(err => {
                    console.error("Lỗi load đơn ứng tuyển:", err);
                });
}
function initCandidateCV()
{
    loadCandidate();
     const btnApprove = document.getElementById("btnApprove");
      const btnReject = document.getElementById("btnReject");

      if (btnApprove) {
        btnApprove.addEventListener("click", approveApplication);
      }

      if (btnReject) {
        btnReject.addEventListener("click", rejectApplication);
      }
}
function approveApplication() {
  const applicationId = window.currentAppId;
  if (!applicationId) return;

  if (!confirm("Bạn chắc chắn muốn DUYỆT hồ sơ này?")) return;

  fetch(`${API_BASE}/employer/application/approved-status/${applicationId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      ...authHeader()
    }
  })
    .then(res => {
      if (!res.ok) throw new Error("Duyệt hồ sơ thất bại");
      return res.json();
    })
    .then(data => {
      updateApplicationStatus("đã duyệt");
      disableActionButtons();
      alert("Đã duyệt hồ sơ ứng viên");
    })
    .catch(err => {
      console.error(err);
      alert(" Không thể duyệt hồ sơ");
    });
}

function rejectApplication() {
  const applicationId = window.currentAppId;
  if (!applicationId) return;

  if (!confirm("Bạn chắc chắn muốn TỪ CHỐI hồ sơ này?")) return;

  fetch(`${API_BASE}/employer/application/reject-status/${applicationId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      ...authHeader()
    }
  })
    .then(res => {
      if (!res.ok) throw new Error("Từ chối hồ sơ thất bại");
      return res.json();
    })
    .then(data => {
      updateApplicationStatus("từ chối");
      alert(" Đã từ chối hồ sơ");
    })
    .catch(err => {
      console.error(err);
      alert(" Không thể từ chối hồ sơ");
    });
}

function updateApplicationStatus(status) {
  const badge = document.getElementById("applicationStatus");
  if (!badge) return;

  badge.classList.remove("badge-pending", "badge-approved", "badge-rejected");

  switch (status.toLowerCase()) {
    case "đã duyệt":
      badge.textContent = "Đã duyệt";
      badge.classList.add("badge-approved");
      break;

    case "từ chối":
      badge.textContent = "Bị từ chối";
      badge.classList.add("badge-rejected");
      break;

    default:
      badge.textContent = "Đang chờ";
      badge.classList.add("badge-pending");
  }
}
function disableActionButtons() {
  const btnApprove = document.getElementById("btnApprove");
  const btnReject = document.getElementById("btnReject");

  if (btnApprove) btnApprove.style.display = "none";
  if (btnReject) btnReject.style.display = "none";
}
