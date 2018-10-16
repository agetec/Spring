package br.com.servico.spc.model;

public class Fault {

	
	private String faultcode;
	private String faultstring;
	private String ns2;
	private String ns3;
	
	public String getFaultcode() {
		return faultcode;
	}
	public void setFaultcode(String faultcode) {
		this.faultcode = faultcode;
	}
	public String getFaultstring() {
		return faultstring;
	}
	public void setFaultstring(String faultstring) {
		this.faultstring = faultstring;
	}
	public String getNs2() {
		return ns2;
	}
	public void setNs2(String ns2) {
		this.ns2 = ns2;
	}
	public String getNs3() {
		return ns3;
	}
	public void setNs3(String ns3) {
		this.ns3 = ns3;
	}
	
	
	
}
