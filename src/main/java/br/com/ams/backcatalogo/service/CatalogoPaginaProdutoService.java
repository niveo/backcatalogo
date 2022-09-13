package br.com.ams.backcatalogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.ams.backcatalogo.exception.CatalogoPaginaProdutoNotFoundException;
import br.com.ams.backcatalogo.model.CatalogoPaginaProduto;
import br.com.ams.backcatalogo.repository.CatalogoPaginaProdutoRepository;

@Service
public class CatalogoPaginaProdutoService implements ContractService<CatalogoPaginaProduto> {

	@Autowired
	private CatalogoPaginaProdutoRepository catalogoPaginaProdutoRepository;

	@Override
	public List<CatalogoPaginaProduto> obterTodos() throws Exception {
		return this.catalogoPaginaProdutoRepository.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public CatalogoPaginaProduto salvar(CatalogoPaginaProduto entity) throws Exception {
		return catalogoPaginaProdutoRepository.save(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public CatalogoPaginaProduto atualizar(Integer codigo, CatalogoPaginaProduto entity) throws Exception {
		return catalogoPaginaProdutoRepository.save(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void remover(Integer codigo) throws Exception {
		this.catalogoPaginaProdutoRepository.deleteById(codigo);
	}

	@Override
	public CatalogoPaginaProduto obterCodigo(Integer codigo) throws Exception {
		return this.catalogoPaginaProdutoRepository.findById(codigo)
				.orElseThrow(() -> new CatalogoPaginaProdutoNotFoundException(codigo));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<CatalogoPaginaProduto> obterRegistrosPagina(Integer codigoPagina) throws Exception {
		return catalogoPaginaProdutoRepository.obterRegistrosPagina(codigoPagina);
	}

	public long obterRegistrosPaginaConta(Integer codigoPagina) {
		return catalogoPaginaProdutoRepository.obterRegistrosPaginaConta(codigoPagina);
	}

}
