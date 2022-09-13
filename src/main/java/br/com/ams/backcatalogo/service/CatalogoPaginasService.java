package br.com.ams.backcatalogo.service;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ams.backcatalogo.commons.Funcoes;
import br.com.ams.backcatalogo.commons.Utils;
import br.com.ams.backcatalogo.repository.CatalogoPaginasRepository;

@Service
public class CatalogoPaginasService {

	@Autowired
	private CatalogoPaginasRepository repository;

	@Value("${diretorio.catalogos}")
	private String diretorioArquivoCatalogos;

	@Autowired
	ObjectMapper objectMapper;

	private static final DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	public File downloadTodos() throws Exception {

		var registro = repository.findAll();

		File files = Utils.diretorioTemporario();

		for (var caPagina : registro) {

			var destFile = new File(files.toString() + File.separator + caPagina.getCodigo() + "_"
					+ dtFormat.format(caPagina.getDataAlterado()));

			FileUtils
					.copyFile(
							new File(diretorioArquivoCatalogos + File.separator + caPagina.getCatalogo().getCodigo()
									+ File.separator + "paginas" + File.separator + caPagina.getCodigo() + ".jpeg"),
							destFile);
		}

		File fileZip = Utils.criarArquivoDinamicoTemporario(".zip");

		Funcoes.zipDirectory(new ArrayList<>(), files, fileZip.toString());

		return fileZip;
	}

	public File downloadCatalogo(Integer codigoCatalogo) throws Exception {

		var registro = repository.obterPaginasDoCatalogo(codigoCatalogo);

		File files = Utils.diretorioTemporario();

		for (var caPagina : registro) {
			var destFile = new File(files.toString() + File.separator + caPagina.getCodigo() + "_"
					+ dtFormat.format(caPagina.getDataAlterado()));

			var fs = new File(diretorioArquivoCatalogos + File.separator + caPagina.getCatalogo().getCodigo()
					+ File.separator + "paginas" + File.separator + caPagina.getCodigo() + ".jpeg");

			if (!fs.exists()) {
				throw new Exception("Arquivo " + fs.toString() + " não localizado.");
			}

			FileUtils.copyFile(fs, destFile);
		}

		File fileZip = File.createTempFile(UUID.randomUUID().toString(), ".zip");

		Funcoes.zipDirectory(new ArrayList<>(), files, fileZip.toString());

		return fileZip;
	}

	public File downloadCatalogoPaginas(Integer codigoCatalogo, String codigoCatalogoPaginas) throws Exception {

		Integer[] codigos = objectMapper.readValue(codigoCatalogoPaginas, Integer[].class);

		var registro = repository.obterPaginasDoCatalogo(codigoCatalogo, codigos);

		File files = new File(System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID().toString());
		if (!files.exists()) {
			files.mkdirs();
		}

		for (var caPagina : registro) {
			var destFile = new File(files.toString() + File.separator + caPagina.getCodigo() + "_"
					+ dtFormat.format(caPagina.getDataAlterado()));

			var fs = new File(diretorioArquivoCatalogos + File.separator + caPagina.getCatalogo().getCodigo()
					+ File.separator + "paginas" + File.separator + caPagina.getCodigo() + ".jpeg");

			if (!fs.exists()) {
				throw new Exception("Arquivo " + fs.toString() + " não localizado.");
			}

			FileUtils.copyFile(fs, destFile);
		}

		File fileZip = File.createTempFile(UUID.randomUUID().toString(), ".zip");

		Funcoes.zipDirectory(new ArrayList<>(), files, fileZip.toString());

		return fileZip;
	}

	public File downloadCatalogoPagina(Integer codigoCatalogo, Integer codigoCatalogoPaginas) throws Exception {

		var registro = repository.obterPaginaDoCatalogo(codigoCatalogo, codigoCatalogoPaginas);

		File destFile = new File(System.getProperty("java.io.tmpdir") + File.separator + registro.getCodigo() + "_"
				+ dtFormat.format(registro.getDataAlterado()));

		FileUtils
				.copyFile(
						new File(diretorioArquivoCatalogos + File.separator + registro.getCatalogo().getCodigo()
								+ File.separator + "paginas" + File.separator + registro.getCodigo() + ".jpeg"),
						destFile);

		File fileZip = Utils.criarArquivoDinamicoTemporario(".zip");

		Funcoes.zipSingleFile(destFile, fileZip.toString());

		return fileZip;
	}

}
