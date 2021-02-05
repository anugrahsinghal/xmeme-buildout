package com.anugrah.projects.xmeme.crio.controller;

import com.anugrah.projects.xmeme.crio.exceptions.DuplicateMemeException;
import com.anugrah.projects.xmeme.crio.exceptions.MemeNotFoundException;
import com.anugrah.projects.xmeme.crio.exceptions.MemeUpdateException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MemeControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler( {MemeNotFoundException.class})
	void dataNotFound(HttpServletResponse response) {
		response.setStatus(HttpStatus.NOT_FOUND.value());
	}

	@ExceptionHandler( {MemeUpdateException.class, ConstraintViolationException.class})
	void badRequest(HttpServletResponse response) {
//		response.sendError(HttpStatus.BAD_REQUEST.value());
		response.setStatus(HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler( {DuplicateMemeException.class})
	void conflict(HttpServletResponse response) {
//		response.sendError(HttpStatus.BAD_REQUEST.value());
		response.setStatus(HttpStatus.CONFLICT.value());
	}

}
