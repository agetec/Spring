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
@Api(value = "onlinestore", description = "Operações para controle da inadimplencia no spc")
public class SpcControler {
	@Autowired
	SpcService spcService = new SpcService();

	@ApiOperation(value = "Incluir inadimplênte no SPC", response = SOAPMessage.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição"),
			@ApiResponse(code = 404, message = "O recurso que você estava tentando acessar não foi encontrado") })
	@RequestMapping(method = RequestMethod.POST, value = "/incluirSpc", consumes = MediaType.APPLICATION_JSON_VALUE)
	
	public ResponseEntity<SOAPMessage> incluir(@RequestBody Operador opr) {
		if (opr != null && opr.getSpcs() != null) {
			SOAPMessage message = new SoapSpcControler().callSoapWebServiceInclusao(opr.getSpcs(), opr);
			for (Spc spc2 : opr.getSpcs()) {
				spcService.salvar(spc2);
			}
			return new ResponseEntity<SOAPMessage>(message, HttpStatus.CREATED);
		} else
			return new ResponseEntity<SOAPMessage>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Excluir inadimplênte do SPC", response = SOAPMessage.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição"),
			@ApiResponse(code = 404, message = "O recurso que você estava tentando acessar não foi encontrado") })
	@RequestMapping(method = RequestMethod.POST, value = "/excluirSpc", consumes = MediaType.APPLICATION_JSON_VALUE)
	
	public ResponseEntity<SOAPMessage> excluir(@RequestBody Operador opr) {
		if (opr != null && opr.getSpcs() != null) {
			SOAPMessage message = new SoapSpcControler().callSoapWebServiceExclusao(opr.getSpcs(), opr);
			for (Spc spc2 : opr.getSpcs()) {
				spcService.salvar(spc2);
			}
			return new ResponseEntity<SOAPMessage>(message, HttpStatus.CREATED);
		} else
			return new ResponseEntity<SOAPMessage>(HttpStatus.NOT_FOUND);
	}

}
