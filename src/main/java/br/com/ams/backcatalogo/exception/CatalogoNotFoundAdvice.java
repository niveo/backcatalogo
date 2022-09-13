package br.com.ams.backcatalogo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CatalogoNotFoundAdvice {
	@ResponseBody
	@ExceptionHandler(CatalogoNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String catalogoNotFoundHandler(CatalogoNotFoundException ex) {
		return ex.getMessage();
	}
}
