package com.anugrah.projects.xmeme.crio.controller;

import com.anugrah.projects.xmeme.crio.exceptions.MemeNotFoundException;
import com.anugrah.projects.xmeme.crio.exceptions.MemeUpdateException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MemeControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler( {MemeNotFoundException.class})
	void dataNotFound(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value());
	}

	@ExceptionHandler( {MemeUpdateException.class})
	void badRequest(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

}
