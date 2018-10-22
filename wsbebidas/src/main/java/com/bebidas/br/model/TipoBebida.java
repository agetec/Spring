package com.bebidas.br.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(schema = "bebidas", name = "tipo_bebida")
public class TipoBebida {
	@Id
	@SequenceGenerator(name = "IdTipoBebida", sequenceName = "IdTipoBebida_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdTipoBebida")
	@ApiModelProperty(notes = "Não é necessário informar")
	private Long idTipoBebida;

	@Column(length = 250, nullable = false)
	@ApiModelProperty(notes = "Descrição do tipo da bebida")
	private String descricao;

	@Column(length = 2, nullable = false)
	@ApiModelProperty(notes = "tipo da bebida")
	private String tipo;

	
	public Long getIdTipoBebida() {
		return idTipoBebida;
	}

	public void setIdTipoBebida(Long idTipoBebida) {
		this.idTipoBebida = idTipoBebida;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
