package br.com.servico.spc.model;

public class EnvelopeIncluir {
	private String s;
	private BodyIncluir bodyIncluir = new BodyIncluir();

	public BodyIncluir getBodyIncluir() {
		return bodyIncluir;
	}

	public void setBodyIncluir(BodyIncluir bodyIncluir) {
		this.bodyIncluir = bodyIncluir;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

}
