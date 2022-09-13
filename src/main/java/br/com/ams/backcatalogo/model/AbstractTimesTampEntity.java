package br.com.ams.backcatalogo.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class AbstractTimesTampEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic
	@CreationTimestamp
	@Column
	private ZonedDateTime dataCadastrado;

	@Basic
	@UpdateTimestamp
	@Column
	private ZonedDateTime dataAlterado;

}
