package com.anugrah.projects.xmeme.crio.controller;

import com.anugrah.projects.xmeme.crio.config.AppConfig;
import com.anugrah.projects.xmeme.crio.entity.Meme;
import com.anugrah.projects.xmeme.crio.exchanges.MemeCreatedResponse;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;
import com.anugrah.projects.xmeme.crio.service.MemeRetrievalService;
import com.anugrah.projects.xmeme.crio.service.MemeStorageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
public class MemesController {

	@Autowired
	private MemeStorageService memeStorageService;

	@Autowired
	private MemeRetrievalService memeRetrievalService;

	@Autowired
	private AppConfig appConfig;

	@GetMapping
	public String ping() {
		return "X-MEME is up and running";
	}

	/**
	 * Endpoint to send a meme to the backend
	 * HTTP Method - POST
	 * Endpoint - /memes
	 * Attributes - name, url, caption
	 * The backend should allocate a unique id for the meme and return it as a json response.
	 * Example request and sample response
	 * curl --request POST
	 * 'https://<Server_URL>/memes
	 * ?name=ashok%20kumar&
	 * url=https://images.pexels.com/photos/3573382/pexels-photo-3573382.jpeg&
	 * caption=This%20is%20a%20meme'
	 * {
	 * "id": "1"
	 * }
	 */
	@PostMapping("/memes")
	public ResponseEntity<MemeCreatedResponse> postMeme(@RequestParam("name") String name, @RequestParam("url") String url,
	                                                    @RequestParam("caption") String caption) {

		final MemeCreatedResponse memeCreated = memeStorageService.createMeme(name, url, caption);

		return ResponseEntity.ok().body(memeCreated);
	}

	@GetMapping("/memes")
	@ApiOperation(value = "retrieveMemesPaged", nickname = "retrieveMemesPaged")
	@ApiImplicitParams( {@ApiImplicitParam(name = "page", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "size", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, paramType = "query", dataType = "string")})
	public ResponseEntity<List<Meme>> retrieveMemesPaged(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, instead they are explained in the implicit params")
			@PageableDefault(size = 100) Pageable pageable) {
		final List<Meme> memes = memeRetrievalService.retrieveMemes(pageable);

		return ResponseEntity.ok().body(memes);
	}


	@GetMapping("/memes/{id}")
	public ResponseEntity<Meme> retrieveMemes(@PathVariable Long id) {
		final Meme meme = memeRetrievalService.retrieveMeme(id);

		return ResponseEntity.ok().body(meme);
	}

	/**
	 * Endpoint to update the caption or url for an existing meme at the backend
	 * It shall be capable of changing both caption/url or one of them in a single call
	 * Name of the meme creator shall not be allowed to change
	 * Add an edit button next to the memes in the list where this can be triggered from
	 * <p>
	 * Error:
	 * If a meme with that Id doesn’t exist, a 404 HTTP response code should be returned.
	 * Example request
	 * <p>
	 * HTTP Method: PATCH
	 * Endpoint: 'https://<Server_URL>/memes/<id>'
	 * Body:
	 * {
	 * "caption": “new caption”,
	 * "url": “new url”
	 * }
	 */
	@PatchMapping("/memes/{id}")
	public ResponseEntity<MemeCreatedResponse> updateMeme(UpdateMemeRequest updateMemeRequest, @PathVariable Long id) {
		final MemeCreatedResponse memeUpdated = memeStorageService.updateMeme(id, updateMemeRequest);

		return ResponseEntity.ok().body(memeUpdated);
	}

}

