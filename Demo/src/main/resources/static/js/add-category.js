// add-category.js

window.initAddCategory = function () {
    const btnAdd = document.getElementById("btnAddCategory");
    const nameInput = document.getElementById("Name");

    if (!btnAdd || !nameInput) return;

    btnAdd.onclick = async () => {
        const name = nameInput.value.trim();

        if (!name) {
            alert("Vui lòng nhập tên ngành nghề");
            nameInput.focus();
            return;
        }

        try {
            const res = await fetch(`${API_BASE}/category/createCategory`, {
                method: "POST",
                headers: {
                    ...authHeader(),
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ name })
            });

            if (!res.ok) throw new Error("Tạo ngành nghề thất bại");

            alert("Thêm ngành nghề thành công ");

            loadPage("/pages/admin-home.html");

        } catch (err) {
            console.error(err);
            alert("Không thể thêm ngành nghề ");
        }
    };
};
