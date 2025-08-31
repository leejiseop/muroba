const navbarItemList = document.querySelectorAll(".navbar-item");
const contentItemList = document.querySelectorAll(".content-item");
document.querySelector("#content-threads").classList.add("content-active");

navbarItemList.forEach((navbarItem) => {
    navbarItem.addEventListener('click', () => {
        navbarItemList.forEach((navbarItem) => navbarItem.classList.remove('navbar-active'));
        navbarItem.classList.add('navbar-active');
        contentItemList.forEach((contentItem) => contentItem.classList.remove('content-active'));
    })
})

document.querySelector("#navbar-threads").addEventListener("click", (item) => {
    document.querySelector("#content-threads").classList.add("content-active");
})
document.querySelector("#navbar-messages").addEventListener("click", (item) => {
    document.querySelector("#content-messages").classList.add("content-active");
})
document.querySelector("#navbar-mypage").addEventListener("click", (item) => {
    document.querySelector("#content-mypage").classList.add("content-active");
})
document.querySelector("#navbar-settings").addEventListener("click", (item) => {
    document.querySelector("#content-settings").classList.add("content-active");
})