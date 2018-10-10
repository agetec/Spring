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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "onlinestore", description = "Operações para controle da inadimplência do SPC")
public class SpcControler {
	@Autowired
	SpcService spcService = new SpcService();

	@ApiOperation(value = "Incluir inadimplênte no SPC", response = SOAPMessage.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 404, message = "O recurso que você estava tentando acessar não foi encontrado") })
	@RequestMapping(method = RequestMethod.POST, value = "/incluirSpc", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SOAPMessage> incluir(@RequestBody Operador operador) {
		SOAPMessage message = null;		
		if (operador != null && operador.getSpcs() != null) {
			for (Spc spc2 : operador.getSpcs()) {
				message = new SoapSpcControler().callSoapWebServiceInclusao(spc2, operador);
				//spcService.salvar(spc2);
			}			
		} else 		
			return new ResponseEntity<SOAPMessage>(message, HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<SOAPMessage>(message, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Excluir inadimplênte do SPC", response = SOAPMessage.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 404, message = "O recurso que você estava tentando acessar não foi encontrado") })
	@RequestMapping(method = RequestMethod.POST, value = "/excluirSpc", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SOAPMessage> excluir(@RequestBody Operador operador) {
		SOAPMessage message = null;
		if (operador != null && operador.getSpcs() != null) {
			message = new SoapSpcControler().callSoapWebServiceExclusao(operador.getSpcs(), operador);
			if (message.getSOAPPart().getChildNodes().getLength() > 0) {
				for (Spc spc2 : operador.getSpcs()) {
					spcService.salvar(spc2);
				}
			} else
				return new ResponseEntity<SOAPMessage>(message, HttpStatus.NOT_FOUND);
			return new ResponseEntity<SOAPMessage>(message, HttpStatus.CREATED);
		} else
			return new ResponseEntity<SOAPMessage>(message, HttpStatus.NOT_FOUND);
	}

}
