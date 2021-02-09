let log = console.log;

// const xmemeBackendUrl = "http://localhost:8082/memes";
const xmemeBackendUrl =
	"https://pettywaterycookie.anugrahsinghal.repl.co/memes";
	
document.querySelector("#exit-overlay").addEventListener("click", hideOverlayForm)

let editMemeBtns = document.querySelectorAll("#edit-meme");
log(editMemeBtns);

editMemeBtns.forEach((item) => {
  item.addEventListener("click", editMeme);
});

function editMeme(event) {
  let cardToEdit = event.target.parentElement.parentElement;
  let memeUnqiueId =
    cardToEdit.getAttribute("id") != null ? cardToEdit.getAttribute("id") : 2;
  log(memeUnqiueId);
  showOverlayForm(memeUnqiueId);

  let submitMemeBtn = document.querySelector("#submit-meme");
  submitMemeBtn.addEventListener("click", perforMemeEdit(memeUnqiueId));
}

function showOverlayForm(memeUnqiueId) {
  // maybe fetch and show
  document.querySelector("#overlay").style.display = "block";
}

function hideOverlayForm() {
  document.querySelector("#overlay").style.display = "none";
  // clear data
}

function perforMemeEdit(memeUnqiueId) {
  // take input
  let editedName = document.querySelector("#edited-name").value;
  let editedCaption = document.querySelector("#edited-caption").value;

  let editMemeData = {};
  if (editedName != undefined) {
    editMemeData["name"] = editedName;
  }
  if (editedCaption != undefined) {
    editMemeData["caption"] = editedCaption;
  }

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
      } else if (response.status === "204" || response.status === "204") {
        triggerIframe(true);
        return response.json();
      } else {
        throw new Error(response.status);
      }
    })
    .then((data) => {
      console.log("Meme Edited Successfully:", JSON.stringify(data));
    })
    .catch((error) => {
      console.error("Error while editing meme:", error);
    });
}

function triggerIframe(isSuccess) {
  console.log("IFRAME TRIGGER " + isSuccess);
  let frame = document.querySelector("#meme_created_msg");

  if (isSuccess === false) {
    frame.setAttribute("src", "duplicate-data.html");
  } else {
    frame.setAttribute("src", "success-data.html");
  }
  frame.style.display = "inline";

  console.log("END IFRAME TRIGGER " + isSuccess);

  setTimeout(() => {
    console.log("Hide I frame start");
    let frame = document.querySelector("#meme_created_msg");
    frame.style.display = "none";
    console.log("Hide I frame complete");
  }, 5000);
}
