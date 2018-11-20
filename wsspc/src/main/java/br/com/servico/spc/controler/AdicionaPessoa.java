package br.com.servico.spc.controler;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import br.com.servico.spc.model.Spc;

public class AdicionaPessoa {

	public SOAPBody adicionaSpcInclusao(SOAPBody soapBody, String myNamespace, Collection<Spc> spc) throws SOAPException {
		for (Spc spc2 : spc) {
			SOAPElement soapBodyElem = soapBody.addChildElement(myNamespace);
			SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("insumoSpc");
			SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("tipo-pessoa");
			soapBodyElem2.addTextNode(spc2.getTipoPessoa());
			SOAPElement dadosPessoaFisica = soapBodyElem1.addChildElement("dados-pessoa-fisica");
			SOAPElement soapBodyElem31 = dadosPessoaFisica.addChildElement("cpf");
			soapBodyElem31.setAttribute("numero", spc2.getCpf());
			SOAPElement soapBodyElem32 = dadosPessoaFisica.addChildElement("nome");
			soapBodyElem32.addTextNode(spc2.getNome());
			SOAPElement soapBodyElem33 = dadosPessoaFisica.addChildElement("data-nascimento");
			soapBodyElem33.addTextNode(spc2.getDataNascimento() == null
					? (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(1900, 1, 1))).replace(" ", "T")
					: (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(spc2.getDataNascimento())).replace(" ", "T"));
			SOAPElement soapBodyElem34 = dadosPessoaFisica.addChildElement("telefone");
			soapBodyElem34.setAttribute("numero",
					spc2.getNumeroTelelefone() == null || spc2.getNumeroTelelefone().equals("") ? "0"
							: spc2.getNumeroTelelefone());
			soapBodyElem34.setAttribute("numero-ddd",
					spc2.getNumeroTelelefone().equals("") ? "00" : spc2.getDddTelefone());
			SOAPElement soapBodyElem4 = soapBodyElem1.addChildElement("data-compra");

			soapBodyElem4.addTextNode(
					(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(spc2.getDataCompra())).replace(" ", "T"));
			SOAPElement soapBodyElem5 = soapBodyElem1.addChildElement("data-vencimento");
			if ((new Date().getTime() - spc2.getDataVencimento().getTime()) / 86400000L <= 1800) {
				soapBodyElem5.addTextNode((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(spc2.getDataVencimento()))
						.replace(" ", "T"));
			} else {
				soapBodyElem5.addTextNode((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date(new Date().getYear(), new Date().getMonth() - 1, new Date().getDate())))
								.replace(" ", "T"));
			}
			SOAPElement soapBodyElem6 = soapBodyElem1.addChildElement("codigo-tipo-devedor");
			soapBodyElem6.addTextNode(spc2.getCodigoTipoDevedor());
			SOAPElement soapBodyElem7 = soapBodyElem1.addChildElement("numero-contrato");
			soapBodyElem7.addTextNode(spc2.getNumeroContrato().toString());
			SOAPElement soapBodyElem8 = soapBodyElem1.addChildElement("valor-debito");
			soapBodyElem8.addTextNode(spc2.getValorDebito().toString());
			SOAPElement soapBodyElem9 = soapBodyElem1.addChildElement("natureza-inclusao");
			SOAPElement soapBodyElem91 = soapBodyElem9.addChildElement("id");
			soapBodyElem91.addTextNode(spc2.getIdNatureza().toString());
			SOAPElement enderecoPessoa = soapBodyElem1.addChildElement("endereco-pessoa");
			SOAPElement cep = enderecoPessoa.addChildElement("cep");
			cep.addTextNode(spc2.getCep() == null || spc2.getCep().length() < 8 ? "79002824" : spc2.getCep());
			SOAPElement logradouro = enderecoPessoa.addChildElement("logradouro");
			logradouro.addTextNode(spc2.getLogradouro() == null ? "0" : spc2.getLogradouro());
			SOAPElement bairro = enderecoPessoa.addChildElement("bairro");
			bairro.addTextNode(spc2.getBairro() == null ? "0" : spc2.getBairro());
			SOAPElement numero = enderecoPessoa.addChildElement("numero");
			numero.addTextNode(spc2.getNumero() == null ? "0" : spc2.getNumero());
			spc2.setTipoOperacao("I");
		}
		return soapBody;

	}

	public SOAPBody adicionaSpcExclusao(SOAPBody soapBody,String myNamespace, Collection<Spc> spc) throws SOAPException {
		for (Spc spc2 : spc) {
			SOAPElement soapBodyElem = soapBody.addChildElement(myNamespace);
			SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("excluir");
			SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("tipo-pessoa");
			soapBodyElem2.addTextNode(spc2.getTipoPessoa());
			SOAPElement dadosPessoaFisica = soapBodyElem1.addChildElement("dados-pessoa-fisica");
			SOAPElement soapBodyElem31 = dadosPessoaFisica.addChildElement("cpf");
			soapBodyElem31.setAttribute("numero", spc2.getCpf());
			SOAPElement soapBodyElem5 = soapBodyElem1.addChildElement("data-vencimento");
			if ((new Date().getTime() - spc2.getDataVencimento().getTime()) / 86400000L <= 1800) {
				soapBodyElem5.addTextNode((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(spc2.getDataVencimento()))
						.replace(" ", "T"));
			} else {
				soapBodyElem5.addTextNode((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date(new Date().getYear(), new Date().getMonth() - 1, new Date().getDate())))
								.replace(" ", "T"));
			}
			SOAPElement soapBodyElem7 = soapBodyElem1.addChildElement("numero-contrato");
			soapBodyElem7.addTextNode(spc2.getNumeroContrato().toString());
			SOAPElement soapBodyElem9 = soapBodyElem1.addChildElement("motivo-exclusao");
			SOAPElement soapBodyElem91 = soapBodyElem9.addChildElement("id");
			soapBodyElem91.addTextNode(spc2.getIdExclusao().toString());
			spc2.setTipoOperacao("E");			
		}
		return soapBody;
	}
}
