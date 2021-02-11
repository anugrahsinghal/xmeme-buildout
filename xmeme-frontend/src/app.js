let log = console.log;
let editFormWrapper = document.querySelector(".create-update-form-obj");
let memeForm = document.forms["meme-create-update-form"];
memeForm.addEventListener("click", (e) => e.preventDefault());
memeForm['url'].addEventListener('change', renderPreview)

function clearForm() {
  let inputs = memeForm.getElementsByTagName('input')
  inputs['name'].value = ""
  inputs['url'].value = ""
  inputs['caption'].value = ""
  let renderTarget = document.querySelector('#image-render')
  renderTarget.setAttribute('src','src/placeholder-xmeme.jpg')
}

function renderPreview(event) {
  let urlToShow = event.target.value
  
  log("url to show" + urlToShow)
  let renderTarget = document.querySelector('#image-render')
  log("before src set" + renderTarget)
  renderTarget.setAttribute('src',urlToShow)
  log("after src set" + renderTarget.getAttribute('src'))
}

let editCreatePreview = document.querySelector(".form-container-with-preview");
let cancelBtn = document.querySelector("#cancel-form");
cancelBtn.addEventListener("click", () => {
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
  console.log(event);
  /* save meme id to local storage */
  let memeToBeEdited = event.target.id
  log("element captured = " + memeToBeEdited)
  localStorage.setItem("memeId", memeToBeEdited);
  log("meme-id" + localStorage.getItem("memeId"));
  let heading = document.querySelector('.edit-create')
  heading.innerText = 'edit meme'

  editCreatePreview.style.display = "unset";
  scroll(0, 0);
}

const xmemeBackendUrl =
  "https://pettywaterycookie.anugrahsinghal.repl.co/memes";
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
        <img
          src="{{url}}"
          alt="Image could not be loaded"
          onerror="this.onerror=null; this.src='src/placeholder-xmeme.jpg'"
        />
        <ul>
          <li class="meme-author">
            <i class="fa fa-user"></i>
            <span id="meme-author-name">
              {{name}}
            </span>
          </li>
          <br>
          <li class="meme-caption">
            <i class="fa fa-pencil"></i>
            <span>
              {{caption}}
            </span>
          </li>
        </ul>
        <p class="edit-meme-btn rounded-corners" id={{id}}>
          <button id={{id}}>Edit</button>
        </p>
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
    })
    .catch((error) => {
      console.error("Error while Loading memes:", error);
      //   location.reload();
    });
}
function attachButtonsToTheLoadedMemes() {
  log("attach buttons");

  let editMemeBtns = document.querySelectorAll(".edit-meme-btn");
  log(editMemeBtns.length);

  editMemeBtns.forEach((item) => {
    item.addEventListener("click", showEditForm);
  });
}

// add new meme
let creationIframeLink = document.querySelectoAll(".create-meme-iframe-link").forEach(item => {
  item.addEventListener("click", showCreateForm);
});
function showCreateForm() {
  modifyElemenetsWithClassHidden("unset");
  editCreatePreview.style.display = "unset";
  let heading = document.querySelector('.edit-create')
  heading.innerText = 'create meme'
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
