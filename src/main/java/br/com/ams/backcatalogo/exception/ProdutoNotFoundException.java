package br.com.ams.backcatalogo.exception;

public class ProdutoNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ProdutoNotFoundException(Object codigo) {
		super(String.format("Produto %s não localizado", codigo));
	}
}
