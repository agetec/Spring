package br.com.servico.spc.controler;

import java.util.Collection;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.apache.tomcat.util.codec.binary.Base64;
import br.com.servico.spc.model.Operador;
import br.com.servico.spc.model.Spc;

public class SoapClienteControler {

	private static String soapEndpointUrlHom = "https://treina.spc.org.br/spc/remoting/ws/insumo/spc/spcWebService?wsdl";
	private static String soapEndpointUrlPro = "https://servicos.spc.org.br/spc/remoting/ws/insumo/spc/spcWebService?wsdl";
	private static String soapActionIncusaoHom = "http://treina.spc.insumo.spcjava.spcbrasil.org/SpcWebService/incluirSpcRequest";
	private static String soapActionExclusaoHom = "http://treina.spc.insumo.spcjava.spcbrasil.org/SpcWebService/excluirSpcRequest";
	private static String soapActionIncusaoPro= "http://servicos.spc.insumo.spcjava.spcbrasil.org/SpcWebService/incluirSpcRequest";
	private static String soapActionExclusaoPro = "http://servicos.spc.insumo.spcjava.spcbrasil.org/SpcWebService/excluirSpcRequest";

	public SOAPMessage callSoapWebServiceInclusao(Collection<Spc> spc, Operador opr) {
		String soapEndpointUrl = "";
		String soapActionIncusao = "";
		try {
			if (opr.getAmbiente().equals("P")) {
				soapEndpointUrl = soapEndpointUrlPro;
				soapActionIncusao=soapActionIncusaoPro;
			}
			else {
				soapEndpointUrl = soapEndpointUrlHom;
				soapActionIncusao=soapActionIncusaoHom;
			}
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequestIncusao(soapActionIncusao, spc, opr),
					soapEndpointUrl);
			System.out.println("Response SOAP Message:");
			soapResponse.writeTo(System.out);
			System.out.println();
			soapConnection.close();
			return soapResponse;
		} catch (Exception e) {
			System.err.println(
					"\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
			e.printStackTrace();
		}
		return null;

	}

	public SOAPMessage callSoapWebServiceExclusao(Collection<Spc> spc, Operador opr) {
		String soapEndpointUrl = "";
		String soapActionExclusao="";
		try {
			if (opr.getAmbiente().equals("P")) {
				soapEndpointUrl = soapEndpointUrlPro;
				soapActionExclusao=soapActionExclusaoPro;
			}
			else {
				soapEndpointUrl = soapEndpointUrlHom;
				soapActionExclusao=soapActionExclusaoHom;
			}
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequestExclusao(soapActionExclusao, spc, opr),
					soapEndpointUrl);
			System.out.println("Response SOAP Message:");
			soapResponse.writeTo(System.out);
			System.out.println();
			soapConnection.close();
			return soapResponse;
		} catch (Exception e) {
			System.err.println(
					"\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
			e.printStackTrace();
		}
		return null;
	}

	private SOAPMessage createSOAPRequestIncusao(String soapActionIncusao, Collection<Spc> spc, Operador opr)
			throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		createSoapEnvelopeInclusao(soapMessage, spc);
		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapActionIncusao);
		headers.addHeader("Content-Type", "text/xml;charset=UTF-8");
		if (opr != null && opr.getUsername() != null && opr.getUsername() != null) {
			headers.addHeader("Authorization",
					"Basic " + Base64.encodeBase64String((opr.getUsername() + ":" + opr.getPassword()).getBytes()));
		} else {
			headers.addHeader("Authorization", "Basic " + Base64.encodeBase64String("399037:26092018".getBytes()));
		}
		soapMessage.saveChanges();
		System.out.println("Request SOAP Message:");
		soapMessage.writeTo(System.out);
		System.out.println("\n");
		return soapMessage;
	}

	private void createSoapEnvelopeInclusao(SOAPMessage soapMessage, Collection<Spc> spc) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String myNamespace = "web:incluirSpc";
		String myNamespaceURI = "http://webservice.spc.insumo.spcjava.spcbrasil.org/";
		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("web", myNamespaceURI);
		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement(myNamespace);
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("insumoSpc");
		SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("tipo-pessoa");
		soapBodyElem2.addTextNode("F");

		SOAPElement dadosPessoaFisica = soapBodyElem1.addChildElement("dados-pessoa-fisica");
		SOAPElement soapBodyElem31 = dadosPessoaFisica.addChildElement("cpf");
		soapBodyElem31.setAttribute("numero", "02499224100");
		SOAPElement soapBodyElem32 = dadosPessoaFisica.addChildElement("nome");
		soapBodyElem32.addTextNode("Lucas dos santos de souza");
		SOAPElement soapBodyElem33 = dadosPessoaFisica.addChildElement("data-nascimento");
		soapBodyElem33.addTextNode("1975-12-23T21:02:14");
		SOAPElement soapBodyElem34 = dadosPessoaFisica.addChildElement("telefone");

		soapBodyElem34.setAttribute("numero", "991556635");
		soapBodyElem34.setAttribute("numero-ddd", "67");

		SOAPElement soapBodyElem4 = soapBodyElem1.addChildElement("data-compra");
		soapBodyElem4.addTextNode("2015-02-01T21:02:14");
		SOAPElement soapBodyElem5 = soapBodyElem1.addChildElement("data-vencimento");
		soapBodyElem5.addTextNode("2015-03-01T21:02:14");
		SOAPElement soapBodyElem6 = soapBodyElem1.addChildElement("codigo-tipo-devedor");
		soapBodyElem6.addTextNode("C");
		SOAPElement soapBodyElem7 = soapBodyElem1.addChildElement("numero-contrato");
		soapBodyElem7.addTextNode("5");
		SOAPElement soapBodyElem8 = soapBodyElem1.addChildElement("valor-debito");
		soapBodyElem8.addTextNode("99.99");

		SOAPElement soapBodyElem9 = soapBodyElem1.addChildElement("natureza-inclusao");
		SOAPElement soapBodyElem91 = soapBodyElem9.addChildElement("id");
		soapBodyElem91.addTextNode("1");

		SOAPElement enderecoPessoa = soapBodyElem1.addChildElement("endereco-pessoa");
		SOAPElement cep = enderecoPessoa.addChildElement("cep");
		cep.addTextNode("11740000");
		SOAPElement logradouro = enderecoPessoa.addChildElement("logradouro");
		logradouro.addTextNode("Avenida Condessa");
		SOAPElement bairro = enderecoPessoa.addChildElement("bairro");
		bairro.addTextNode("Centro");
		SOAPElement numero = enderecoPessoa.addChildElement("numero");
		numero.addTextNode("999");

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
		} else {
			headers.addHeader("Authorization", "Basic " + Base64.encodeBase64String("399037:26092018".getBytes()));
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
		SOAPElement soapBodyElem = soapBody.addChildElement(myNamespace);
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("excluir");
		SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("tipo-pessoa");
		soapBodyElem2.addTextNode("F");
		SOAPElement dadosPessoaFisica = soapBodyElem1.addChildElement("dados-pessoa-fisica");
		SOAPElement soapBodyElem31 = dadosPessoaFisica.addChildElement("cpf");
		soapBodyElem31.setAttribute("numero", "02499224100");
		SOAPElement soapBodyElem5 = soapBodyElem1.addChildElement("data-vencimento");
		soapBodyElem5.addTextNode("2015-03-01T21:02:14");
		SOAPElement soapBodyElem7 = soapBodyElem1.addChildElement("numero-contrato");
		soapBodyElem7.addTextNode("5");
		SOAPElement soapBodyElem9 = soapBodyElem1.addChildElement("motivo-exclusao");
		SOAPElement soapBodyElem91 = soapBodyElem9.addChildElement("id");
		soapBodyElem91.addTextNode("1");

	}

}
