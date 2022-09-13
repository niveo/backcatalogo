package br.com.ams.backcatalogo.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ams.backcatalogo.service.CatalogoPaginasService;

@RestController
@RequestMapping("catalogopagina")
public class CatalogoPaginasController {

	@Autowired
	private CatalogoPaginasService service;

	@GetMapping(value = "/todos")
	public ResponseEntity<Resource> downloadSistemaLiberado(HttpServletResponse response) throws Exception {
		var bis = service.downloadTodos();
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(new ByteArrayResource(FileUtils.readFileToByteArray(bis)));
	}

	@GetMapping(value = "/catalogo")
	public ResponseEntity<Resource> downloadCatalogo(HttpServletResponse response,
			@RequestParam(value = "codigoCatalogo") Integer codigoCatalogo) throws Exception {
		var bis = service.downloadCatalogo(codigoCatalogo);
		return ResponseEntity.ok().contentLength(bis.length())
				.header("Content-Disposition", "attachment; filename=\"" + bis.getName() + "\"")
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(new ByteArrayResource(FileUtils.readFileToByteArray(bis)));
	}

	@GetMapping(value = "/catalogoPaginas")
	public ResponseEntity<Resource> downloadCatalogoPaginas(HttpServletResponse response,
			@RequestParam(value = "codigoCatalogo") Integer codigoCatalogo,
			@RequestParam(value = "codigoCatalogoPagina") String codigoCatalogoPagina) throws Exception {
		var bis = service.downloadCatalogoPaginas(codigoCatalogo, codigoCatalogoPagina);
		return ResponseEntity.ok().contentLength(bis.length())
				.header("Content-Disposition", "attachment; filename=\"" + bis.getName() + "\"")
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(new ByteArrayResource(FileUtils.readFileToByteArray(bis)));
	}

	@GetMapping(value = "/catalogoPagina")
	public ResponseEntity<Resource> downloadCatalogoPagina(HttpServletResponse response,
			@RequestParam(value = "codigoCatalogo") Integer codigoCatalogo,
			@RequestParam(value = "codigoCatalogoPagina") Integer codigoCatalogoPagina) throws Exception {
		var bis = service.downloadCatalogoPagina(codigoCatalogo, codigoCatalogoPagina);
		return ResponseEntity.ok().contentLength(bis.length())
				.header("Content-Disposition", "attachment; filename=\"" + bis.getName() + "\"")
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(new ByteArrayResource(FileUtils.readFileToByteArray(bis)));
	}
}
