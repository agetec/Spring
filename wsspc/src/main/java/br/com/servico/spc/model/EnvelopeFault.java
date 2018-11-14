package br.com.servico.spc.model;

public class EnvelopeFault {

	private String s;
	private BodyFault bodyFault = new BodyFault();

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public BodyFault getBodyFault() {
		return bodyFault;
	}

	public void setBodyFault(BodyFault bodyFault) {
		this.bodyFault = bodyFault;
	}

}
