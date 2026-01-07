async function loadCategories() {
  try {
    const res = await fetch(API_BASE + "/category/list", {
      headers: {
        "Content-Type": "application/json",
        ...authHeader()
      }
    });

    if (!res.ok) throw new Error("Không load được category");

    const data = await res.json();

    console.log("🔥 Category data:", data); // log ở đây, bên trong hàm async

    const select = document.getElementById("categoryId");
    select.innerHTML = "";

    data.forEach(c => {
      const opt = document.createElement("option");
      opt.value = c.id;
      opt.textContent = c.name;
      select.appendChild(opt);
    });

  } catch (err) {
    console.error("Lỗi load category", err);
  }
}


async function loadJobTypes() {
  try {
    const res = await fetch(API_BASE + "/JobType", {
      headers: {
        "Content-Type": "application/json",
        ...authHeader()
      }
    });

    if (!res.ok) throw new Error("Không load được jobtype");

    const data = await res.json();

    const select = document.getElementById("jobTypeId");
    select.innerHTML = "";

    data.forEach(c => {
      const opt = document.createElement("option");
      opt.value = c.id;
      opt.textContent = c.name;
      select.appendChild(opt);
    });

  } catch (err) {
    console.error("Lỗi load jobtype", err);
  }
}

async function initCreateJob() {
  const form = document.getElementById("createJobForm");
  if (!form) {
    setTimeout(initCreateJob, 50);
    return;
  }

  // await để chắc chắn dữ liệu load xong trước khi log
  await loadCategories();
  await loadJobTypes();

  console.log("Select Category:", document.getElementById("categoryId").innerHTML);
  console.log("Select JobType:", document.getElementById("jobTypeId").innerHTML);

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const dateValue = document.getElementById("applicationDeadline").value;

    const payload = {
      jobTitle: document.getElementById("jobTitle").value.trim(),
      locate: document.getElementById("locate").value.trim(),
      salary: document.getElementById("salary").value.trim(),
      jobDescription: document.getElementById("jobDescription").value,
      requirements: document.getElementById("requirements").value,
      benefits: document.getElementById("benefits").value,
      applicationDeadline: dateValue ? `${dateValue}T23:59:59` : null,
      categoryId: document.getElementById("categoryId").value,
      jobTypeId: document.getElementById("jobTypeId").value
    };

    try {
      const res = await fetch(API_BASE + "/jobs", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          ...authHeader()
        },
        body: JSON.stringify(payload)
      });

      if (!res.ok) {
        const msg = await res.text();
        const jobMsg = document.getElementById("jobMsg");
        jobMsg.textContent = msg || "Đăng công việc thất bại";
        jobMsg.style.color = "red";
        return;
      }

      const jobMsg = document.getElementById("jobMsg");
      jobMsg.textContent = "Đăng công việc thành công! Đang chờ duyệt.";
      jobMsg.style.color = "green";

      setTimeout(() => loadPage("/pages/employer-home.html"), 800);
      form.reset();

    } catch (err) {
      console.error(err);
      const jobMsg = document.getElementById("jobMsg");
      jobMsg.textContent = "Không kết nối được server";
      jobMsg.style.color = "red";
    }
  });
}
