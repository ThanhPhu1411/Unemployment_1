function fmtDate(iso) {
if (!iso) return "";
const d = new Date(iso);
const dd = String(d.getDate()).padStart(2, "0");
const mm = String(d.getMonth() + 1).padStart(2, "0");
const yy = d.getFullYear();
return `${dd}/${mm}/${yy}`;
}

function fillNotification(noti)
{
const container = document.getElementById("notification-list");
const template = document.getElementById("notification-template");
container.innerHTML = "";

if (noti.length === 0) {
document.getElementById("empty-notification").style.display = "block";
return;
} else {
document.getElementById("empty-notification").style.display = "none";
}
noti.forEach(noti => {
const clone = template.content.cloneNode(true);

clone.querySelector(".notification-title").textContent = noti.title;
clone.querySelector(".notification-message").innerHTML = noti.message || "";

clone.querySelector(".notification-date").textContent = fmtDate(noti.sentDate || "");

const card = clone.querySelector(".notification-card");


if (!noti.read) {
card.classList.add("unread");
}

card.addEventListener("click", () => {
        card.classList.remove("unread");
       openNotificationDetail(noti.id);

   });

container.appendChild(clone);
});

}

function loadNoti()
{
fetch(API_BASE + "/notifications" ,{
method:"GET",
headers: {
"Content-Type": "application/json",
...authHeader()
}
})
.then(function(res){
if(!res.ok)
{
alert("Không thể lấy thông báo");
throw new Error("Không thể lấy thông báo");
}
return res.json();
})
.then(function(data){
fillNotification(data)
})
.catch(err => {
console.error("Lỗi load thông báo:", err);
});
}
window.openNotificationDetail = function (notiId) {
window.currentNotificationId = notiId;

loadPage("/pages/notificationdetail.html")
.then(() => {
  return fetch(API_BASE + "/notifications/" + notiId, {
    headers: {
      ...authHeader()
    }
  });
})
.then(res => {
  if (!res.ok) throw new Error("Không đọc được thông báo");
  return res.json();
})
.then(data => {
  loadNotificationDetail(data);
})
.catch(err => {
  console.error("Lỗi load notification detail:", err);
});
};

function loadNotificationDetail(data) {
document.getElementById("noti-title").textContent = data.title;
document.getElementById("noti-message").innerHTML = data.message;
document.getElementById("noti-date").textContent = fmtDate(data.sentDate);
}



