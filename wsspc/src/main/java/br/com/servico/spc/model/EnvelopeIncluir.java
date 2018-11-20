package br.com.servico.spc.model;

public class EnvelopeIncluir {
	private String s;
	private BodyIncluir bodyincluir = new BodyIncluir();

	public BodyIncluir getBodyincluir() {
		return bodyincluir;
	}

	public void setBodyincluir(BodyIncluir bodyincluir) {
		this.bodyincluir = bodyincluir;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

}
