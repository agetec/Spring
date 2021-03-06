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

import com.bebidas.br.model.Bebida;
import com.bebidas.br.service.BebidaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "onlinestore", description = "Operações para controle de bebidas ")
@CrossOrigin(origins = "*")
public class BebidaControler {

	@Autowired
	BebidaService bebidaService ;

	@ApiOperation(value = "salvar bebida", response = Bebida.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })

	@RequestMapping(method = RequestMethod.POST, value = "/salvarBebida", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity salvar(@RequestBody Bebida bebida) {
		try {
			bebidaService.salvar(bebida);
			return new ResponseEntity<Bebida>(bebida, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Listar todas bebidas", response = Collection.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })

	@RequestMapping(method = RequestMethod.GET, value = "/buscarTodos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity buscarTodos() {
		try {
			Collection<Bebida> bebidas = bebidaService.buscarTodos();
			return new ResponseEntity<Collection<Bebida>>(bebidas, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Listar bebidas por nome", response = Bebida.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })
	
	@RequestMapping(method = RequestMethod.GET, value = "/buscarBebidaByNome",
			produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity buscarBebidaByNome(@RequestBody String nome) {
		try {
			Bebida bebida = bebidaService.findNome(nome);
			return new ResponseEntity<Bebida>(bebida, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}
		 
	}
	

}
