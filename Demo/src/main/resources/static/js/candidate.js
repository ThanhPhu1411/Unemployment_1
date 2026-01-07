function initCandidate() {
  const API = API_BASE; // base API
  const fields = [
    "userName","userPosition","userEmail","userPhone","userAddress",
    "userBirthDate","userFacebook","careerObjective","educationYear","education",
    "experienceYear","experience","desiredSalary","userDesiredJob",
    "certificateYear","certificateName","prizeYear","prizeDesc",
    "language","softSkill","interest"
  ];

  const previewMap = {
    userName: "pName",
    userPosition: "pPosition",
    userEmail: "pEmail",
    userPhone: "pPhone",
    userAddress: "pAddress",
    userBirthDate: "pBirth",
    userFacebook: "pFacebook",
    careerObjective: "pObjective",
    educationYear: "pEdu",
    education: "pEdu",
    experienceYear: "pExp",
    experience: "pExp",
    userDesiredJob: "pDesired",
    desiredSalary: "pSalary",
    certificateYear: "pCert",
    certificateName: "pCert",
    prizeYear: "pPrize",
    prizeDesc: "pPrize",
    language: "pSkill",
    softSkill: "pSkill",
    interest: "pSkill"
  };

  const defaultValues = {
    userName: "NGUYỄN VĂN A",
    userPosition: "TIÊU ĐỀ HỒ SƠ",
    userEmail: "—",
    userPhone: "—",
    userAddress: "—",
    userBirthDate: "—",
    userFacebook: "—",
    careerObjective: "Nhập mục tiêu nghề nghiệp để xem preview.",
    educationYear: "",
    education: "",
    experienceYear: "",
    experience: "",
    userDesiredJob: "—",
    desiredSalary: "—",
    certificateYear: "",
    certificateName: "",
    prizeYear: "",
    prizeDesc: "",
    language: "",
    softSkill: "",
    interest: ""
  };

  let currentCandidateId = null;
  const inputs = {};
  const previews = {};

  fields.forEach(f => {
    inputs[f] = document.getElementById(f);
    previews[f] = document.getElementById(previewMap[f]);
  });

  const avatarInput = document.getElementById("avatar");
  const avatarMini = document.getElementById("avatarMini");
  const avatarPreview = document.getElementById("avatarPreview");
  const btnSave = document.getElementById("btnSave");

  // ================= LOAD PROFILE =================
  async function loadProfile() {
    try {
      const res = await fetch(`${API}/candidate/me`, {
        method: "GET",
        headers: { "Content-Type": "application/json", ...authHeader() }
      });
      if (!res.ok) throw new Error("Không thể lấy hồ sơ việc làm");
      const data = await res.json();
      currentCandidateId = data.id;

      fields.forEach(f => {
        if (inputs[f]) inputs[f].value = data[f] || "";
      });

      updatePreview(data);

      if (data.userAvatar) {
        avatarMini.src = data.userAvatar;
        avatarPreview.src = data.userAvatar;
      }
    } catch (err) {
      console.error(err);
      alert(err.message);
    }
  }

  // ================= UPDATE PREVIEW =================
  function updatePreview(data) {
    previews["userName"].textContent = data.userName || defaultValues.userName;
    previews["userPosition"].textContent = data.userPosition || defaultValues.userPosition;
    previews["userEmail"].textContent = data.userEmail || defaultValues.userEmail;
    previews["userPhone"].textContent = data.userPhone || defaultValues.userPhone;
    previews["userAddress"].textContent = data.userAddress || defaultValues.userAddress;
    previews["userBirthDate"].textContent = data.userBirthDate || defaultValues.userBirthDate;
    previews["userFacebook"].textContent = data.userFacebook || defaultValues.userFacebook;
    previews["careerObjective"].textContent = data.careerObjective || defaultValues.careerObjective;

    previews["educationYear"].textContent =
      (data.educationYear || "") + (data.education ? " - " + data.education : "");
    previews["experienceYear"].textContent =
      (data.experienceYear || "") + (data.experience ? " - " + data.experience : "");
    previews["userDesiredJob"].textContent = data.userDesiredJob || defaultValues.userDesiredJob;
    previews["desiredSalary"].textContent = data.desiredSalary || defaultValues.desiredSalary;
    previews["certificateYear"].textContent =
      (data.certificateYear || "") + (data.certificateName ? " - " + data.certificateName : "");
    previews["prizeYear"].textContent =
      (data.prizeYear || "") + (data.prizeDesc ? " - " + data.prizeDesc : "");
    previews["language"].textContent =
      [data.language, data.softSkill, data.interest].filter(Boolean).join(", ");
  }

  // ================= INPUT EVENT LISTENER =================
  fields.forEach(f => {
    if (!inputs[f]) return;
    inputs[f].addEventListener("input", () => {
      updatePreview({
        ...inputsToObj(),
        [f]: inputs[f].value
      });
    });
  });

  function inputsToObj() {
    const obj = {};
    fields.forEach(f => obj[f] = inputs[f]?.value || "");
    return obj;
  }

  // ================= AVATAR PREVIEW =================
  avatarInput.addEventListener("change", e => {
    const file = e.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = () => {
      avatarMini.src = reader.result;
      avatarPreview.src = reader.result;
    };
    reader.readAsDataURL(file);
  });

  // ================= SAVE =================
    btnSave.addEventListener("click", async () => {
      try {
        const candidateObj = inputsToObj(); // JSON thẳng

        const url = currentCandidateId
          ? `${API}/candidate/updateCandidate/${currentCandidateId}`
          : `${API}/candidate/createCandidate`;
        const method = currentCandidateId ? "PUT" : "POST";

        const res = await fetch(url, {
          method,
          headers: {
            "Content-Type": "application/json", // BẮT BUỘC với @RequestBody
            ...authHeader()
          },
          body: JSON.stringify(candidateObj)
        });

        if (!res.ok) {
          const text = await res.text();
          alert(text || "Lưu hồ sơ thất bại");
          return;
        }

        const data = await res.json();
        currentCandidateId = data.id;
        loadProfile();
        alert("Lưu hồ sơ thành công");
      } catch (err) {
        console.error(err);
        alert("Không thể kết nối đến server");
      }
    });

    loadProfile();

  loadProfile();
}
