package br.com.ams.backcatalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ams.backcatalogo.model.CatalogoPaginaProduto;

@Repository
public interface CatalogoPaginaProdutoRepository extends JpaRepository<CatalogoPaginaProduto, Integer> {
	@Query("Select c From CatalogoPaginaProduto c where c.catalogoPagina.codigo = ?1")
	List<CatalogoPaginaProduto> obterRegistrosPagina(Integer codigoPagina);
	
	@Query("Select count(c) From CatalogoPaginaProduto c where c.catalogoPagina.codigo = ?1")
	long obterRegistrosPaginaConta(Integer codigoPagina);
}
