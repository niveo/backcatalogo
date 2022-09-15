package br.com.ams.backcatalogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.ams.backcatalogo.exception.EntityNotFoundException;
import br.com.ams.backcatalogo.model.CatalogoPaginaProduto;
import br.com.ams.backcatalogo.repository.CatalogoPaginaProdutoRepository;

@Service
public class CatalogoPaginaProdutoService implements ContractService<CatalogoPaginaProduto> {

	@Autowired
	private CatalogoPaginaProdutoRepository repository;

	@Override
	public List<CatalogoPaginaProduto> obterTodos() throws Exception {
		return this.repository.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public CatalogoPaginaProduto salvar(CatalogoPaginaProduto entity) throws Exception {
		return repository.save(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public CatalogoPaginaProduto atualizar(Integer codigo, CatalogoPaginaProduto entity) throws Exception {
		return repository.save(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void remover(Integer codigo) throws Exception {
		this.repository.deleteById(codigo);
	}

	@Override
	public CatalogoPaginaProduto obterCodigo(Integer codigo) throws Exception {
		return this.repository.findById(codigo)
				.orElseThrow(() -> new EntityNotFoundException("Catalogo Pagina Produto", codigo));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<CatalogoPaginaProduto> obterRegistrosPagina(Integer codigoPagina) throws Exception {
		return repository.obterRegistrosPagina(codigoPagina);
	}

	public long obterRegistrosPaginaConta(Integer codigoPagina) {
		return repository.obterRegistrosPaginaConta(codigoPagina);
	}

}
