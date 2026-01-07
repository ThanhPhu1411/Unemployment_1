// add-category.js

window.initAddJobType = function () {
    const btnAdd = document.getElementById("btnAddJobType");
    const nameInput = document.getElementById("Name");

    if (!btnAdd || !nameInput) return;

    btnAdd.onclick = async () => {
        const name = nameInput.value.trim();

        if (!name) {
            alert("Vui lòng nhập hình thức làm việc");
            nameInput.focus();
            return;
        }

        try {
            const res = await fetch(`${API_BASE}/JobType/createJobType`, {
                method: "POST",
                headers: {
                    ...authHeader(),
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ name })
            });

            if (!res.ok) throw new Error("Tạo hình thức làm việc thất bại");

            alert("Thêm hình thức làm việc thành công ");

            loadPage("/pages/admin-home.html");

        } catch (err) {
            console.error(err);
            alert("Không thể thêm");
        }
    };
};
