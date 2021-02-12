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

loadMemes();

function openMemeInNewPage(event) {
  window.location.assign("meme.html?q=" + event.target.id);
}
