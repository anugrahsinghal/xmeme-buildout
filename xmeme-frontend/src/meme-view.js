getMemeIdFromCurrentWindowAndLoad();

document
  .querySelector(".generate-qr")
  .addEventListener("click", generateAndRenderQrCode);

document
  .querySelector("#delete-meme")
  .addEventListener("click", deleteMemeRequest);

/**
 * this is a callback function to the delete button on the display form
 * it fetch the id of the meme to be deleted from the url of the page
 * makes a fetch request to the backend to delete the meme
 * only status 204 is considered a success, all else are failures
 * triggers Notification for both success and failure scenarios
 */
function deleteMemeRequest() {
  const memeId = window.location.search.substr(3);
  fetch(encodeURI(xmemeBackendUrl + "/" + memeId), {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
      charset: "UTF-8",
    },
  })
    .then((response) => {
      log(response);
      if (response.status === 204 || response.status === "204") {
        triggerIframe("Meme Deleted");
        log("Meme Deleted Successfully");
      } else {
        throw new Error(response.status);
      }
    })
    .catch((error) => {
      console.error("Error while editing meme:", error);
      triggerIframe("Error Occured");
    });
}
/**
 * this is a callback function to the Generate QR button on the display form
 * it uses a google chart API to form the image source
 * It then sets the src property of the img element
 * and then sets it to be visble
 */
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
/**
 * it fetch the id of the meme to be deleted from the url of the page
 * makes a fetch request to the backend to load the meme for that id
 */
function getMemeIdFromCurrentWindowAndLoad() {
  const memeId = window.location.search.substr(3);
  log(memeId);
  loadMeme(memeId);
}

/**
 * This function calls the backend URL to get details of the meme with the provided meme id
 * and renders it to the form
 * @param {*} memeId unique Id of meme
 */
function loadMeme(memeId) {
  let memeURL = xmemeBackendUrl + "/" + memeId;
  fetch(encodeURI(memeURL))
    .then((response) => {
      log(response.body);
      log(response);
      if (response.ok) {
        log("Meme Loaded Successfully");
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
