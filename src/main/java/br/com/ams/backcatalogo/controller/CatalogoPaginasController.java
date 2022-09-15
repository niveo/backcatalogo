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

import br.com.ams.backcatalogo.model.CatalogoPagina;
import br.com.ams.backcatalogo.service.CatalogoPaginasService;

@RestController
public class CatalogoPaginasController implements ContractController<CatalogoPagina> {

	@Autowired
	private CatalogoPaginasService service;

	@RequestMapping(value = "/catalogopagina", method = RequestMethod.GET)
	public ResponseEntity<List<CatalogoPagina>> obterTodos() throws Exception {
		var registro = this.service.obterTodos();
		return new ResponseEntity<List<CatalogoPagina>>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/catalogopagina/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<CatalogoPagina> obterCodigo(@PathVariable(value = "codigo") Integer codigo) throws Exception {
		var registro = this.service.obterCodigo(codigo);
		return new ResponseEntity<CatalogoPagina>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/catalogopagina", method = RequestMethod.POST)
	public ResponseEntity<CatalogoPagina> salvar(@RequestBody CatalogoPagina entity) throws Exception {
		var registro = this.service.salvar(entity);
		return new ResponseEntity<CatalogoPagina>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/catalogopagina/{codigo}", method = RequestMethod.PUT)
	public ResponseEntity<CatalogoPagina> atualizar(@PathVariable(value = "codigo") Integer codigo,
			@RequestBody CatalogoPagina entity) throws Exception {
		var registro = this.service.atualizar(codigo, entity);
		return new ResponseEntity<CatalogoPagina>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/catalogopagina/{codigo}", method = RequestMethod.DELETE)
	public ResponseEntity<String> remover(@PathVariable(value = "codigo") Integer codigo) throws Exception {
		this.service.remover(codigo);
		return ResponseEntity.ok().build();
	}

	
}
