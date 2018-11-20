package br.com.servico.spc.controler;

import java.io.ByteArrayOutputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Collection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import br.com.servico.spc.model.Operador;
import br.com.servico.spc.model.Spc;

public class SoapSpcControler {

	private static String soapEndpointUrlHom = "https://treina.spc.org.br/spc/remoting/ws/insumo/spc/spcWebService?wsdl";
	private static String soapEndpointUrlPro = "https://servicos.spc.org.br/spc/remoting/ws/insumo/spc/spcWebService?wsdl";
	private static String soapActionIncusaoHom = "http://treina.spc.insumo.spcjava.spcbrasil.org/SpcWebService/incluirSpcRequest";
	private static String soapActionExclusaoHom = "http://treina.spc.insumo.spcjava.spcbrasil.org/SpcWebService/excluirSpcRequest";
	private static String soapActionIncusaoPro = "http://servicos.spc.insumo.spcjava.spcbrasil.org/SpcWebService/incluirSpcRequest";
	private static String soapActionExclusaoPro = "http://servicos.spc.insumo.spcjava.spcbrasil.org/SpcWebService/excluirSpcRequest";
	SOAPConnectionFactory soapConnectionFactory = null;
	SOAPConnection soapConnection = null;

	public SoapSpcControler() {

	}

	public void connection() {
		try {
			validarCertificado();
			soapConnectionFactory = SOAPConnectionFactory.newInstance();
			soapConnection = soapConnectionFactory.createConnection();
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void destroyConnection() {
		try {
			soapConnection.close();
			soapConnection = null;
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JSONObject callSoapWebServiceInclusao(Collection<Spc> collection, Operador opr) {
		String soapEndpointUrl = "";
		String soapActionIncusao = "";
		if (soapConnection != null) {
			try {
				if (opr.getAmbiente().equals("P")) {
					soapEndpointUrl = soapEndpointUrlPro;
					soapActionIncusao = soapActionIncusaoPro;
				} else {
					soapEndpointUrl = soapEndpointUrlHom;
					soapActionIncusao = soapActionIncusaoHom;
				}

				SOAPMessage soapResponse = soapConnection
						.call(createSOAPRequestInclusao(soapActionIncusao, collection, opr), soapEndpointUrl);
				System.out.println("Response SOAP Message:");
				ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
				soapResponse.writeTo(byteOutStream);
				String res = new String(byteOutStream.toByteArray());
				JSONObject xmlJSONObj = XML.toJSONObject(alteraJson(res));
				System.out.println(res);
				destroyConnection();
				return xmlJSONObj;
			} catch (Exception e) {
				if (soapConnection == null) {
					connection();
					callSoapWebServiceInclusao(collection, opr);
				} else {
					e.printStackTrace();
				}
			}
		} else {
			connection();
			return callSoapWebServiceInclusao(collection, opr);
		}
		return null;
	}

	public JSONObject callSoapWebServiceExclusao(Collection<Spc> spc2, Operador opr) {
		String soapEndpointUrl = "";
		String soapActionExclusao = "";
		if (soapConnection != null) {
			try {
				if (opr.getAmbiente().equals("P")) {
					soapEndpointUrl = soapEndpointUrlPro;
					soapActionExclusao = soapActionExclusaoPro;
				} else {
					soapEndpointUrl = soapEndpointUrlHom;
					soapActionExclusao = soapActionExclusaoHom;
				}
				SOAPMessage soapResponse = soapConnection.call(createSOAPRequestExclusao(soapActionExclusao, spc2, opr),
						soapEndpointUrl);
				System.out.println("Response SOAP Message:");
				ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
				soapResponse.writeTo(byteOutStream);
				String res = new String(byteOutStream.toByteArray());
				JSONObject xmlJSONObj = XML.toJSONObject(alteraJson(res));
				System.out.println(res);
				destroyConnection();
				return xmlJSONObj;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			connection();
			return callSoapWebServiceExclusao(spc2, opr);
		}
		return null;
	}

	private SOAPMessage createSOAPRequestInclusao(String soapActionIncusao, Collection<Spc> spc2, Operador opr)
			throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		createSoapEnvelopeInclusao(soapMessage, spc2);
		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapActionIncusao);
		headers.addHeader("Content-Type", "text/xml;charset=UTF-8");
		if (opr != null && opr.getUsername() != null && opr.getUsername() != null) {
			headers.addHeader("Authorization",
					"Basic " + Base64.encodeBase64String((opr.getUsername() + ":" + opr.getPassword()).getBytes()));
		}
		soapMessage.saveChanges();
		System.out.println("Request SOAP Message:");
		soapMessage.writeTo(System.out);
		System.out.println("\n");
		return soapMessage;
	}

	private void createSoapEnvelopeInclusao(SOAPMessage soapMessage, Collection<Spc> spc2) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();
		String myNamespace = "web:incluirSpc";
		String myNamespaceURI = "http://webservice.spc.insumo.spcjava.spcbrasil.org/";
		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("web", myNamespaceURI);
		// SOAP Body
		SOAPBody soapBody = envelope.getBody();

		soapBody = new AdicionaPessoa().adicionaSpcInclusao(soapBody, myNamespace, spc2);
	}

	private SOAPMessage createSOAPRequestExclusao(String soapActionExclusao, Collection<Spc> spc, Operador opr)
			throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		createSoapEnvelopeExclusao(soapMessage, spc);
		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapActionExclusao);
		headers.addHeader("Content-Type", "text/xml;charset=UTF-8");
		if (opr != null && opr.getUsername() != null && opr.getUsername() != null) {
			headers.addHeader("Authorization",
					"Basic " + Base64.encodeBase64String((opr.getUsername() + ":" + opr.getPassword()).getBytes()));
		}
		soapMessage.saveChanges();
		System.out.println("Request SOAP Message:");
		soapMessage.writeTo(System.out);
		System.out.println("\n");
		return soapMessage;
	}

	private void createSoapEnvelopeExclusao(SOAPMessage soapMessage, Collection<Spc> spc) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();
		soapMessage.setProperty(javax.xml.soap.SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");
		String myNamespace = "web:excluirSpc";
		String myNamespaceURI = "http://webservice.spc.insumo.spcjava.spcbrasil.org/";
		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("web", myNamespaceURI);
		// SOAP Body
		SOAPBody soapBody = envelope.getBody();

		soapBody = new AdicionaPessoa().adicionaSpcExclusao(soapBody, myNamespace, spc);
	}

	public void validarCertificado() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };
		// Install the all-trusting trust manager
		SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("SSL");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

	public String alteraJson(String json) {
		if (json.indexOf("ns2:Fault") != -1) {
			json = json.replace("S:Envelope", "").replace("S:Body", "bodyfault");
		} else if (json.indexOf("ns2:incluirSpcResponse") != -1) {
			json = json.replace("S:Envelope", "").replace("S:Body", "bodyincluir");
		} else {
			json = json.replace("S:Envelope", "").replace("S:Body", "bodyexcluir");
		}
		json = json.replace("ns2:Fault", "fault").replace("xmlns:ns3", "ns3").replace("xmlns:ns2", "ns2")
				.replace("xmlns:S", "s").replace("ns2:incluirSpcResponse", "incluir")
				.replaceAll("ns2:excluirSpcResponse", "excluir");
		return json;
	}
}
