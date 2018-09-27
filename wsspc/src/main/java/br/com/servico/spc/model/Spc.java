package br.com.servico.spc.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(schema="public",name="spc")
public class Spc {
	@Id
	@SequenceGenerator(name = "IdSpc", sequenceName = "IdSpc_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdSpc")
	private Long IdSpc;
	
	@Column(length=2,nullable=false)
	private String tipoPessoa;
	
	@Transient
	private String dadosPessoaFisica;
	
	@Column(length=11,nullable=false)
	private String cpf;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataNascimento;
	
	@Transient
	private String telefone;
	
	@Column(length=9,nullable=false)
	private String numeroTelelefone;
	
	@Column(length=2,nullable=false)
	private String dddTelefone;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCompra;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataVencimento;
	
	@Column(length=1,nullable=false)
	private String codigoTipoDevedor;
	
	@Column(nullable=false)
	private Integer numeroContrato;
	
	@Column(nullable=false,scale=2,precision=12)
	private BigDecimal valorDebito;
	
	@Transient
	private String naturezaInclusao;
	
	@Column(nullable=false)
	private Integer idNatureza;
	
	@Column(length=1,nullable=false)
	private String enderecoPessoa;
	
	@Column(length=8,nullable=false)
	private String cep;
	
	@Column(nullable=false)
	private String logradouro;
	
	@Column(nullable=false)
	private String bairro;
	
	@Column(nullable=false)
	private Integer numero;
	
	@Column(length=1,nullable=false)
	private String tipoOperacao;
	
	
	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getDadosPessoaFisica() {
		return dadosPessoaFisica;
	}

	public void setDadosPessoaFisica(String dadosPessoaFisica) {
		this.dadosPessoaFisica = dadosPessoaFisica;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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

	public String getNaturezaInclusao() {
		return naturezaInclusao;
	}

	public void setNaturezaInclusao(String naturezaInclusao) {
		this.naturezaInclusao = naturezaInclusao;
	}

	public Integer getIdNatureza() {
		return idNatureza;
	}

	public void setIdNatureza(Integer idNatureza) {
		this.idNatureza = idNatureza;
	}

	public String getEnderecoPessoa() {
		return enderecoPessoa;
	}

	public void setEnderecoPessoa(String enderecoPessoa) {
		this.enderecoPessoa = enderecoPessoa;
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

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	

	

}
