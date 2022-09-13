package br.com.ams.backcatalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.ams.backcatalogo.model.Produto;
import br.com.ams.backcatalogo.service.ProdutoService;

@RestController
public class ProdutoController implements ContractController<Produto> {

	@Autowired
	ProdutoService produtoService;

	@RequestMapping(value = "/produto", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> obterTodos() throws Exception {
		var registro = this.produtoService.obterTodos();
		return new ResponseEntity<List<Produto>>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<Produto> obterCodigo(@PathVariable(value = "codigo") Integer codigo) throws Exception {
		var registro = this.produtoService.obterCodigo(codigo);
		return new ResponseEntity<Produto>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/referencia/{referencia}", method = RequestMethod.GET)
	public ResponseEntity<Produto> obterReferencia(@PathVariable(value = "referencia") String referencia)
			throws Exception {
		var registro = this.produtoService.obterReferencia(referencia);
		return new ResponseEntity<Produto>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto", method = RequestMethod.POST)
	public ResponseEntity<Produto> salvar(@RequestBody Produto entity) throws Exception {
		var registro = this.produtoService.salvar(entity);
		return new ResponseEntity<Produto>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{codigo}", method = RequestMethod.PUT)
	public ResponseEntity<Produto> atualizar(@PathVariable(value = "codigo") Integer codigo,
			@RequestBody Produto entity) throws Exception {
		var registro = this.produtoService.atualizar(codigo, entity);
		return new ResponseEntity<Produto>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{codigo}", method = RequestMethod.DELETE)
	public ResponseEntity<String> remover(@PathVariable(value = "codigo") Integer codigo) throws Exception {
		this.produtoService.remover(codigo);
		return ResponseEntity.ok().build();
	}
}
