

function setMsg(id, text, ok = false) {
  const el = document.getElementById(id);
  if (!el) return;
  el.textContent = text || "";
  el.classList.remove("text-danger", "text-success");
  el.classList.add(ok ? "text-success" : "text-danger");
}

function initRegister() {
  const form = document.getElementById("registerForm");
  if (!form) return;

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const role = document.querySelector('input[name="roleType"]:checked')?.value || "user";

    const payload = {
      userName: document.getElementById("regUsername").value.trim(),
      passWord: document.getElementById("regPassword").value
    };

const endpoint = role === "employer"
  ? "/users/createEmployers"
  : "/users/createUsers";

    try {
      const res = await fetch(API_BASE + endpoint, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      });

      const data = await res.json().catch(() => null);

      if (!res.ok) {
        setMsg("registerMsg", data?.message || "Đăng ký thất bại", false);
        return;
      }

      setMsg("registerMsg", "Đăng ký thành công! Chuyển sang đăng nhập…", true);
      setTimeout(() => loadPage("/pages/login.html"), 600);

    } catch (err) {
      setMsg("registerMsg", "Không kết nối được server!", false);
    }
  });
}
function initLogin(){
  const form = document.getElementById("loginForm")
  if(!form) return;
  form.addEventListener("submit", async(e) => {
     e.preventDefault();
  const payload = {
      userName: document.getElementById("username").value.trim(),
      passWord: document.getElementById("password").value
    };
try{
const res = await fetch(API_BASE + "/auth/log-in",{
method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
});
const data = await res.json().catch(() => null);
if(!res.ok){

   setMsg("loginMsg", data?.message || "Sai tài khoản hoặc mật khẩu", false);
   return;
}
  const token = data?.result?.token || data?.token;
      if (token) localStorage.setItem("token", token);
      updateNavbarAuth();
       setMsg("loginMsg", "Đăng nhập thành công!", true);
            setTimeout(() => loadPage("/pages/home.html"), 400);

}catch(e){
  setMsg("loginMsg", "Không kết nối được server!", false);
}
  })
}

function updateNavbarAuth() {
  const loginLink = document.getElementById("nav-login");
  const logoutLink = document.getElementById("logoutLink");

  const token = localStorage.getItem("token");

  if (token) {
    if (loginLink) loginLink.style.display = "none";
    if (logoutLink) logoutLink.style.display = "inline-block";
  } else {
    if (loginLink) loginLink.style.display = "inline-block";
    if (logoutLink) logoutLink.style.display = "none";
  }
}


function initLogout()
{
const logoutLink = document.getElementById("logoutLink");
logoutLink.addEventListener("click" , e=>{
e.preventDefault;
localStorage.removeItem("token");
 updateNavbarAuth();
 loadPage("/pages/home.html");

})
}
