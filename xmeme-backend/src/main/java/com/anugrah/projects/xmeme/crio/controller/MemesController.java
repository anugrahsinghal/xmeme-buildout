package com.anugrah.projects.xmeme.crio.controller;

import com.anugrah.projects.xmeme.crio.config.AppConfig;
import com.anugrah.projects.xmeme.crio.entity.Meme;
import com.anugrah.projects.xmeme.crio.exchanges.MemeCreatedResponse;
import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;
import com.anugrah.projects.xmeme.crio.service.MemeRetrievalService;
import com.anugrah.projects.xmeme.crio.service.MemeStorageService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(
		allowedHeaders = "*",
		methods = {RequestMethod.GET, RequestMethod.PATCH, RequestMethod.POST, RequestMethod.PUT}
)
@RestController
@Tag(name = "memes", description = "The Meme API")
@Validated
public class MemesController {

	private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(MemesController.class);
	@Autowired
	private MemeStorageService memeStorageService;

	@Autowired
	private MemeRetrievalService memeRetrievalService;

	@GetMapping("/ping")
	@Hidden
	public String ping() {
		return "X-MEME is up and running";
	}

	/**
	 * Endpoint to send a meme
	 * HTTP Method - POST
	 * Endpoint - /memes
	 * Attributes - name, url, caption
	 * Return id allocated to meme as a json response.
	 * Example request and sample response
	 * <p>
	 * Endpoint: https://{Server_URL}/memes
	 * <p>
	 * Body:
	 * {
	 * "name":"new name"
	 * "caption": “new caption”,
	 * "url": “http://something.com/img/meme”
	 * }
	 * Response:
	 * {
	 * "id": "1"
	 * }
	 */
	@PostMapping("/memes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "409", description = "Duplicate Meme")
	})
	public ResponseEntity<MemeCreatedResponse> postMeme(@Valid @RequestBody MemeDto memeDto) {

		final MemeCreatedResponse memeCreated = memeStorageService.createMeme(memeDto);
		log.info("Meme Created {}", memeCreated);

		return ResponseEntity.status(HttpStatus.CREATED).body(memeCreated);
	}

	/**
	 * Endpoint to get the latest 100 memes
	 * HTTP Method - GET
	 * Endpoint - /memes
	 * Example request and sample response
	 * <p>
	 * Endpoint: https://{Server_URL}/memes
	 * <p>
	 * Body:
	 * [
	 * {
	 * "id": "1",
	 * "name":"new name",
	 * "caption": “new caption”,
	 * "url": “new url”
	 * },{
	 * "id": "2",
	 * "name":"new name 2",
	 * "caption": “new caption 2”,
	 * "url": “new url 2”
	 * }
	 * ]
	 */
	@GetMapping(value = "/memes", produces = {"application/json"})
	@Operation(description = "retrieveMemes", summary = "retrieveMemes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful Operation")})
	public ResponseEntity<List<Meme>> retrieveMemes() {
		final List<Meme> memes = memeRetrievalService.retrieveMemes();
		log.info("Memes Fetched Size {}", memes.size());

		return ResponseEntity.ok().body(memes);
	}


	/**
	 * Endpoint to get meme by specified id
	 * HTTP Method - GET
	 * Endpoint - /memes/{id}
	 * Example request and sample response
	 * <p>
	 * Endpoint: https://{Server_URL}/memes/{id}
	 * <p>
	 * Response:
	 * [
	 * {
	 * "id": "1",
	 * "name":"new name",
	 * "caption": “new caption”,
	 * "url": “new url”
	 * }
	 * ]
	 */
	@GetMapping(value = "memes/{id}", produces = {"application/json"})
	public ResponseEntity<Meme> retrieveMemes(@NotNull @NotBlank @PathVariable Long id) {
		final Meme meme = memeRetrievalService.retrieveMeme(id);
		log.info("meme found = {}", meme);

		return ResponseEntity.ok().body(meme);
	}

	/**
	 * Endpoint to update the caption or url for an existing meme
	 * Capable of changing both caption/url or one of them in a single call
	 * <p>
	 * Error:
	 * If a meme with that Id doesn’t exist, a 404 HTTP response code should be returned.
	 * Example request
	 * <p>
	 * HTTP Method: PATCH
	 * Endpoint: 'https://{Server_URL}/memes/{id}'
	 * Body:
	 * {
	 * "caption": “new caption”,
	 * "url": “new url”
	 * }
	 */
	@PatchMapping(value = "memes/{id}", produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Meme Not Found"),
			@ApiResponse(responseCode = "409", description = "Duplicate Meme")
	})
	public void updateMeme(@RequestBody UpdateMemeRequest updateMemeRequest, @NotNull @NotBlank @PathVariable Long id) {
		memeStorageService.updateMeme(id, updateMemeRequest);
	}

	/**
	 * Endpoint to delete the meme
	 * <p>
	 * Error:
	 * If a meme with that Id doesn’t exist, a 404 HTTP response code should be returned.
	 * Example request
	 * <p>
	 * HTTP Method: DELETE
	 * Endpoint: 'https://{Server_URL}/memes/{id}'
	 */
	@DeleteMapping(value = "memes/{id}", produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Meme Not Found")
	})
	public void deleteMeme(@NotNull @NotBlank @PathVariable Long id) {
		memeStorageService.deleteMeme(id);
	}
}

