package br.com.servico.spc.controler;

import javax.xml.soap.SOAPMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.servico.spc.model.Operador;
import br.com.servico.spc.model.Spc;
import br.com.servico.spc.service.SpcService;

@RestController
public class SpcControler {
	@Autowired
	SpcService spcService = new SpcService();

	@RequestMapping(method = RequestMethod.POST, value = "/incluirSpc", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SOAPMessage> incluir(@RequestBody Operador opr) {
		if (opr != null) {
			SOAPMessage message = new SoapSpcControler().callSoapWebServiceInclusao(opr.getSpcs(), opr);
			for (Spc spc2 : opr.getSpcs()) {
				spcService.salvar(spc2);
			}
			return new ResponseEntity<SOAPMessage>(message, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/excluirSpc", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SOAPMessage> excluir(@RequestBody Operador opr) {
		if (opr != null) {
			SOAPMessage message = new SoapSpcControler().callSoapWebServiceExclusao(opr.getSpcs(), opr);
			for (Spc spc2 : opr.getSpcs()) {
				spcService.salvar(spc2);
			}
			return new ResponseEntity<SOAPMessage>(message, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
