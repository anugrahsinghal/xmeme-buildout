let editFormWrapper = document.querySelector(".create-update-form-obj");

let editOrCreateFormContainer = document.querySelector(
  ".form-container-with-preview"
);
let closeFormButton = document.querySelector("#cancel-form");
attachCloseButtonToForm();

// prevent form reload
memeForm.addEventListener("click", (e) => e.preventDefault());

// on image url entered , try to load it
memeForm["url"].addEventListener("change", renderPreview);

document.querySelectorAll(".create-meme-iframe-link").forEach((item) => {
  item.addEventListener("click", showFormToCreateMeme);
});

/**
 * Removed hidden class from the form
 * Sets form heading
 * hides the floating create button
 * scrolls screen to the top, where form is present
 */
function showFormToCreateMeme() {
  modifyElemenetsWithClassHidden("unset");
  editOrCreateFormContainer.style.display = "unset";
  changeFormHeading("Create Meme");
  hideFloatingCreateButton();
  scroll(0, 0); //  scroll to top
}

/**
 * Saves id of meme to be edited to localStorage
 * Sets form heading for edit
 * sets/renders the image src to form of the meme being edited
 * Shows the form
 * scrolls screen to the top, where form is present
 *
 * @param {*} event helps to capture id of the meme that need to be edited
 */
function showFormToEditMeme(event) {
  console.log("show edit form" + event);
  saveMemeIdToLocalStorage(event);
  changeFormHeading("Edit Meme");
  renderPreviewOfImageBeingEdited(event);
  editOrCreateFormContainer.style.display = "unset";
  scroll(0, 0); // scroll to top
}

/**
 * This shows a non-editable view of the form
 * sets the input fields as provide by data variable
 * sets cursor style to not allowed
 * @param {*} data the data to be renders in the form
 */
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
// hides the floating create button
function hideFloatingCreateButton() {
  document.querySelector("#create-meme-iframe").style.display = "none";
}
/**
 * Saves the id of the target to localStorage
 * @param {*} event the meme/card target
 */
function saveMemeIdToLocalStorage(event) {
  let memeToBeEdited = event.target.id;
  log("element captured = " + memeToBeEdited);
  localStorage.setItem("memeId", memeToBeEdited);
  log("meme-id" + localStorage.getItem("memeId"));
}

/**
 *
 * @param {*} heading to Change heading of form
 */
function changeFormHeading(heading) {
  let formHeading = document.querySelector(".edit-create");
  formHeading.innerText = heading;
}

/**
 *
 * @param {*} event get image source of the target
 * render that url to form
 */
function renderPreviewOfImageBeingEdited(event) {
  let children = event.target.parentNode.parentNode.children;
  let existingImgUrl = children[0].src;

  renderImageInForm(existingImgUrl);
}

/**
 * sets form image src to the url provided
 * @param {*} urlToRender the url to be rendered
 */
function renderImageInForm(urlToRender) {
  log("url to show" + urlToRender);
  let renderTarget = document.querySelector("#image-render");
  log("before src set" + renderTarget);
  renderTarget.setAttribute("src", urlToRender);
}

/**
 * sets the form image to the new url when meme is being edited/created
 * @param {*} event the url of the image being edited
 */
function renderPreview(event) {
  let urlToRender = event.target.value;
  renderImageInForm(urlToRender);
}

