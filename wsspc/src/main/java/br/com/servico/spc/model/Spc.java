package br.com.servico.spc.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(schema="public",name="spc")
public class Spc {
	@Id
	@SequenceGenerator(name = "IdSpc", sequenceName = "IdSpc_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdSpc")
	@ApiModelProperty(notes = "Não é necessário informar")
	private Long IdSpc;
	
	@Column(length=2,nullable=false)
	@ApiModelProperty(notes = "Informe sempre F nesse campo")
	private String tipoPessoa;
	
	
	@Column(length=11)
	private String cpf;
	
	private String nome;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataNascimento;
	
		
	@Column(length=9)
	private String numeroTelelefone;
	
	@Column(length=2)
	private String dddTelefone;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCompra;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataVencimento;
	
	@ApiModelProperty(notes = "Informe sempre C nesse campo")
	@Column(length=1,nullable=false)
	private String codigoTipoDevedor;
	
	@Column(nullable=false)
	private Integer numeroContrato;
	
	@Column(nullable=false,scale=2,precision=12)
	@ApiModelProperty(notes = "Informe sempre no formato ###.00")
	private BigDecimal valorDebito;
	
	@Column(nullable=false)
	@ApiModelProperty(notes = "Informe sempre 1 nesse campo")
	private Integer idNatureza;
	
	@Column(length=8)
	private String cep;
	
	private String logradouro;
	
	private String bairro;
	
	@Column(length=10)
	private String numero;
	
	@Column(length=1,nullable=false)
	@ApiModelProperty(notes = "Informe sempre E para exclusão e I para inclusao")
	private String tipoOperacao;
	
	@Column(nullable=false)
	@ApiModelProperty(notes = "Informe sempre 1 nesse campo")
	private Integer idExclusao;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Operador spcoperador;
	
	@Column(nullable=false)
	private Integer codsis;
	
	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNumeroTelelefone() {
		return numeroTelelefone;
	}

	public void setNumeroTelelefone(String numeroTelelefone) {
		this.numeroTelelefone = numeroTelelefone;
	}

	public String getDddTelefone() {
		return dddTelefone;
	}

	public void setDddTelefone(String dddTelefone) {
		this.dddTelefone = dddTelefone;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public String getCodigoTipoDevedor() {
		return codigoTipoDevedor;
	}

	public void setCodigoTipoDevedor(String codigoTipoDevedor) {
		this.codigoTipoDevedor = codigoTipoDevedor;
	}

	public Integer getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(Integer numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public BigDecimal getValorDebito() {
		return valorDebito;
	}

	public void setValorDebito(BigDecimal valorDebito) {
		this.valorDebito = valorDebito;
	}

	public Integer getIdNatureza() {
		return idNatureza;
	}

	public void setIdNatureza(Integer idNatureza) {
		this.idNatureza = idNatureza;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Long getIdSpc() {
		return IdSpc;
	}

	public void setIdSpc(Long idSpc) {
		IdSpc = idSpc;
	}

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public Integer getIdExclusao() {
		return idExclusao;
	}

	public void setIdExclusao(Integer idExclusao) {
		this.idExclusao = idExclusao;
	}

	public Operador getSpcoperador() {
		return spcoperador;
	}

	public void setSpcoperador(Operador spcoperador) {
		this.spcoperador = spcoperador;
	}

	public Integer getCodsis() {
		return codsis;
	}

	public void setCodsis(Integer codsis) {
		this.codsis = codsis;
	}
	

	

}
