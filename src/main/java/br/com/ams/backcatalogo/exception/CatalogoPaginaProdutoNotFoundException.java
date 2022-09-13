package br.com.ams.backcatalogo.exception;

public class CatalogoPaginaProdutoNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CatalogoPaginaProdutoNotFoundException(Integer codigo) {
		super(String.format("Catalogo pagina Produto %s não localizado", codigo));
	}
}
