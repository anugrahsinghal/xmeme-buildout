/**
 * URL to communicate with the backend server
 */
const xmemeBackendUrl =
  "https://pettywaterycookie.anugrahsinghal.repl.co/memes";
/**
 * the meme for to edit or create meme
 */
let memeForm = document.forms["meme-create-update-form"];
let log = console.log;

/**
 * This function is triggerd on create/edit/delete of the meme
 * It recieves message from the functions and display that message as a notification
 * It also functions to reload the page after a delay of 2 seconds so that user can read notification
 * after which it again goes back to hidden state
 * @param {*} message The Message to be displayed in the Notification Frame
 * @param {*} reload - reloads full page on true or undefined | does not reload on false
 */
function triggerIframe(message, reload) {
  console.log("IFRAME TRIGGER " + message);
  let notification = document.querySelector("#meme-notification");
  let notificationMsg = document.querySelector("#meme-notification-message");
  notificationMsg.innerText = message + "[Page will reload]";

  notification.style.display = "unset";

  console.log("END IFRAME TRIGGER " + message);

  setTimeout(() => {
    console.log("Hide I frame start");
    let notification = document.querySelector("#meme-notification");
    notification.style.display = "none";
    console.log("Hide I frame complete");
    if (reload === true || reload === undefined) {
      location.reload();
    }
  }, 2000);
}

/**
 * attach click event listener to close button of form
 * on click this shows the floating create button
 * hides element with class hidden
 * clears the form input
 */
function attachCloseButtonToForm() {
  let closeFormButton = document.querySelector("#cancel-form");
  closeFormButton.addEventListener("click", () => {
    editOrCreateFormContainer.style.display = "none";
    document.querySelector("#create-meme-iframe").style.display = "unset";
    modifyElemenetsWithClassHidden("none");
    clearForm();
  });
}

/**
 * set display style of elements with class hidden as provided by displayState variable
 * @param {*} displayState the display state to be set
 */
function modifyElemenetsWithClassHidden(displayState) {
  document.querySelectorAll(".hidden").forEach((item) => {
    item.style.display = displayState;
  });
}

/**
 * Clear the form input variables
 */
function clearForm() {
  let inputs = memeForm.getElementsByTagName("input");
  inputs["name"].value = "";
  inputs["url"].value = "";
  inputs["caption"].value = "";
  let renderTarget = document.querySelector("#image-render");
  renderTarget.setAttribute("src", "src/placeholder-xmeme.jpg");
}

function hideForm() {
  let closeFormButton = document.querySelector("#cancel-form");
  closeFormButton.click();
}
