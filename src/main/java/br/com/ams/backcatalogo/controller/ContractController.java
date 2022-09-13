package br.com.ams.backcatalogo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ContractController<T> {

	public ResponseEntity<List<T>> obterTodos() throws Exception;

	public ResponseEntity<T> obterCodigo(@PathVariable(value = "codigo") Integer codigo) throws Exception;

	public ResponseEntity<T> salvar(@RequestBody T entity) throws Exception;

	public ResponseEntity<T> atualizar(@PathVariable(value = "codigo") Integer codigo, @RequestBody T entity)
			throws Exception;

	public ResponseEntity<String> remover(@PathVariable(value = "codigo") Integer codigo) throws Exception;
}
