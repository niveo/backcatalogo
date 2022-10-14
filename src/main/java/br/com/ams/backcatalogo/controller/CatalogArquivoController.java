package br.com.ams.backcatalogo.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.ams.backcatalogo.service.CatalogoArquivosService;
import br.com.ams.backcatalogo.service.FileStorageService;

@RestController
public class CatalogArquivoController {

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	CatalogoArquivosService catalogoArquivosService;

	@RequestMapping(value = "catalogoarquivo/uploadcatalogo/{codigo}", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	public ResponseEntity<Boolean> uploadFile(@PathVariable(value = "codigo") Integer codigo,
			@RequestParam(value = "dpi") Integer dpi, @RequestParam("file") MultipartFile file) throws Exception {

		String fileName = fileStorageService.storeFile(file, UUID.randomUUID().toString());

		catalogoArquivosService.importarCatalogo(fileName, codigo, dpi);

		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	@PostMapping(path = "/uploadarquivo/{codigo}", consumes = { "multipart/form-data" })
	public ResponseEntity<Boolean> uploadArquivo(@PathVariable(value = "codigo") Integer codigo,
			@RequestParam("file") MultipartFile file) throws Exception {

		String fileName = fileStorageService.storeFile(file, UUID.randomUUID().toString());

		catalogoArquivosService.uploadArquivo(fileName, codigo);

		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	@RequestMapping(value = "catalogoarquivo/download/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadCatalogo(HttpServletResponse response,
			@PathVariable(value = "codigo") Integer codigo) throws Exception {
		var bis = catalogoArquivosService.downloadCatalogo(codigo);
		return ResponseEntity.ok().contentLength(bis.length())
				.header("Content-Disposition", "attachment; filename=\"" + bis.getName() + "\"")
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(new ByteArrayResource(FileUtils.readFileToByteArray(bis)));
	}

	@RequestMapping(value = "catalogoarquivo/pagina/download/{codigoCatalogo}", method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadCatalogoPaginas(HttpServletResponse response,
			@PathVariable(value = "codigoCatalogo") Integer codigoCatalogo,
			@RequestParam(value = "codigoCatalogoPaginas") String codigoCatalogoPaginas) throws Exception {
		var bis = catalogoArquivosService.downloadCatalogoPaginas(codigoCatalogo, codigoCatalogoPaginas);
		return ResponseEntity.ok().contentLength(bis.length())
				.header("Content-Disposition", "attachment; filename=\"" + bis.getName() + "\"")
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(new ByteArrayResource(FileUtils.readFileToByteArray(bis)));
	}

}
