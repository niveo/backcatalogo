package br.com.ams.backcatalogo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ams.backcatalogo.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	@Query("Select c From Produto c where c.referencia = ?1")
	Optional<Produto> obterReferencia(String referencia);
}
