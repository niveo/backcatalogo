package br.com.ams.backcatalogo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CatalogoPaginaProdutoNotFoundAdvice {
	@ResponseBody
	@ExceptionHandler(CatalogoPaginaProdutoNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String catalogopaginaProdutoNotFoundHandler(CatalogoPaginaProdutoNotFoundException ex) {
		return ex.getMessage();
	}
}
