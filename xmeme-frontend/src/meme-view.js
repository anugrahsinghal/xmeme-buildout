document
  .querySelector(".generate-qr")
  .addEventListener("click", generateAndRenderQrCode);
function generateAndRenderQrCode() {
  let finalURL =
    "https://chart.googleapis.com/chart?cht=qr&chl=" +
    "Hey! Check out this cool meme I made! on " +
    encodeURI(window.location.href) +
    "&chs=160x160&chld=L|0";
  log(finalURL);
  document.querySelector(".qr-code").setAttribute("src", finalURL);
  document.querySelector(".qr-code").style = "visibility: visible;";
}

getCurrentWindowData();

function getCurrentWindowData() {
  const memeId = window.location.search.substr(3);
  log(memeId);
  loadMeme(memeId);
}

function loadMeme(memeId) {
  let memeURL = xmemeBackendUrl + "/" + memeId;
  fetch(encodeURI(memeURL))
    .then((response) => {
      log(response.body);
      log(response);
      if (response.ok) {
        console.log("Meme Loaded Successfully");
        return response.json();
      } else {
        throw new Error(response.status);
      }
    })
    .then((data) => {
      log(JSON.stringify(data));
      showMemeViewForm(data);
    })
    .catch((error) => {
      console.error("Error while Loading memes:", error);
    });
}
