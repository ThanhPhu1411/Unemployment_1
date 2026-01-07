window.loadAdminData = async function() {
  await loadCategories();
  await loadJobTypes();
};

async function loadCategories() {
  const categoryListEl = document.getElementById("categoryList");
  if (!categoryListEl) return;

  categoryListEl.innerHTML = '<tr><td colspan="2">Đang tải...</td></tr>';
  try {
    const res = await fetch(`${API_BASE}/category/list`, { headers: authHeader() });
    if (!res.ok) throw new Error("Lỗi lấy danh sách ngành");
    const categories = await res.json();

    if (!categories.length) {
      categoryListEl.innerHTML = '<tr><td colspan="2">Chưa có dữ liệu</td></tr>';
      return;
    }

    categories.forEach(cat => {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${cat.name}</td>
        <td>
          <button class="action-btn btn-edit" onclick="editCategory(${cat.id}, '${cat.name}')">Sửa</button>
          <button class="action-btn btn-delete" onclick="deleteCategory(${cat.id})">Xóa</button>
        </td>
      `;
      categoryListEl.appendChild(tr);
    });

  } catch (e) {
    console.error(e);
    categoryListEl.innerHTML = '<tr><td colspan="2">Lỗi tải dữ liệu</td></tr>';
  }
}

async function loadJobTypes() {
  const jobTypeListEl = document.getElementById("jobTypeList");
  if (!jobTypeListEl) return;

  jobTypeListEl.innerHTML = '<tr><td colspan="2">Đang tải...</td></tr>';
  try {
    const res = await fetch(`${API_BASE}/JobType`, { headers: authHeader() });
    if (!res.ok) throw new Error("Lỗi lấy danh sách JobType");
    const jobTypes = await res.json();

    if (!jobTypes.length) {
      jobTypeListEl.innerHTML = '<tr><td colspan="2">Chưa có dữ liệu</td></tr>';
      return;
    }

    jobTypes.forEach(jt => {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${jt.name}</td>
        <td>
          <button class="action-btn btn-edit" onclick="editJobType(${jt.id}, '${jt.name}')">Sửa</button>
          <button class="action-btn btn-delete" onclick="deleteJobType(${jt.id})">Xóa</button>
        </td>
      `;
      jobTypeListEl.appendChild(tr);
    });

  } catch (e) {
    console.error(e);
    jobTypeListEl.innerHTML = '<tr><td colspan="2">Lỗi tải dữ liệu</td></tr>';
  }
}
