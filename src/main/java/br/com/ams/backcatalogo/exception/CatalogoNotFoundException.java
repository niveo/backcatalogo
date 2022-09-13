package br.com.ams.backcatalogo.exception;

public class CatalogoNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CatalogoNotFoundException(Integer codigo) {
		super(String.format("Catalogo %s não localizado", codigo));
	}
}
