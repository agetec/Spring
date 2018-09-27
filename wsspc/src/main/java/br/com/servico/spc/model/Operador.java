package br.com.servico.spc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema = "public", name = "operador")
public class Operador {

	@Id
	@SequenceGenerator(name = "IdOpr", sequenceName = "IdOpr_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdOpr")
	private Integer IdOpr;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

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

}
