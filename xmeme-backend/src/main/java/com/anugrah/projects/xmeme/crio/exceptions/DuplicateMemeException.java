package com.anugrah.projects.xmeme.crio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate Meme")
public class DuplicateMemeException extends RuntimeException {
	public DuplicateMemeException(String message) {
		super(message);
	}
}
