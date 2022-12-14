package br.com.ams.backcatalogo.service;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.ams.backcatalogo.exception.EntityNotFoundException;
import br.com.ams.backcatalogo.model.Catalogo;
import br.com.ams.backcatalogo.repository.CatalogoRepository;

@Service
public class CatalogoService implements ContractService<Catalogo> {

	@Value("${diretorio.catalogos}")
	String sistemaDiretorioCatalogos;

	@Autowired
	private CatalogoRepository repository;

	@Override
	public List<Catalogo> obterTodos() throws Exception {
		return this.repository.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Catalogo salvar(Catalogo entity) throws Exception {
		return repository.save(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Catalogo atualizar(Integer codigo, Catalogo entity) throws Exception {
		return repository.save(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void remover(Integer codigo) throws Exception {
		try {
			this.repository.deleteById(codigo);
		} finally {
			FileUtils.deleteDirectory(new File(sistemaDiretorioCatalogos + File.separator + codigo));
		}
	}

	@Override
	public Catalogo obterCodigo(Integer codigo) throws Exception {
		return this.repository.findById(codigo)
				.orElseThrow(() -> new EntityNotFoundException("Catalogo", codigo));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Catalogo obterCodigoLazy(Integer codigo) throws Exception {
		var catalogo = this.repository.findById(codigo)
				.orElseThrow(() -> new EntityNotFoundException("Catalogo", codigo));
		var paginas = catalogo.getCatalogoPaginas();
		if (paginas != null) {
			for (var pp : paginas) {
				var paginasP = pp.getCatalogoPaginaProdutos();
				if (paginasP != null) {
					paginasP.size();
				}
			}
		}
		return catalogo;
	}

}
