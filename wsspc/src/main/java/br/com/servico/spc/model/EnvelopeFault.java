package br.com.servico.spc.model;

public class EnvelopeFault {

	private String s;
	private BodyFault bodyfault = new BodyFault();

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public BodyFault getBodyfault() {
		return bodyfault;
	}

	public void setBodyfault(BodyFault bodyfault) {
		this.bodyfault = bodyfault;
	}

	

}
