document.querySelector(".toggle-theme").addEventListener("click", toggleTheme);
setThemeForWindow();
function setThemeForWindow() {
  if (localStorage.getItem("theme") === "dark" || localStorage.getItem("theme") === undefined) {
    goDark();
  } else {
    letThereBeLight();
  }
}

function toggleTheme() {
  log("theme click");
  if (localStorage.getItem("theme") === "dark") {
    letThereBeLight();
  } else {
    goDark();
  }
}

function goDark() {
  localStorage.setItem("theme", "dark");
  document.body.classList.add("dark");
  document.querySelector(".wrapper").classList.add("dark");
  document.querySelectorAll(".meme-view-container").forEach((item) => {
    log(item.classList);
    item.classList.add("dark");
  });
}

function letThereBeLight() {
  localStorage.setItem("theme", "light");
  document.body.classList.remove("dark");
  document.querySelector(".wrapper").classList.remove("dark");
  document.querySelectorAll(".meme-view-container").forEach((item) => {
    log(item.classList);
    item.classList.remove("dark");
  });
}


