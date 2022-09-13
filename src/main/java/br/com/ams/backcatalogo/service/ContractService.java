package br.com.ams.backcatalogo.service;

import java.util.List;

public interface ContractService<T> {

	List<T> obterTodos() throws Exception;

	T salvar(T entity) throws Exception;

	T atualizar(Integer codigo, T entity) throws Exception;

	void remover(Integer codigo) throws Exception;

	T obterCodigo(Integer codigo) throws Exception;

}
