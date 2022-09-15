package br.com.ams.backcatalogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.ams.backcatalogo.exception.EntityNotFoundException;
import br.com.ams.backcatalogo.model.CatalogoPagina;
import br.com.ams.backcatalogo.repository.CatalogoPaginasRepository;

@Service
public class CatalogoPaginasService implements ContractService<CatalogoPagina> {

	@Autowired
	private CatalogoPaginasRepository repository;

	@Override
	public List<CatalogoPagina> obterTodos() throws Exception {
		return this.repository.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public CatalogoPagina salvar(CatalogoPagina entity) throws Exception {
		return repository.save(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public CatalogoPagina atualizar(Integer codigo, CatalogoPagina entity) throws Exception {
		return repository.save(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void remover(Integer codigo) throws Exception {
		this.repository.deleteById(codigo);
	}

	@Override
	public CatalogoPagina obterCodigo(Integer codigo) throws Exception {
		return this.repository.findById(codigo)
				.orElseThrow(() -> new EntityNotFoundException("Catalogo Pagina", codigo));
	}

	/*
	 * @Value("${diretorio.catalogos}") private String diretorioArquivoCatalogos;
	 * 
	 * @Autowired ObjectMapper objectMapper;
	 * 
	 * private static final DateTimeFormatter dtFormat =
	 * DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	 * 
	 * public File downloadTodos() throws Exception {
	 * 
	 * var registro = repository.findAll();
	 * 
	 * File files = Utils.diretorioTemporario();
	 * 
	 * for (var caPagina : registro) {
	 * 
	 * var destFile = new File(files.toString() + File.separator +
	 * caPagina.getCodigo() + "_" + dtFormat.format(caPagina.getDataAlterado()));
	 * 
	 * FileUtils .copyFile( new File(diretorioArquivoCatalogos + File.separator +
	 * caPagina.getCatalogo().getCodigo() + File.separator + "paginas" +
	 * File.separator + caPagina.getCodigo() + ".jpeg"), destFile); }
	 * 
	 * File fileZip = Utils.criarArquivoDinamicoTemporario(".zip");
	 * 
	 * Funcoes.zipDirectory(new ArrayList<>(), files, fileZip.toString());
	 * 
	 * return fileZip; }
	 * 
	 * public File downloadCatalogo(Integer codigoCatalogo) throws Exception {
	 * 
	 * var registro = repository.obterPaginasDoCatalogo(codigoCatalogo);
	 * 
	 * File files = Utils.diretorioTemporario();
	 * 
	 * for (var caPagina : registro) { var destFile = new File(files.toString() +
	 * File.separator + caPagina.getCodigo() + "_" +
	 * dtFormat.format(caPagina.getDataAlterado()));
	 * 
	 * var fs = new File(diretorioArquivoCatalogos + File.separator +
	 * caPagina.getCatalogo().getCodigo() + File.separator + "paginas" +
	 * File.separator + caPagina.getCodigo() + ".jpeg");
	 * 
	 * if (!fs.exists()) { throw new Exception("Arquivo " + fs.toString() +
	 * " não localizado."); }
	 * 
	 * FileUtils.copyFile(fs, destFile); }
	 * 
	 * File fileZip = File.createTempFile(UUID.randomUUID().toString(), ".zip");
	 * 
	 * Funcoes.zipDirectory(new ArrayList<>(), files, fileZip.toString());
	 * 
	 * return fileZip; }
	 * 
	 * public File downloadCatalogoPaginas(Integer codigoCatalogo, String
	 * codigoCatalogoPaginas) throws Exception {
	 * 
	 * Integer[] codigos = objectMapper.readValue(codigoCatalogoPaginas,
	 * Integer[].class);
	 * 
	 * var registro = repository.obterPaginasDoCatalogo(codigoCatalogo, codigos);
	 * 
	 * File files = new File(System.getProperty("java.io.tmpdir") + File.separator +
	 * UUID.randomUUID().toString()); if (!files.exists()) { files.mkdirs(); }
	 * 
	 * for (var caPagina : registro) { var destFile = new File(files.toString() +
	 * File.separator + caPagina.getCodigo() + "_" +
	 * dtFormat.format(caPagina.getDataAlterado()));
	 * 
	 * var fs = new File(diretorioArquivoCatalogos + File.separator +
	 * caPagina.getCatalogo().getCodigo() + File.separator + "paginas" +
	 * File.separator + caPagina.getCodigo() + ".jpeg");
	 * 
	 * if (!fs.exists()) { throw new Exception("Arquivo " + fs.toString() +
	 * " não localizado."); }
	 * 
	 * FileUtils.copyFile(fs, destFile); }
	 * 
	 * File fileZip = File.createTempFile(UUID.randomUUID().toString(), ".zip");
	 * 
	 * Funcoes.zipDirectory(new ArrayList<>(), files, fileZip.toString());
	 * 
	 * return fileZip; }
	 * 
	 * public File downloadCatalogoPagina(Integer codigoCatalogo, Integer
	 * codigoCatalogoPaginas) throws Exception {
	 * 
	 * var registro = repository.obterPaginaDoCatalogo(codigoCatalogo,
	 * codigoCatalogoPaginas);
	 * 
	 * File destFile = new File(System.getProperty("java.io.tmpdir") +
	 * File.separator + registro.getCodigo() + "_" +
	 * dtFormat.format(registro.getDataAlterado()));
	 * 
	 * FileUtils .copyFile( new File(diretorioArquivoCatalogos + File.separator +
	 * registro.getCatalogo().getCodigo() + File.separator + "paginas" +
	 * File.separator + registro.getCodigo() + ".jpeg"), destFile);
	 * 
	 * File fileZip = Utils.criarArquivoDinamicoTemporario(".zip");
	 * 
	 * Funcoes.zipSingleFile(destFile, fileZip.toString());
	 * 
	 * return fileZip; }
	 */

}
