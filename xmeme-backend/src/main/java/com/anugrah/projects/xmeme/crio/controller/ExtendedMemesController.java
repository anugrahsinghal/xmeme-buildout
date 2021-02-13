package com.anugrah.projects.xmeme.crio.controller;

import com.anugrah.projects.xmeme.crio.entity.Meme;
import com.anugrah.projects.xmeme.crio.service.MemeRetrievalService;
import com.anugrah.projects.xmeme.crio.service.MemeStorageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/extended")
public class ExtendedMemesController {

	private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(ExtendedMemesController.class);
	@Autowired
	private MemeStorageService memeStorageService;

	@Autowired
	private MemeRetrievalService memeRetrievalService;

	@GetMapping("/memes")
	// Query Params: latitude, longitude, searchFor(optional)
	@ApiOperation(value = "retrieveMemesPaged", nickname = "retrieveMemesPaged")
	@ApiImplicitParams( {@ApiImplicitParam(name = "page", paramType = "query", dataType = "int", example = "0"),
			@ApiImplicitParam(name = "size", paramType = "query", dataType = "int", example = "20"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, paramType = "query", dataType = "string", example = "id")})
	public ResponseEntity<List<Meme>> retrieveMemesPaged(
			@PageableDefault(size = 100, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		final List<Meme> memes = memeRetrievalService.retrieveMemes(pageable);

		return ResponseEntity.ok().body(memes);
	}


}

