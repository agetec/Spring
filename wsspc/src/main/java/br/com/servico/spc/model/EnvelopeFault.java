package br.com.servico.spc.model;

public class EnvelopeFault {

	private String s;
	private BodyFault body=new BodyFault();
	
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	public BodyFault getBody() {
		return body;
	}
	public void setBody(BodyFault body) {
		this.body = body;
	}
	
	
	
}
