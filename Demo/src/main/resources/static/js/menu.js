//async function renderMenu() {
//  const token = localStorage.getItem("token");
//
//  const userMenus = document.querySelectorAll(".menu-user");
//  const employerMenus = document.querySelectorAll(".menu-employer");
//    const adminMenus = document.querySelectorAll(".menu-admin");
//
//  userMenus.forEach(m => m.style.display = "none");
//  employerMenus.forEach(m => m.style.display = "none");
//    adminMenus.forEach(m => m.style.display = "none");
//
//  const loginBtn = document.getElementById("nav-login");
//  const logoutBtn = document.getElementById("logoutLink");
//
//  if (!token) {
//    loginBtn.style.display = "";
//    logoutBtn.style.display = "none";
//    return;
//  }
//
//  loginBtn.style.display = "none";
//  logoutBtn.style.display = "";
//
//  try {
//    const res = await fetch(API_BASE + "/auth/me", {
//      headers: authHeader()
//    });
//
//    if (!res.ok) throw new Error("Không lấy được user");
//
//    const user = await res.json();
//    console.log("USER FROM API:", user);
//      if (user.roles.includes("ROLE_USER")) {
//        userMenus.forEach(m => m.style.display = "");
//      }
//      if (user.roles.includes("ROLE_EMPLOYER")) {
//        employerMenus.forEach(m => m.style.display = "");
//
//    }
//     if (user.roles.includes("ROLE_ADMIN")) {
//          adminMenus.forEach(m => m.style.display = "");
//        }
//  } catch (e) {
//    console.error(e);
//  }
//}
// ===========================
// Hàm tạo header an toàn
// ===========================
function authHeader() {
    const token = localStorage.getItem("token") || "";
    if (!token) return { "Content-Type": "application/json" };

    return {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + token // dùng trực tiếp
    };
}


// ===========================
// Hàm render menu theo role
// ===========================
async function renderMenu() {
    const token = localStorage.getItem("token");

    // Ẩn hết menu trước
    const userMenus = document.querySelectorAll(".menu-user");
    const employerMenus = document.querySelectorAll(".menu-employer");
    const adminMenus = document.querySelectorAll(".menu-admin");

    userMenus.forEach(m => m.style.display = "none");
    employerMenus.forEach(m => m.style.display = "none");
    adminMenus.forEach(m => m.style.display = "none");

    // Login / Logout
    const loginBtn = document.getElementById("nav-login");
    const logoutBtn = document.getElementById("logoutLink");

    if (!token) {
        loginBtn.style.display = "";
        logoutBtn.style.display = "none";
        return;
    }

    loginBtn.style.display = "none";
    logoutBtn.style.display = "";

    try {
        const res = await fetch(`${API_BASE}/auth/me`, {
            headers: authHeader()
        });

        if (!res.ok) throw new Error("Không lấy được user");

        const user = await res.json();
        console.log("USER FROM API:", user);

        // Hiển thị menu theo role
        if (user.roles.includes("ROLE_USER")) {
            userMenus.forEach(m => m.style.display = "");
        }
        if (user.roles.includes("ROLE_EMPLOYER")) {
            employerMenus.forEach(m => m.style.display = "");
        }
        if (user.roles.includes("ROLE_ADMIN")) {
            adminMenus.forEach(m => m.style.display = "");
        }

    } catch (e) {
        console.error("Lỗi renderMenu:", e);
    }
}
