package br.com.servico.spc.model;

public class Body {

	private Fault fault = new Fault();
	private Incluir incluir = new Incluir();
	private Excluir excluir;

	public Fault getFault() {
		return fault;
	}

	public void setFault(Fault fault) {
		this.fault = fault;
	}

	public Incluir getIncluir() {
		return incluir;
	}

	public void setIncluir(Incluir incluir) {
		this.incluir = incluir;
	}

	public Excluir getExcluir() {
		return excluir;
	}

	public void setExcluir(Excluir excluir) {
		this.excluir = excluir;
	}

}
