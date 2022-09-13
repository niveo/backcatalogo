package br.com.ams.backcatalogo.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = { "codigo" })
@Entity
public class CatalogoPagina implements Serializable {
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer codigo;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	private Catalogo catalogo;

	@Column
	private Integer pagina;

	@Column
	private ZonedDateTime dataAlterado;

	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "catalogoPagina", targetEntity = CatalogoPaginaProduto.class)
	private List<CatalogoPaginaProduto> catalogoPaginaProdutos;

	public CatalogoPagina(Catalogo catalogo, Integer pagina, ZonedDateTime dataAlterado) {
		this.catalogo = catalogo;
		this.pagina = pagina;
		this.dataAlterado = dataAlterado;
	}
}
