let allCompanies = [];
let currentStatus = "Đang chờ";

// =====================
// Khởi tạo page admin-company
// =====================
window.initAdminCompany = function () {
    initTabs();
    loadCompanies();
};

// =====================
// Load dữ liệu công ty
// =====================
async function loadCompanies() {
    try {
        const res = await fetch(`${API_BASE}/employer`, { headers: authHeader() });
        if (!res.ok) throw new Error("Không thể tải công ty");

        allCompanies = await res.json();
        renderCompanies();

    } catch (err) {
        console.error(err);
        const listEl = document.getElementById("companyList");
        if (listEl) listEl.innerHTML = `<tr><td colspan="6">Lỗi tải dữ liệu</td></tr>`;
    }
}

// =====================
// Render bảng công ty
// =====================
function renderCompanies() {
    const listEl = document.getElementById("companyList");
    if (!listEl) return;

    listEl.innerHTML = "";

    const filtered = allCompanies.filter(c => c && c.status === currentStatus);

    if (!filtered.length) {
        const tr = document.createElement("tr");
        const td = document.createElement("td");
        td.colSpan = 6;
        td.textContent = "Không có dữ liệu";
        tr.appendChild(td);
        listEl.appendChild(tr);
        return;
    }

    filtered.forEach(c => {
        const tr = document.createElement("tr");

        // Tên
        const tdName = document.createElement("td");
        tdName.textContent = c.companyName ?? "-";
        tr.appendChild(tdName);

        // Email
        const tdEmail = document.createElement("td");
        tdEmail.textContent = c.email ?? "-";
        tr.appendChild(tdEmail);

        // Địa chỉ
        const tdAddress = document.createElement("td");
        tdAddress.textContent = c.companyAddress ?? "-";
        tr.appendChild(tdAddress);

        // Quy mô
        const tdSize = document.createElement("td");
        tdSize.textContent = c.companySize ?? "-";
        tr.appendChild(tdSize);

        // Trạng thái
        const tdStatus = document.createElement("td");
        tdStatus.textContent = c.status ?? "-";
        tdStatus.className = "status-badge " + statusClass(c.status);
        tr.appendChild(tdStatus);

        // Hành động
        const tdAction = document.createElement("td");
        const btn = document.createElement("button");
        btn.className = "action-btn btn-detail";
        btn.textContent = "Chi tiết";
        btn.addEventListener("click", () => viewCompany(c.id));
        tdAction.appendChild(btn);
        tr.appendChild(tdAction);

        listEl.appendChild(tr);
    });
}

// =====================
// Class màu trạng thái
// =====================
function statusClass(status) {
    switch(status){
        case "Đang chờ": return "status-pending";
        case "Đã duyệt": return "status-approved";
        case "Từ chối": return "status-rejected";
        default: return "";
    }
}

// =====================
// Tab trạng thái
// =====================
function initTabs() {
    document.querySelectorAll(".tab-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            document.querySelectorAll(".tab-btn").forEach(b => b.classList.remove("active"));
            btn.classList.add("active");
            currentStatus = btn.dataset.status ?? "Đang chờ";
            renderCompanies();
        });
    });
}

// =====================
// Xem chi tiết công ty
// =====================
function viewCompany(id) {
    if (!id) return;

    // Set global companyId để trang detail dùng
    window.companyId = id;

    // Nếu bạn SPA, xóa jobId cũ (nếu muốn)
    window.jobId = null;

    // Chuyển sang trang chi tiết công ty
    loadPage(`/pages/admin-company-detail.html?id=${id}`);
}
