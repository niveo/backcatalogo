package br.com.ams.backcatalogo.exception;

public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String entity, Object codigo) {
		super(String.format("%s %s não localizado", entity, codigo));
	}
}
