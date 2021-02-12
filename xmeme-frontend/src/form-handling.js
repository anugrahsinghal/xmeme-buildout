let log = console.log;
const xmemeBackendUrl =  "https://pettywaterycookie.anugrahsinghal.repl.co/memes";

let editFormWrapper = document.querySelector(".create-update-form-obj");
let memeForm = document.forms["meme-create-update-form"];
let editOrCreateFormContainer = document.querySelector(
  ".form-container-with-preview"
);
let closeFormButton = document.querySelector("#cancel-form");
attachCloseButtonToForm();

memeForm.addEventListener("click", (e) => e.preventDefault());
memeForm["url"].addEventListener("change", renderPreview);

document.querySelectorAll(".create-meme-iframe-link").forEach((item) => {
  item.addEventListener("click", showFormToCreateMeme);
});

function clearForm() {
  let inputs = memeForm.getElementsByTagName("input");
  inputs["name"].value = "";
  inputs["url"].value = "";
  inputs["caption"].value = "";
  let renderTarget = document.querySelector("#image-render");
  renderTarget.setAttribute("src", "src/placeholder-xmeme.jpg");
}

/* #region  show/hide form */

function showFormToCreateMeme() {
  modifyElemenetsWithClassHidden("unset");
  editOrCreateFormContainer.style.display = "unset";
  changeFormHeading("Create Meme");
  hideFloatingCreateButton();
  scroll(0, 0); //  scroll to top
}

function showFormToEditMeme(event) {
  console.log("show edit form" + event);
  saveMemeIdToLocalStorage(event);
  changeFormHeading("Edit Meme");
  renderPreviewOfImageBeingEdited(event);
  editOrCreateFormContainer.style.display = "unset";
  scroll(0, 0); // scroll to top
}

function showMemeViewForm(data) {
  console.table(data);
  let inputs = memeForm.getElementsByTagName("input");

  document.querySelector("#image-render").setAttribute("src", data.url);

  inputs["name"].value = data.name;
  inputs["url"].value = data.url;
  inputs["caption"].value = data.caption;

  inputs["name"].style = "cursor: not-allowed;";
  inputs["url"].style = "cursor: not-allowed;";
  inputs["caption"].style = "cursor: not-allowed;";
}

/* #endregion */

function hideFloatingCreateButton() {
  document.querySelector("#create-meme-iframe").style.display = "none";
}
function saveMemeIdToLocalStorage(event) {
  let memeToBeEdited = event.target.id;
  log("element captured = " + memeToBeEdited);
  localStorage.setItem("memeId", memeToBeEdited);
  log("meme-id" + localStorage.getItem("memeId"));
}

function changeFormHeading(heading) {
  let formHeading = document.querySelector(".edit-create");
  formHeading.innerText = heading;
}

function renderPreviewOfImageBeingEdited(event) {
  let children = event.target.parentNode.parentNode.children;
  let existingImgUrl = children[0].src;

  renderImageInForm(existingImgUrl);
}

function renderImageInForm(urlToRender) {
  log("url to show" + urlToRender);
  let renderTarget = document.querySelector("#image-render");
  log("before src set" + renderTarget);
  renderTarget.setAttribute("src", urlToRender);
}

function renderPreview(event) {
  let urlToRender = event.target.value;
  renderImageInForm(urlToRender);
}

function attachSubmitButtonToForm() {
  let submitBtn = document.querySelector("#submit-data");
  submitBtn.addEventListener("click", sendDataAndReload);
}

function sendDataAndReload(event) {
  memeForm.addEventListener("click", (e) => {
    sendDataToPersist();
    clearForm();
  });
}
function sendDataToPersist() {
  let inputs = memeForm.getElementsByTagName("input");
  let inputname = inputs["name"].value;
  log("inputname => " + inputname);
  if (inputname === "") {
    log("makePatchRequest");
    makePatchRequest(inputs);
  } else {
    log("makePostRequest");
    makePostRequest(inputs);
  }
}

function attachCloseButtonToForm() {
  closeFormButton.addEventListener("click", () => {
    editOrCreateFormContainer.style.display = "none";
    document.querySelector("#create-meme-iframe").style.display = "unset";
    modifyElemenetsWithClassHidden("none");
    clearForm();
  });
}

function modifyElemenetsWithClassHidden(displayState) {
  document.querySelectorAll(".hidden").forEach((item) => {
    item.style.display = displayState;
  });
}
