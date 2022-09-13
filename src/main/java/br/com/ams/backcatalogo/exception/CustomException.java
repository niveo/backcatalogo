package br.com.ams.backcatalogo.exception;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;

	String message;
	String developerMessage;

	public CustomException() {
		super();
	}

	public CustomException(Exception ex) {
		super(ex);
		this.developerMessage = ex.getMessage();
	}

	public CustomException(String developerMessage) {
		super(developerMessage);
		this.developerMessage = developerMessage;
	}

	public CustomException(String message, String developerMessage) {
		super(developerMessage);
		this.developerMessage = developerMessage;
		this.message = message;
	}
}
