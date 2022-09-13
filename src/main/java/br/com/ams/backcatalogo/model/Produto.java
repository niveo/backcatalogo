package br.com.ams.backcatalogo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
@AllArgsConstructor()
@NoArgsConstructor
@ToString(of = { "codigo" })
@Entity
public class Produto extends AbstractTimesTampEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer codigo;

	@Column
	private String referencia;

	@Column
	private String descricao;

	public Produto(String descricao, String referencia) {
		this.descricao = descricao;
		this.referencia = referencia;
	}

}
