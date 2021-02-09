package com.anugrah.projects.xmeme.crio.controller;

import com.anugrah.projects.xmeme.crio.config.AppConfig;
import com.anugrah.projects.xmeme.crio.entity.Meme;
import com.anugrah.projects.xmeme.crio.exchanges.MemeCreatedResponse;
import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;
import com.anugrah.projects.xmeme.crio.service.MemeRetrievalService;
import com.anugrah.projects.xmeme.crio.service.MemeStorageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController/*(value = "/memes")*/
public class MemesController {

	private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(MemesController.class);
	@Autowired
	private MemeStorageService memeStorageService;

	@Autowired
	private MemeRetrievalService memeRetrievalService;

	@Autowired
	private AppConfig appConfig;

	@GetMapping("/ping")
	@ApiIgnore
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
	@ApiResponses(value = {
			@ApiResponse(code = 409, message = "Duplicate Meme")
	})
	public ResponseEntity<MemeCreatedResponse> postMeme(@RequestBody MemeDto memeDto) {

		final MemeCreatedResponse memeCreated = memeStorageService.createMeme(memeDto);
		log.info("Meme Created {}", memeCreated);

		return ResponseEntity.status(HttpStatus.CREATED).body(memeCreated);
	}

	@GetMapping("/memes")
	@ApiOperation(value = "retrieveMemes", nickname = "retrieveMemes")
	public ResponseEntity<List<Meme>> retrieveMemes() {
		final List<Meme> memes = memeRetrievalService.retrieveMemes();
		log.info("Memes Fetched Size {}", memes.size());

		return ResponseEntity.ok().body(memes);
	}


	@GetMapping("memes/{id}")
	public ResponseEntity<Meme> retrieveMemes(@PathVariable Long id) {
		final Meme meme = memeRetrievalService.retrieveMeme(id);
		log.info("meme found = {}", meme);

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
	@PatchMapping("memes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "Meme Not Found")
	})
	public void updateMeme(@RequestBody UpdateMemeRequest updateMemeRequest, @PathVariable Long id) {
		memeStorageService.updateMeme(id, updateMemeRequest);
	}

}

