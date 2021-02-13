attachSubmitButtonToForm();
/**
 * attached click listener to submit button on meme create/edit form
 */
function attachSubmitButtonToForm() {
  let submitBtn = document.querySelector("#submit-data");
  submitBtn.addEventListener("click", sendDataAndClearForm);
}
/**
 * This function calls the backend URL to create a new meme
 * checks for validity of inputs
 * on valid input sends data to backend
 * get unique Id of the created data in response
 * response 200 is a success
 * response 409 tells duplicate data
 * any other reponse code is assumed to be invalid data
 * triggers notification for both success and failure scenarios
 */
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
      .catch((error) => {
        triggerIframe("Something went wrong! Please try again.");
        console.error("Error while posting meme:", error);
      });
  }
  console.groupEnd();
}

/**
 * This function calls the backend URL to update an existing meme
 * The id of the meme is access from the localStorage of browser
 *
 * checks for validity of inputs
 * on valid input sends data to backend
 * response 204 is a success
 * response 404 is a when a meme is not found in the backend
 * response 409 is duplicate data
 * any other reponse code is assumed to be invalid data
 * triggers notification for both success and failure scenarios
 * reloads page on successful request
 * @param {*} inputs form inputs having updated caption and/or url;
 */
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
          triggerIframe("Meme Not Found", false);
          clearAndHideForm();
        } else if (response.status === 409 || response.status === "409") {
          console.error("Duplicate Data");
          triggerIframe("Duplicate Data", false);
          clearAndHideForm();
        }
      })
      .catch((error) => {
        console.error("Error while editing meme:", error);
        triggerIframe("Error Occurred!", false);
        clearAndHideForm();
      });
  }

  console.groupEnd();
  console.log("patch end");
}
/**
 * Clears form data
 * hides the form
 */
function clearAndHideForm() {
  clearForm();
  hideForm();
}

/**
 *
 * @param {*} inputs the inputs name,caption,url from the create/edit form
 * @param {*} operationType create or edit
 * for create operation all three inputs are validated
 */
function checkDataValidity(inputs, operationType) {
  let inputname = inputs["name"].value;
  let inputcaption = inputs["caption"].value;
  let inputUrl = inputs["url"].value;
  if (operationType === "create") {
    if (inputname === "" || inputcaption === "" || inputUrl === "") {
      triggerIframe("Invalid Data Provided. All fields are required.", false);
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

/**
 * send data to be create or edit the meme
 * clear form after operation complete
 */
function sendDataAndClearForm() {
  sendDataToPersist();
  clearForm();
}

/**
 * Call POST or PATCH request based on presence of input name in form
 */
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
