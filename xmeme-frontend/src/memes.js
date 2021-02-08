let log = console.log;

// const xmemeBackendUrl = "http://localhost:8082/memes";
const xmemeBackendUrl =
  "https://pettywaterycookie.anugrahsinghal.repl.co/memes";

let editMemeBtn = document.querySelector("#edit-meme");
log(editMemeBtn);

editMemeBtn.addEventListener("click", editMeme);

function editMeme() {
  let cardToEdit = editMemeBtn.parentElement.parentElement;
  let memeUid = cardToEdit.getAttribute("id");

  showFormToEdit(memeUid);
}

function showFormToEdit(memeUid) {
  // show form

  // take input
  let editedName = document.querySelector("#edited-name").value;
  let editedCaption = document.querySelector("#meme-edited-caption").value;

	let editMemeData = {}
	if(editedName != undefined) {
		editMemeData['name'] = editedName;
	}
	if(editedCaption != undefined) {
		editMemeData['caption'] = editedCaption;
	}

	fetch(encodeURI(xmemeBackendUrl + "/" + memeUid), {
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
      if (response.status === 409 || response.status === "409") {
        console.error("Duplicate Meme Posted");
        triggerIframe(false);
        throw new Error("Please post an original Meme");
      } else if (!response.ok) {
        console.error(
          "Response is not OK, SOME ERROR OCCURED, Error with Server Response"
        );
        throw new Error("Error with Server Response");
      } else if (response.ok) {
        triggerIframe(true);
        return response.json();
      }
    })
    .then((data) => {
      console.log("Meme Posted Successfully:", JSON.stringify(data));
    })
    .catch((error) => {
      console.error("Error while posting meme:", error);
    });
}

function createMeme() {
  console.log("Creating Meme");
  let inputName = document.querySelector("#input-name").value;
  let inputUrl = document.querySelector("#input-url").value;
  let inputCaption = document.querySelector("#meme-input-caption").value;

  const memeData = {
    name: inputName,
    url: inputUrl,
    caption: inputCaption,
  };

  console.log(JSON.stringify(memeData));

  fetch(encodeURI(xmemeBackendUrl), {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      charset: "UTF-8",
    },
    body: JSON.stringify(memeData),
  })
    .then((response) => {
      log(response.body);
      log(response);
      if (response.status === 409 || response.status === "409") {
        console.error("Duplicate Meme Posted");
        triggerIframe(false);
        throw new Error("Please post an original Meme");
      } else if (!response.ok) {
        console.error(
          "Response is not OK, SOME ERROR OCCURED, Error with Server Response"
        );
        throw new Error("Error with Server Response");
      } else if (response.ok) {
        triggerIframe(true);
        return response.json();
      }
    })
    .then((data) => {
      console.log("Meme Posted Successfully:", JSON.stringify(data));
    })
    .catch((error) => {
      console.error("Error while posting meme:", error);
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
  // frame.style.display='block'

  console.log("END IFRAME TRIGGER " + isSuccess);

  setTimeout(() => {
    console.log("Hide I frame start");
    let frame = document.querySelector("#meme_created_msg");
    frame.style.display = "none";
    console.log("Hide I frame complete");
  }, 5000);
}
