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
@Table(schema = "bebidas", name = "estoque")
public class Estoque {
	@Id
	@SequenceGenerator(name = "IdEstoque", sequenceName = "IdEstoque_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdEstoque")
	@ApiModelProperty(notes = "Não é necessário informar")
	private Long idEstoque;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Bebida bebida;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Sessao sessao;

	@Column(nullable = false)
	private Integer qtd;

	public Long getIdEstoque() {
		return idEstoque;
	}

	public void setIdEstoque(Long idEstoque) {
		this.idEstoque = idEstoque;
	}

	public Bebida getBebida() {
		return bebida;
	}

	public void setBebida(Bebida bebida) {
		this.bebida = bebida;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

}
