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
@Table(schema = "bebidas", name = "sessao")
public class Sessao {

	@Id
	@SequenceGenerator(name = "IdSessao", sequenceName = "IdSessao_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdSessao")
	@ApiModelProperty(notes = "Não é necessário informar")
	private Long IdSessao;

	@Column(length = 250, nullable = false)
	@ApiModelProperty(notes = "Descrição da sessão")
	private String descricao;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(nullable=false)
	@ApiModelProperty(notes = "tipo de bebida da sessão")
	private TipoBebida tipoBebida;

	@Column(nullable = false)
	@ApiModelProperty(notes = "capacidade de armazenamento")
	private Double capacidade;

	public Long getIdSessao() {
		return IdSessao;
	}

	public void setIdSessao(Long idSessao) {
		IdSessao = idSessao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoBebida getTipoBebida() {
		return tipoBebida;
	}

	public void setTipoBebida(TipoBebida tipoBebida) {
		this.tipoBebida = tipoBebida;
	}

	public Double getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Double capacidade) {
		this.capacidade = capacidade;
	}

}
