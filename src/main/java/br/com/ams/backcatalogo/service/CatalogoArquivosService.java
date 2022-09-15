package br.com.ams.backcatalogo.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ams.backcatalogo.commons.Funcoes;
import br.com.ams.backcatalogo.commons.Utils;
import br.com.ams.backcatalogo.exception.CustomException;
import br.com.ams.backcatalogo.model.Catalogo;
import br.com.ams.backcatalogo.model.CatalogoPagina;
import br.com.ams.backcatalogo.model.CatalogoPaginaProduto;
import br.com.ams.backcatalogo.repository.CatalogoPaginasRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CatalogoArquivosService {

	@Value("${diretorio.upload}")
	String sistemaDiretorioUpload;

	@Value("${diretorio.catalogos}")
	String sistemaDiretorioCatalogos;

	private static final DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	@Autowired
	private CatalogoPaginasRepository catalogoPaginasRepository;

	@Autowired
	private CatalogoService catalogoService;

	public File obterArquivoCatalogo(Integer codigo) throws Exception {

		var catalogo = this.catalogoService.obterCodigo(codigo);

		var catalogoArquivo = new File(sistemaDiretorioCatalogos + File.separator + catalogo.getCodigo()
				+ File.separator + catalogo.getCodigo() + ".pdf");

		if (!catalogoArquivo.exists()) {
			throw new Exception("Catalogo " + catalogo.getDescricao() + " não localizado.");
		}
		return catalogoArquivo;
	}

	public void uploadArquivo(String arquivo, Integer codigo) throws Exception {
		var catalogoArquivo = new File(sistemaDiretorioUpload + "/" + arquivo);

		var diretorioCatalogo = new File(sistemaDiretorioCatalogos);
		if (!diretorioCatalogo.exists()) {
			diretorioCatalogo.mkdirs();
		}

		FileUtils.copyFile(catalogoArquivo,
				new File(sistemaDiretorioCatalogos + File.separator + codigo + File.separator + codigo + ".pdf"));

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void importarCatalogo(String arquivo, Integer codigo, Integer dpi) throws Exception {

		var catalogo = catalogoService.obterCodigoLazy(codigo);
		if (catalogo.getCatalogoPaginas() == null) {
			catalogo.setCatalogoPaginas(new ArrayList<CatalogoPagina>());
		} else {
			catalogo.getCatalogoPaginas().clear();
			catalogoService.salvar(catalogo);
		}

		File destinationFile = new File(
				System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID().toString() + File.separator);

		var catalogoArquivo = new File(sistemaDiretorioUpload + "/" + arquivo);

		FileUtils.deleteDirectory(new File(sistemaDiretorioCatalogos + File.separator + catalogo.getCodigo()));

		try {

			var diretorioCatalogo = new File(sistemaDiretorioCatalogos);
			if (diretorioCatalogo.exists()) {
				diretorioCatalogo.mkdirs();
			}

			FileUtils.copyFile(catalogoArquivo, new File(sistemaDiretorioCatalogos + File.separator
					+ catalogo.getCodigo() + File.separator + catalogo.getCodigo() + ".pdf"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		exportPDF(catalogoArquivo, destinationFile, catalogo, dpi);

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void importarCatalogoIndividual(String arquivo, Integer codigo, Integer dpi, Integer paginaArquivo,
			Integer paginaSistema) throws Exception {

		var catalogo = catalogoService.obterCodigoLazy(codigo);

		File destinationFile = new File(
				System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID().toString() + File.separator);

		var catalogoArquivo = new File(sistemaDiretorioUpload + "/" + arquivo);

		exportPDFIndividual(catalogoArquivo, destinationFile, catalogo, dpi, paginaArquivo, paginaSistema);

	}

	private void exportPDFIndividual(File sourceFile, File destinationFile, Catalogo catalogo, Integer dpi,
			Integer paginaArquivo, Integer paginaSistema) {

		if (!destinationFile.exists()) {
			destinationFile.mkdir();
			log.info("Folder Created -> " + destinationFile.getAbsolutePath());
		}

		if (sourceFile.exists()) {
			PDDocument document = null;
			try {
				System.out.println("Images copied to Folder: " + destinationFile.getName());
				document = PDDocument.load(sourceFile);
				PDFRenderer pdfRenderer = new PDFRenderer(document);

				int numberOfPages = document.getNumberOfPages();

				if (numberOfPages < paginaArquivo) {
					throw new Exception("Arquivo contem " + numberOfPages + " paginas solicitado " + paginaArquivo);
				}

				log.info("Total files to be converted -> " + numberOfPages);

				String fileExtension = "jpeg";

				File outPutFile = new File(UUID.randomUUID().toString() + "." + fileExtension);

				BufferedImage bImage = pdfRenderer.renderImageWithDPI((paginaArquivo - 1), dpi, ImageType.RGB);

				ImageIO.write(bImage, fileExtension, outPutFile);

				var catalogoPaginaGet = catalogo.getCatalogoPaginas().stream()
						.filter(a -> a.getPagina().equals(paginaSistema)).findFirst().get();

				try {
					FileUtils.forceDeleteOnExit(new File(sistemaDiretorioCatalogos + File.separator
							+ catalogo.getCodigo() + File.separator + "paginas" + File.separator
							+ catalogoPaginaGet.getCodigo() + "." + fileExtension));
				} catch (Exception e) {
				}

				catalogo.getCatalogoPaginas().remove(catalogoPaginaGet);

				var catalogoPagina = new CatalogoPagina();
				catalogoPagina.setCatalogo(catalogo);
				catalogoPagina.setDataAlterado(ZonedDateTime.now());
				catalogoPagina.setPagina(paginaSistema);
				catalogoPagina.setCatalogoPaginaProdutos(new ArrayList<CatalogoPaginaProduto>());

				catalogo.getCatalogoPaginas().add(catalogoPagina);

				catalogo = catalogoService.salvar(catalogo);

				catalogoPaginaGet = catalogo.getCatalogoPaginas().stream()
						.filter(a -> a.getPagina().equals(paginaSistema)).findFirst().get();

				try {

					var fileDest = new File(
							sistemaDiretorioCatalogos + File.separator + catalogo.getCodigo() + File.separator
									+ "paginas" + File.separator + catalogoPaginaGet.getCodigo() + "." + fileExtension);

					FileUtils.copyFile(outPutFile, fileDest);

				} catch (Exception e) {
					log.error("ERRO ", e);
				}

				log.info("Converted Images are saved at -> " + destinationFile.getAbsolutePath());

			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException(e);
			} finally {
				if (document != null) {
					try {
						document.close();
					} catch (IOException e) {
						e.printStackTrace();
						throw new CustomException(e);
					}
				}
			}
		} else {
			log.error(sourceFile.getName() + " File not exists");
		}
	}

	private void exportPDF(File sourceFile, File destinationFile, Catalogo catalogo, Integer dpi)
			throws CustomException {
		if (!destinationFile.exists()) {
			destinationFile.mkdir();
			log.info("Folder Created -> " + destinationFile.getAbsolutePath());
		}
		if (sourceFile.exists()) {
			PDDocument document = null;
			try {
				log.info("Images copied to Folder: " + destinationFile.getName());
				document = PDDocument.load(sourceFile);
				PDFRenderer pdfRenderer = new PDFRenderer(document);

				int numberOfPages = document.getNumberOfPages();

				log.info("Total files to be converted -> " + numberOfPages);

				String fileName = sourceFile.getName().replace(".pdf", "");
				String fileExtension = "jpeg";

				var paginaOutput = new HashMap<Integer, File>();

				for (int i = 0; i < numberOfPages; ++i) {

					File outPutFile = new File(
							destinationFile + File.separator + fileName + "_" + (i + 1) + "." + fileExtension);

					BufferedImage bImage = pdfRenderer.renderImageWithDPI(i, dpi, ImageType.RGB);

					ImageIO.write(bImage, fileExtension, outPutFile);

					var idPagina = (i + 1);

					paginaOutput.put(idPagina, outPutFile);

					catalogo.getCatalogoPaginas().add(new CatalogoPagina(catalogo, idPagina, ZonedDateTime.now()));

				}

				log.info("paginaOutput: {}", paginaOutput.size());

				catalogo = catalogoService.salvar(catalogo);

				for (var cp : catalogo.getCatalogoPaginas()) {
					try {

						var fileDest = new File(sistemaDiretorioCatalogos + File.separator + catalogo.getCodigo()
								+ File.separator + "paginas" + File.separator + cp.getCodigo() + "." + fileExtension);

						log.info("paginaOutput: {}", cp.getPagina());

						FileUtils.copyFile(paginaOutput.get(cp.getPagina()), fileDest);

					} catch (IOException e) {
						log.error("ERRO ", e);
					}
				}

				log.info("Converted Images are saved at -> " + destinationFile.getAbsolutePath());

			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException(e);
			} finally {
				if (document != null) {
					try {
						document.close();
					} catch (IOException e) {
						e.printStackTrace();
						throw new CustomException(e);
					}
				}
			}
		} else {
			log.error(sourceFile.getName() + " File not exists");
		}
	}

	public File downloadCatalogo(Integer codigoCatalogo) throws Exception {

		var registro = catalogoPaginasRepository.obterPaginasDoCatalogo(codigoCatalogo);

		var files = Utils.diretorioTemporario();

		for (var caPagina : registro) {
			var destFile = new File(files.toString() + File.separator + caPagina.getCodigo() + "_"
					+ dtFormat.format(caPagina.getDataAlterado()));

			var fs = new File(sistemaDiretorioCatalogos + File.separator + caPagina.getCatalogo().getCodigo()
					+ File.separator + "paginas" + File.separator + caPagina.getCodigo() + ".jpeg");

			if (!fs.exists()) {
				throw new Exception("Arquivo " + fs.toString() + " não localizado.");
			}

			FileUtils.copyFile(fs, destFile);
		}

		var fileZip = File.createTempFile(UUID.randomUUID().toString(), ".zip");

		Funcoes.zipDirectory(new ArrayList<>(), files, fileZip.toString());

		return fileZip;
	}

	public File downloadCatalogoPaginas(Integer codigoCatalogo, String codigoCatalogoPaginas) throws Exception {

		Integer[] codigos = new ObjectMapper().readValue(codigoCatalogoPaginas, Integer[].class);

		var registro = catalogoPaginasRepository.obterPaginasDoCatalogo(codigoCatalogo, codigos);

		var files = new File(System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID().toString());
		if (!files.exists()) {
			files.mkdirs();
		}

		for (var caPagina : registro) {
			var destFile = new File(files.toString() + File.separator + caPagina.getCodigo() + "_"
					+ dtFormat.format(caPagina.getDataAlterado()));

			var fs = new File(sistemaDiretorioCatalogos + File.separator + caPagina.getCatalogo().getCodigo()
					+ File.separator + "paginas" + File.separator + caPagina.getCodigo() + ".jpeg");

			if (!fs.exists()) {
				throw new Exception("Arquivo " + fs.toString() + " não localizado.");
			}

			FileUtils.copyFile(fs, destFile);
		}

		var fileZip = File.createTempFile(UUID.randomUUID().toString(), ".zip");

		Funcoes.zipDirectory(new ArrayList<>(), files, fileZip.toString());

		return fileZip;
	}
}
