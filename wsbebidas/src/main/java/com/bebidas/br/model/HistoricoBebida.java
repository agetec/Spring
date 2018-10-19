package com.bebidas.br.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(schema = "bebidas", name = "historico_bebida")
public class HistoricoBebida {

	@Id
	@SequenceGenerator(name = "IdHistorico", sequenceName = "IdHistorico_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdHistorico")
	@ApiModelProperty(notes = "Não é necessário informar")
	private Long IdHistorico;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(notes = "data e hora da inserção do histórico")
	private Date datahis;

	@Column(length = 250, nullable = false)
	@ApiModelProperty(notes = "responsavel pela inserção no histórico")
	private String responsavel;

	@Column(length = 1, nullable = false)
	@ApiModelProperty(notes = "tipo de movimento do histórico(E=entrada & S=saída)")
	private String tipoMovimento;

	@OneToOne()
	@JoinColumn(nullable=false)
	private Estoque estoque;

	public Long getIdHistorico() {
		return IdHistorico;
	}

	public void setIdHistorico(Long idHistorico) {
		IdHistorico = idHistorico;
	}

	public Date getDatahis() {
		return datahis;
	}

	public void setDatahis(Date datahis) {
		this.datahis = datahis;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(String tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}

}