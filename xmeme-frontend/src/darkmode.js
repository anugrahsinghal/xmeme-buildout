// this file deals with the functionality for dark and light theme switching


// attach click listener to toggle theme button
document.querySelector(".toggle-theme").addEventListener("click", toggleTheme);

setThemeForWindow();

// set theme for window
// in case no theme is set then use dark mode as default theme
function setThemeForWindow() {
  if (localStorage.getItem("theme") === "dark" || localStorage.getItem("theme") === undefined) {
    goDark();
  } else {
    letThereBeLight();
  }
}
/**
 * change theme on click
 * If the window is dark then go light mode else go to dark mode
 */
function toggleTheme() {
  log("theme click");
  if (localStorage.getItem("theme") === "dark") {
    letThereBeLight();
  } else {
    goDark();
  }
}

/**
 * add 'dark' class to the elements that need a color change
 */
function goDark() {
  localStorage.setItem("theme", "dark");
  document.body.classList.add("dark");
  document.querySelector(".wrapper").classList.add("dark");
  document.querySelectorAll(".meme-view-container").forEach((item) => {
    // console.log(item.classList);
    item.classList.add("dark");
  });
}

/**
 * remove the 'dark' class from elements that need color change
 */
function letThereBeLight() {
  localStorage.setItem("theme", "light");
  document.body.classList.remove("dark");
  document.querySelector(".wrapper").classList.remove("dark");
  document.querySelectorAll(".meme-view-container").forEach((item) => {
    log(item.classList);
    item.classList.remove("dark");
  });
}


