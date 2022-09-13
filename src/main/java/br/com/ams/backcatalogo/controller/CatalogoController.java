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

import br.com.ams.backcatalogo.model.Catalogo;
import br.com.ams.backcatalogo.service.CatalogoService;

@RestController
public class CatalogoController implements ContractController<Catalogo> {

	@Autowired
	CatalogoService catalogoService;

	@RequestMapping(value = "/catalogo", method = RequestMethod.GET)
	public ResponseEntity<List<Catalogo>> obterTodos() throws Exception {
		var registro = this.catalogoService.obterTodos();
		return new ResponseEntity<List<Catalogo>>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/catalogo/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<Catalogo> obterCodigo(@PathVariable(value = "codigo") Integer codigo) throws Exception {
		var registro = this.catalogoService.obterCodigoLazy(codigo);
		return new ResponseEntity<Catalogo>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/catalogo", method = RequestMethod.POST)
	public ResponseEntity<Catalogo> salvar(@RequestBody Catalogo entity) throws Exception {
		var registro = this.catalogoService.salvar(entity);
		return new ResponseEntity<Catalogo>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/catalogo/{codigo}", method = RequestMethod.PUT)
	public ResponseEntity<Catalogo> atualizar(@PathVariable(value = "codigo") Integer codigo,
			@RequestBody Catalogo entity) throws Exception {
		var registro = this.catalogoService.atualizar(codigo, entity);
		return new ResponseEntity<Catalogo>(registro, HttpStatus.OK);
	}

	@RequestMapping(value = "/catalogo/{codigo}", method = RequestMethod.DELETE)
	public ResponseEntity<String> remover(@PathVariable(value = "codigo") Integer codigo) throws Exception {
		this.catalogoService.remover(codigo);
		return ResponseEntity.ok().build();
	}

}
