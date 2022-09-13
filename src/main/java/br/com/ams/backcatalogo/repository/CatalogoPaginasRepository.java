package br.com.ams.backcatalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ams.backcatalogo.model.CatalogoPagina;

@Repository
public interface CatalogoPaginasRepository extends JpaRepository<CatalogoPagina, Integer> {
	@Query("Select c From CatalogoPagina c where c.catalogo.codigo = ?1")
	List<CatalogoPagina> obterPaginasDoCatalogo(Integer codigo);

	@Query("Select c From CatalogoPagina c where c.catalogo.codigo = ?1 and c.codigo IN (?2)")
	List<CatalogoPagina> obterPaginasDoCatalogo(Integer codigo, Integer[] codigos);

	@Query("Select c From CatalogoPagina c where c.catalogo.codigo = ?1 and c.codigo = ?2")
	CatalogoPagina obterPaginaDoCatalogo(Integer codigo, Integer codigos);
}
