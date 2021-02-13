/**
 * Template for each meme card
 * uses templating engine from handlebar-js
 */
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
/**
 * compiling the template
 */
const template = Handlebars.compile(cardTemplate);

loadMemes();
/**
 * This function calls the backend URL to get details of the meme
 * gets latest 100 memes
 * generates HTML for meme cards from template and inject/render to page
 * after render - attach edit and open button to card
 */
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
      log("Total Data Received = z" + data.length);
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
    });
}
/**
 * attaches click event listeners to edit and open button on each card view
 */
function attachButtonsToTheLoadedMemes() {
  log("attach buttons");

  let editMemeBtns = document.querySelectorAll(".edit-meme-btn");
  let openMemeBtns = document.querySelectorAll(".open-meme-btn");
  log("editMemeBtns.length = " + editMemeBtns.length);
  log("openMemeBtns.length = " + openMemeBtns.length);

  editMemeBtns.forEach((item) => {
    item.addEventListener("click", showFormToEditMeme);
  });
  openMemeBtns.forEach((item) => {
    item.addEventListener("click", openMemeInNewPage);
  });
}
/**
 * used to get the meme id and redirect user to view page
 * @param {*} event The target open button of card
 */
function openMemeInNewPage(event) {
  window.location.assign("meme.html?q=" + event.target.id);
}
