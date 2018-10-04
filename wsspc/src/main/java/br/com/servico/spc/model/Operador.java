package br.com.servico.spc.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(schema = "public", name = "operador")
public class Operador {

	@Id
	@SequenceGenerator(name = "IdOpr", sequenceName = "IdOpr_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdOpr")
	@ApiModelProperty(notes = "Não é necessário informar")
	private Integer IdOpr;

	@Column(nullable = false)
	@ApiModelProperty(notes = "Operador do convênio com SPC")
	private String username;

	@Column(nullable = false)
	@ApiModelProperty(notes = "Senha do convênio com SPC")
	private String password;

	@Column(length = 1, nullable = false)
	@ApiModelProperty(notes = "Informe H para ambiente de homologação e P para produção")
	private String ambiente;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "spcoperador")
	@ApiModelProperty(notes = "lista de pessoas a incluir ou excluir do spc")
	private Collection<Spc> spcs;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIdOpr() {
		return IdOpr;
	}

	public void setIdOpr(Integer idOpr) {
		IdOpr = idOpr;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public Collection<Spc> getSpcs() {
		return spcs;
	}

	public void setSpcs(Collection<Spc> spcs) {
		this.spcs = spcs;
	}

}
