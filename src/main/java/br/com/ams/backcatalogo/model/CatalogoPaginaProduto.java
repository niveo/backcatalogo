package br.com.ams.backcatalogo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
public class CatalogoPaginaProduto implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer codigo;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	private CatalogoPagina catalogoPagina;

	@OneToOne(fetch = FetchType.EAGER)
	private Produto produto;

	@Column
	private Float inicialPosicalX;

	@Column
	private Float finalPosicalX;

	@Column
	private Float inicialPosicalY;

	@Column
	private Float finalPosicalY;

	@Column
	private Float width;

	@Column
	private Float height;

}
