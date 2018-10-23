package com.bebidas.br.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(schema = "bebidas", name = "bebida")
public class Bebida {

	@Id
	@SequenceGenerator(name = "IdBebida", sequenceName = "IdBebida_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdBebida")
	@ApiModelProperty(notes = "Não é necessário informar")
	private Long IdBebida;

	@Column(length = 250, nullable = false)
	@ApiModelProperty(notes = "Nome da bebida")
	private String nome;

	@Column(nullable = false)
	@ApiModelProperty(notes = "volume da bebida. Informar em litros")
	private Double volume;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(nullable=false)
	@ApiModelProperty(notes = "relacionamento do tipo da bebida")
	private TipoBebida tipoBebida;
	
	
	public Long getIdBebida() {
		return IdBebida;
	}

	public void setIdBebida(Long idBebida) {
		IdBebida = idBebida;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public TipoBebida getTipoBebida() {
		return tipoBebida;
	}

	public void setTipoBebida(TipoBebida tipoBebida) {
		this.tipoBebida = tipoBebida;
	}

}
