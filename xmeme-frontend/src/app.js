let log = console.log;
const xmemeBackendUrl =
  "https://pettywaterycookie.anugrahsinghal.repl.co/memes";
let editFormWrapper = document.querySelector(".create-update-form-obj");
let memeForm = document.forms["meme-create-update-form"];
memeForm.addEventListener("click", (e) => e.preventDefault());
memeForm["url"].addEventListener("change", renderPreview);
function clearForm() {
  let inputs = memeForm.getElementsByTagName("input");
  inputs["name"].value = "";
  inputs["url"].value = "";
  inputs["caption"].value = "";
  let renderTarget = document.querySelector("#image-render");
  renderTarget.setAttribute("src", "src/placeholder-xmeme.jpg");
}

function renderPreview(event) {
  let urlToRender = event.target.value;
  renderImageInForm(urlToRender);
}
function renderImageInForm(urlToRender) {
  log("url to show" + urlToRender);
  let renderTarget = document.querySelector("#image-render");
  log("before src set" + renderTarget);
  renderTarget.setAttribute("src", urlToRender);
  log("after src set" + renderTarget.getAttribute("src"));
}

let editCreatePreview = document.querySelector(".form-container-with-preview");
let closeFormButton = document.querySelector("#cancel-form");
closeFormButton.addEventListener("click", () => {
  editCreatePreview.style.display = "none";
  document.querySelector("#create-meme-iframe").style.display = "unset";
  modifyElemenetsWithClassHidden("none");
  clearForm();
});

function modifyElemenetsWithClassHidden(displayState) {
  document.querySelectorAll(".hidden").forEach((item) => {
    item.style.display = displayState;
  });
}

let submitBtn = document.querySelector("#submit-data");
submitBtn.addEventListener("click", sendDataAndReload);
function sendDataAndReload(event) {
  memeForm.addEventListener("click", (e) => {
    sendDataToPersist();
    clearForm();
  });
}

function showEditForm(event) {
  console.log("show edit form" + event);
  /* save meme id to local storage */
  let memeToBeEdited = event.target.id;
  log("element captured = " + memeToBeEdited);
  localStorage.setItem("memeId", memeToBeEdited);
  log("meme-id" + localStorage.getItem("memeId"));
  let heading = document.querySelector(".edit-create");
  heading.innerText = "edit meme";
  renderPreviewOfImageToBeEdited(event);
  editCreatePreview.style.display = "unset";
  scroll(0, 0);
}

function renderPreviewOfImageToBeEdited(event) {
  let children = event.target.parentNode.parentNode.children;
  let existingImgUrl = children[0].src;

  renderImageInForm(existingImgUrl);
}


// "http://localhost:8082/memes";

function sendDataToPersist() {
  let inputs = document.forms["meme-create-update-form"].getElementsByTagName(
    "input"
  );
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

loadMemes();
// load memes
const cardTemplate = `
    {{#each memes}}
    <div class="meme-view-container">
        <div class="image-edit-container" id={{id}}>
          <img
            src="{{url}}"
            alt="Image could not be loaded"
            class="image-to-edit"
            onerror="this.onerror=null; this.src='src/placeholder-xmeme.jpg'"
          />
          <div class="image-edit-view-overlay">
            <div class="text rounded-corners edit-meme-btn" id={{id}}>
              <i class="fa fa-pencil"></i>Edit
            </div>            
            <div class="text rounded-corners open-meme-btn" id={{id}}>
              Open
            </div>
          </div>
        </div>
        <ul>
          <li class="meme-author">
          <i class="fa fa-user-circle-o" aria-hidden="true"></i>
            <span id="meme-author-name">
              {{name}}
            </span>
          </li>
          <!-- br -->
          <li class="meme-caption">
          <i class="fa fa-commenting-o" aria-hidden="true"></i>
            <span>
              {{caption}}
            </span>
          </li>
        </ul>
        <!--p class="edit-meme-btn rounded-corners" id={{id}}>
          <button id={{id}}>Edit</button>
        </p-->
      </div>
      {{/each}}
      `;

const template = Handlebars.compile(cardTemplate);

function loadMemes() {
  fetch(encodeURI(xmemeBackendUrl))
    .then((response) => {
      log(response.body);
      log(response);
      if (response.ok) {
        console.log("Memes Loaded Successfully");
        return response.json();
      } else {
        throw new Error(response.status);
      }
    })
    .then((data) => {
      log(JSON.stringify(data));
      let cards = template({ memes: data });
      // log(cards);
      document.querySelector(".meme-post-wrapper").innerHTML = cards;
    })
    .then(() => {
      log("rendering complete");
      attachButtonsToTheLoadedMemes();
      log("attached buttons");
      setThemeForWindow();
    })
    .catch((error) => {
      console.error("Error while Loading memes:", error);
      //   location.reload();
    });
}
/* #region  blur-image-on-edit-actions */
/* function doBlurImage(event) {
  log('edit-meme-overlay mouseover')
  log(event.target.parentNode.children[0])
  event.target.parentNode.children[0].style.filter = 'blur(2px)';
}
function removeBlurImage(event) {
  log('edit-meme-overlay mouseout')
  log(event.target.parentNode.children[0])
  // event.target.parentNode.children[0].style.filter = 'none';
}

function doBlurImageBtn(event) {
  log('edit-meme-btn mouseover')
  log(event.target.parentNode.children[0].style)
  event.target.parentNode.children[0].style.filter = 'blur(2px)';
} */
/* #endregion */

function attachButtonsToTheLoadedMemes() {
  log("attach buttons");

  let editMemeBtns = document.querySelectorAll(".edit-meme-btn");
  let openMemeBtns = document.querySelectorAll(".open-meme-btn");
  log(editMemeBtns.length);
  log(openMemeBtns.length);
  /* #region  blur image on edit */

  // document.querySelectorAll('.image-edit-view-overlay').forEach((item) => {
  //   // item.parentNode.children[0].style.filter = 'blur(2px)';
  //   item.addEventListener('mouseover', doBlurImage);
  //   item.addEventListener('mouseout', removeBlurImage);
  //   // item.addEventListener('mouseenter', doBlurImage);
  // }, false);

  // editMemeBtns.forEach((item) => {
  //   item.addEventListener('mouseover', doBlurImageBtn);
  //   // item.addEventListener('mouseenter', doBlurImage);
  // });

  /* #endregion */

  editMemeBtns.forEach((item) => {
    item.addEventListener("click", showEditForm);
  });
  openMemeBtns.forEach((item) => {
    item.addEventListener("click", openMemeInNewPage);
  });
}
function openMemeInNewPage(event) {
  window.location.assign("meme.html?q=" + event.target.id);
}

// add new meme
let creationIframeLink = document
  .querySelectorAll(".create-meme-iframe-link")
  .forEach((item) => {
    item.addEventListener("click", showCreateForm);
  });
function showCreateForm() {
  modifyElemenetsWithClassHidden("unset");
  editCreatePreview.style.display = "unset";
  let heading = document.querySelector(".edit-create");
  heading.innerText = "create meme";
  scroll(0, 0);
  document.querySelector("#create-meme-iframe").style.display = "none";
}

function makePatchRequest(inputs) {
  // take input
  console.group("patch");
  console.log(inputs["caption"].value);
  console.log(inputs["url"].value);
  if (checkDataValidity(inputs, "patch")) {
    let editMemeData = {};
    if (inputs["caption"].value != "")
      editMemeData.caption = inputs["caption"].value;
    if (inputs["url"].value != "") editMemeData.url = inputs["url"].value;
    console.log(JSON.stringify(editMemeData));
    console.log("Edititng Meme " + localStorage.getItem("memeId"));
    const PATCH_URL = xmemeBackendUrl + "/" + localStorage.getItem("memeId");
    log("PATCH_URL => ", PATCH_URL);
    fetch(encodeURI(PATCH_URL), {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
        charset: "UTF-8",
      },
      body: JSON.stringify(editMemeData),
    })
      .then((response) => {
        log(response.body);
        log(response);
        log(response.status);
        if (response.status === 204 || response.status === "204") {
          console.log("Meme Edited Successfully");
          triggerIframe("Meme Edited Successfully");
        } else if (response.status === 404 || response.status === "404") {
          console.error("Meme Not Found");
          triggerIframe("Meme Not Found");
        }
      })
      .then(() => {
        // location.reload();
      })
      .catch((error) => {
        console.error("Error while editing meme:", error);
        triggerIframe(false);
        // location.reload();
      });
  }

  console.groupEnd();
  console.log("patch end");
}

function triggerIframe(message) {
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
    location.reload();
  }, 2000);
}

function makePostRequest() {
  console.group("createMeme makePostRequest");
  let inputs = memeForm.getElementsByTagName("input");

  let inputname = inputs["name"].value;
  let inputcaption = inputs["caption"].value;
  let inputUrl = inputs["url"].value;

  console.table(inputs);

  if (checkDataValidity(inputs, "create")) {
    const memeData = {
      name: inputname,
      url: inputUrl,
      caption: inputcaption,
    };

    console.table(memeData);

    fetch(encodeURI(xmemeBackendUrl), {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        charset: "UTF-8",
      },
      body: JSON.stringify(memeData),
    })
      .then((response) => {
        console.dir(response);
        if (response.status === 409 || response.status === "409") {
          console.error("Duplicate Meme Posted");
          triggerIframe("Duplicate Data");
        } else if (!response.ok) {
          console.error("Error with Server Response : " + response.status);
          triggerIframe("Something went wrong! Please try again.");
        } else if (response.ok || response.status === 200) {
          console.log("meme posted");
          triggerIframe("Meme Created!");
          return response.json();
        }
      })
      .then((data) => {
        console.log("Meme Posted Successfully:", JSON.stringify(data));
      })
      .then(() => {
        // location.reload();
      })
      .catch((error) => {
        triggerIframe("Something went wrong! Please try again.");
        console.error("Error while posting meme:", error);
        // location.reload();
      });
  }
  console.groupEnd();
}

function checkDataValidity(inputs, operationType) {
  let inputname = inputs["name"].value;
  let inputcaption = inputs["caption"].value;
  let inputUrl = inputs["url"].value;
  if (operationType === "create") {
    if (inputname === "" || inputcaption === "" || inputUrl === "") {
      triggerIframe("Invalid Data Provided. All feeds are required.");
      return false;
    }
  } else {
    if (
      (inputcaption === "" && inputUrl === "") ||
      localStorage.getItem("memeId") === "" ||
      localStorage.getItem("memeId") === undefined
    ) {
      triggerIframe("Invalid Data.");
      return false;
    }
  }
  return true;
}
