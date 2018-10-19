package com.bebidas.br.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@ApiModelProperty(notes = "volume da bebida")
	private Integer volume;

	@OneToOne
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

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public TipoBebida getTipoBebida() {
		return tipoBebida;
	}

	public void setTipoBebida(TipoBebida tipoBebida) {
		this.tipoBebida = tipoBebida;
	}

}
