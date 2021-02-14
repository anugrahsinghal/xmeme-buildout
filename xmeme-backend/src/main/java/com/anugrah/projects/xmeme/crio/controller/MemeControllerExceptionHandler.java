package com.anugrah.projects.xmeme.crio.controller;

import com.anugrah.projects.xmeme.crio.exceptions.DuplicateMemeException;
import com.anugrah.projects.xmeme.crio.exceptions.MemeNotFoundException;
import com.anugrah.projects.xmeme.crio.exceptions.MemeUpdateException;
import com.anugrah.projects.xmeme.crio.exceptions.MemeValidationException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MemeControllerExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(MemeControllerExceptionHandler.class);

	@ExceptionHandler( {MemeNotFoundException.class})
	void dataNotFound(HttpServletResponse response, Exception exception) {
		log.error(exception);
		response.setStatus(HttpStatus.NOT_FOUND.value());
	}

	@ExceptionHandler( {MemeUpdateException.class, MemeValidationException.class, ConstraintViolationException.class, IllegalArgumentException.class})
	void badRequest(HttpServletResponse response, Exception exception) {
		log.error(exception);
//		response.sendError(HttpStatus.BAD_REQUEST.value());
		response.setStatus(HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler( {DuplicateMemeException.class})
	void conflict(HttpServletResponse response, Exception exception) {
		log.error(exception);
//		response.sendError(HttpStatus.BAD_REQUEST.value());
		response.setStatus(HttpStatus.CONFLICT.value());
	}

}
