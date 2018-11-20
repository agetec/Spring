package br.com.servico.spc.controler;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.servico.spc.model.EnvelopeExcluir;
import br.com.servico.spc.model.EnvelopeFault;
import br.com.servico.spc.model.EnvelopeIncluir;
import br.com.servico.spc.model.Operador;
import br.com.servico.spc.model.Spc;
import br.com.servico.spc.service.SpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "onlinestore", description = "Operações para controle da inadimplência do SPC!")
@CrossOrigin(origins = "*")
public class SpcControler {
	@Autowired
	SpcService spcService = new SpcService();

	@ApiOperation(value = "Incluir inadimplênte no SPC", response = EnvelopeFault.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })
	@RequestMapping(method = RequestMethod.POST, value = "/incluirSpc", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity incluir(@RequestBody Operador operador) {
		JSONObject message = null;
		if (operador != null && operador.getSpcs() != null) {
			message = new SoapSpcControler().callSoapWebServiceInclusao(operador.getSpcs(), operador);
			if (message != null) {
				for (Spc spc2 : operador.getSpcs()) {
					spcService.salvar(spc2);
				}
			} else {
				return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity(message, HttpStatus.CREATED);
		}
		return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Excluir inadimplênte do SPC", response = JSONObject.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })
	@RequestMapping(method = RequestMethod.POST, value = "/excluirSpc", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity excluir(@RequestBody Operador operador) {
		JSONObject message = null;
		if (operador != null && operador.getSpcs() != null) {
			message = new SoapSpcControler().callSoapWebServiceExclusao(operador.getSpcs(), operador);
			if (message != null) {
				for (Spc spc2 : operador.getSpcs()) {
					spcService.salvar(spc2);
				}
			} else {
				return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity(message, HttpStatus.CREATED);
		}
		return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
	}

}
