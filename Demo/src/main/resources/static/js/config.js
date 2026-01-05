// js/config.js
window.API_BASE = "http://localhost:8083";
function authHeader() {
  const token = localStorage.getItem("token");
  return token ? { Authorization: "Bearer " + token } : {};
}