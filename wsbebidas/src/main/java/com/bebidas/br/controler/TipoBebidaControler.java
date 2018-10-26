package com.bebidas.br.controler;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bebidas.br.model.TipoBebida;
import com.bebidas.br.service.TipoBebidaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "onlinestore", description = "Operações para controle de tipos de bebidas")
@CrossOrigin(origins = "*")
public class TipoBebidaControler {
	@Autowired
	TipoBebidaService service ;

	@ApiOperation(value = "salvar tipo de bebida", response = TipoBebida.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })

	@RequestMapping(method = RequestMethod.POST, value = "/salvarTpBebida", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity salvar(@RequestBody TipoBebida tipoBebida) {
		try {
			service.salvar(tipoBebida);
			return new ResponseEntity<TipoBebida>(tipoBebida, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "buscar todos os tipos de bebida", response = Collection.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })

	@RequestMapping(method = RequestMethod.GET, value = "/buscarTodosTp", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity buscarTodos() {
		try {
			Collection<TipoBebida> tipoBebidas = service.buscarTodos();
			return new ResponseEntity<Collection<TipoBebida>>(tipoBebidas, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "buscar tipos de bebida por tp", response = TipoBebida.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })

	@RequestMapping(method = RequestMethod.GET, value = "/buscarTipoTp", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity buscarTipoTp(@RequestBody String tipo) {
		try {
			TipoBebida tipoBebida = service.buscarTipoBebdidaByTipo(tipo);
			return new ResponseEntity<TipoBebida>(tipoBebida, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}

	}

}
