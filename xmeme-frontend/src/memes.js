let log = console.log;

let cardTemplate = `
{{#each memes}}
<div class="card" id={{this.id}}>
<img src="{{url}}"	alt="url"/>
<div class="container">
	<button class="edit-meme">Edit Meme</button><br>
	<h4 class="name">
		<span class="name-title">Posted By: </span><b>{{name}}</b>
	</h4>
	<br>
	<p class="caption">
		<span class="caption-title">Caption: </span>{{caption}}
	</p>
</div>
</div>
{{/each}}
`;

const template = Handlebars.compile(cardTemplate);
// execute the compiled template and print the output to the console
// console.log(template({ doesWhat: "rocks!" }));

// const xmemeBackendUrl = "http://localhost:8082/memes";
const xmemeBackendUrl =
  "https://pettywaterycookie.anugrahsinghal.repl.co/memes";

attachExitOverlayBtn();
handleFormReload();
loadMemes();

function attachExitOverlayBtn() {
  let exitBtn = document.querySelector("#exit-overlay");
  log(exitBtn);
  exitBtn.addEventListener("click", hideOverlayForm);
}

function handleFormReload() {
  var form = document.querySelector("#edit-meme-form");
  form.addEventListener("submit", (event) => event.preventDefault());
}

function loadMemes() {
  let parentContainer = document.querySelector(".parent-meme-container");

  fetch(encodeURI(xmemeBackendUrl))
    .then((response) => {
      log(response.body);
      log(response);
      if (response.ok || response.status === 200 || response.status === "200") {
        console.log("Memes Loaded Successfully");
        parentContainer.innerHTML = "";
        return response.json();
      } else {
        throw new Error(response.status);
      }
    })
    .then((data) => {
      log(JSON.stringify(data));
      let cards = template({ memes: data });
      log(cards);
      parentContainer.innerHTML = cards;
    })
    .then(() => {
      log("rendering complete");
      attachButtonsToTheLoadedMemes();
      log("attached buttons");
    })
    .catch((error) => {
      console.error("Error while Loading memes:", error);
    });
}

function brkLine() {
  let breakLine = document.createElement("br");
  return breakLine;
}

function createSingleMemeCard(memeJSON) {
  let card = makeCard();
  card.setAttribute("id", memeJSON.id);
  card.appendChild(makeImage(memeJSON.url));

  let cardContainer = makeCardContainer();
  cardContainer.appendChild(makeEditMemeButton());
  cardContainer.appendChild(brkLine());
  cardContainer.appendChild(makeHeadingName(memeJSON.name));
  cardContainer.appendChild(brkLine());
  cardContainer.appendChild(makeCaption(memeJSON.caption));

  card.appendChild(cardContainer);

  return card;
}

function makeCard() {
  let card = document.createElement("div");
  card.setAttribute("class", "card");
  return card;
}

function makeCardContainer() {
  let cardContainer = document.createElement("div");
  cardContainer.setAttribute("class", "container");
  // log(cardContainer);
  return cardContainer;
}

function makeEditMemeButton() {
  let editBtn = document.createElement("button");
  editBtn.setAttribute("class", "edit-meme");
  editBtn.innerText = "Edit Meme";

  return editBtn;
}

function makeImage(url) {
  let image = document.createElement("img");
  image.setAttribute("src", url);
  image.setAttribute("alt", "image could not be loaded");
  return image;
}

function makeHeadingName(name) {
  let nameElem = document.createElement("h4");
  nameElem.setAttribute("class", "name");

  let spanElem = document.createElement("span");
  spanElem.setAttribute("class", "name-title");
  spanElem.innerText = "Posted By: ";

  let bold = document.createElement("b");
  bold.innerText = name;

  nameElem.appendChild(spanElem);
  nameElem.appendChild(bold);

  return nameElem;
}

function makeCaption(caption) {
  let captionElem = document.createElement("p");
  captionElem.setAttribute("class", "caption");

  let spanElem = document.createElement("span");
  spanElem.setAttribute("class", "caption-title");
  spanElem.innerText = "Caption: ";

  captionElem.appendChild(spanElem);
  captionElem.innerText = caption;

  return captionElem;
}

function attachButtonsToTheLoadedMemes() {
  log("attach buttons");

  let editMemeBtns = document.querySelectorAll(".edit-meme");
  log(editMemeBtns[0]);

  editMemeBtns.forEach((item) => {
    item.addEventListener("click", editMeme);
  });
}

function editMeme(event) {
  log(event);
  let cardToEdit = event.target.parentElement.parentElement;
  let memeUnqiueId =
    cardToEdit.getAttribute("id") != null ? cardToEdit.getAttribute("id") : 2;
  log(memeUnqiueId);
  showOverlayForm(memeUnqiueId);

  let submitMemeBtn = document.querySelector("#submit-edit-meme-btn");
  submitMemeBtn.addEventListener("click", perforMemeEdit);
}

function showOverlayForm() {
  // maybe fetch and show
  document.querySelector("#overlay").style.display = "flex";

  document.querySelector("#edited-name").value = "";
  document.querySelector("#edited-caption").value = "";
}

function hideOverlayForm() {
  log("hide overlay");
  document.querySelector("#overlay").style.display = "none";
  // clear data
  document.querySelector("#edited-name").value = "";
  document.querySelector("#edited-caption").value = "";
}

function perforMemeEdit() {
  let memeUnqiueId = 2;

  // take input
  let editedName = document.querySelector("#edited-name").value;
  let editedCaption = document.querySelector("#edited-caption").value;

  let editMemeData = {};
  if (editedName != "") {
    editMemeData["name"] = editedName;
  }
  if (editedCaption != "") {
    editMemeData["caption"] = editedCaption;
  }

  log(editMemeData);

  fetch(encodeURI(xmemeBackendUrl + "/" + memeUnqiueId), {
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
      if (response.status === 404 || response.status === "404") {
        console.error("Meme Not Found");
        triggerIframe(false);
        throw new Error("Meme Not Found");
      } else if (response.status === 204 || response.status === "204") {
        triggerIframe(true);
        console.log("Meme Edited Successfully");
      } else {
        throw new Error(response.status);
      }
    })
    .catch((error) => {
      console.error("Error while editing meme:", error);
      triggerIframe(false);
    });
}

function triggerIframe(isSuccess) {
  console.log("IFRAME TRIGGER " + isSuccess);
  let frame = document.querySelector("#meme_notification");

  if (isSuccess === false) {
    frame.setAttribute("src", "error.html");
  } else {
    frame.setAttribute("src", "success-data.html");
  }
  frame.style.display = "inline";

  console.log("END IFRAME TRIGGER " + isSuccess);

  setTimeout(() => {
    console.log("Hide I frame start");
    let frame = document.querySelector("#meme_notification");
    frame.style.display = "none";
    console.log("Hide I frame complete");
  }, 5000);
}
