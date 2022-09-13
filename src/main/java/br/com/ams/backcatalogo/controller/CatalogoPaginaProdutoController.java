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

import br.com.ams.backcatalogo.model.CatalogoPaginaProduto;
import br.com.ams.backcatalogo.service.CatalogoPaginaProdutoService;

@RestController
public class CatalogoPaginaProdutoController implements ContractController<CatalogoPaginaProduto> {

	@Autowired
	CatalogoPaginaProdutoService catalogoPaginaProdutoService;

	@RequestMapping(value = "/catalogopaginaproduto", method = RequestMethod.GET)
	public ResponseEntity<List<CatalogoPaginaProduto>> obterTodos() throws Exception {
		var registro = this.catalogoPaginaProdutoService.obterTodos();
		return new ResponseEntity<List<CatalogoPaginaProduto>>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/catalogopaginaproduto/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<CatalogoPaginaProduto> obterCodigo(@PathVariable(value = "codigo") Integer codigo)
			throws Exception {
		var registro = this.catalogoPaginaProdutoService.obterCodigo(codigo);
		return new ResponseEntity<CatalogoPaginaProduto>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/catalogopaginaproduto", method = RequestMethod.POST)
	public ResponseEntity<CatalogoPaginaProduto> salvar(@RequestBody CatalogoPaginaProduto entity) throws Exception {
		var registro = this.catalogoPaginaProdutoService.salvar(entity);
		return new ResponseEntity<CatalogoPaginaProduto>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/catalogopaginaproduto/{codigo}", method = RequestMethod.PUT)
	public ResponseEntity<CatalogoPaginaProduto> atualizar(@PathVariable(value = "codigo") Integer codigo,
			@RequestBody CatalogoPaginaProduto entity) throws Exception {
		var registro = this.catalogoPaginaProdutoService.atualizar(codigo, entity);
		return new ResponseEntity<CatalogoPaginaProduto>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/catalogopaginaproduto/{codigo}", method = RequestMethod.DELETE)
	public ResponseEntity<String> remover(@PathVariable(value = "codigo") Integer codigo) throws Exception {
		this.catalogoPaginaProdutoService.remover(codigo);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "/catalogopaginaproduto/pagina/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<List<CatalogoPaginaProduto>> obterRegistrosPagina(
			@PathVariable(value = "codigo") Integer codigo) throws Exception {
		var registro = this.catalogoPaginaProdutoService.obterRegistrosPagina(codigo);
		return new ResponseEntity<List<CatalogoPaginaProduto>>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/catalogopaginaproduto/pagina/conta/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<Long> obterRegistrosPaginaConta(@PathVariable(value = "codigo") Integer codigo)
			throws Exception {
		var registro = this.catalogoPaginaProdutoService.obterRegistrosPaginaConta(codigo);
		return new ResponseEntity<Long>(registro, HttpStatus.OK);
	}

}
