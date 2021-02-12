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
  }, 2500);
}
