
let allJobs = [];
let Status = "Đang chờ";

window.initAdminJob = function () {
    initTabsJob();
    loadJobs();
};

async function loadJobs() {
    try {
        const res = await fetch(`${API_BASE}/jobs/admin`, {
            headers: authHeader()
        });

        if (!res.ok) throw new Error("Không thể tải công việc");

        allJobs = await res.json();
        renderJobs();

    } catch (err) {
        console.error(err);
        document.getElementById("companyList").innerHTML =
            `<tr><td colspan="5">Lỗi tải dữ liệu</td></tr>`;
    }
}

function renderJobs() {
    const listEl = document.getElementById("jobList");

    const filtered = allJobs.filter(
        c => c.status === Status
    );

    if (!filtered.length) {
        listEl.innerHTML =
            `<tr><td colspan="5">Không có dữ liệu</td></tr>`;
        return;
    }

    listEl.innerHTML = "";

    filtered.forEach(c => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${c.jobTitle}</td>
            <td>${c.companyName}</td>
            <td>${c.salary ?? "-"}</td>

            <td>
                ${renderActions(c)}
            </td>
        `;
        listEl.appendChild(tr);
    });
}

function renderActions(job) {
    return `
        <button class="action-btn btn-detail-job"
            onclick="viewJob('${job.id}')">Chi tiết</button>
    `;
}

function initTabsJob() {
    document.querySelectorAll(".tab-btn").forEach(btn => {
        btn.onclick = () => {
            document.querySelectorAll(".tab-btn")
                .forEach(b => b.classList.remove("active"));

            btn.classList.add("active");
            Status = btn.dataset.status;
            renderJobs();
        };
    });
}
function viewJob(id) {
   window.companyId = undefined;
   window.jobId  = id;      // set biến toàn cục
     loadPage("/pages/admin-job-detail.html");
}
