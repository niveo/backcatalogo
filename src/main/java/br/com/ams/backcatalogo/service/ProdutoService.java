package br.com.ams.backcatalogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ams.backcatalogo.exception.ProdutoNotFoundException;
import br.com.ams.backcatalogo.model.Produto;
import br.com.ams.backcatalogo.repository.ProdutoRepository;

@Service
public class ProdutoService implements ContractService<Produto> {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public List<Produto> obterTodos() throws Exception {
		return produtoRepository.findAll();
	}

	@Override
	public Produto salvar(Produto entity) throws Exception {
		return produtoRepository.save(entity);
	}

	@Override
	public Produto atualizar(Integer codigo, Produto entity) throws Exception {
		return produtoRepository.save(entity);
	}

	@Override
	public void remover(Integer codigo) throws Exception {
		this.produtoRepository.deleteById(codigo);
	}

	@Override
	public Produto obterCodigo(Integer codigo) throws Exception {
		return this.produtoRepository.findById(codigo).orElseThrow(() -> new ProdutoNotFoundException(codigo));
	}

	public Produto obterReferencia(String referencia) throws Exception {
		return this.produtoRepository.obterReferencia(referencia)
				.orElseThrow(() -> new ProdutoNotFoundException(referencia));
	}
}