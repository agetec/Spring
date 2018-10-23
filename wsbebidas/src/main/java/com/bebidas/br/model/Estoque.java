package com.bebidas.br.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
	private Long IdEstoque;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Bebida bebida;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Sessao sessao;

	@Column(nullable = false)
	private Integer qtd;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "estoque", fetch = FetchType.LAZY)
	@ApiModelProperty(notes = "lista de historico da bebida")
	private Collection<HistoricoBebida> hisBebidas=new ArrayList<HistoricoBebida>();

	public Long getIdEstoque() {
		return IdEstoque;
	}

	public void setIdEstoque(Long idEstoque) {
		IdEstoque = idEstoque;
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

	public Collection<HistoricoBebida> getHisBebidas() {
		return hisBebidas;
	}

	public void setHisBebidas(Collection<HistoricoBebida> hisBebidas) {
		this.hisBebidas = hisBebidas;
	}

}
