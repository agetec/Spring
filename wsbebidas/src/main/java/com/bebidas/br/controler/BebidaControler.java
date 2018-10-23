package com.bebidas.br.controler;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bebidas.br.model.Bebida;
import com.bebidas.br.model.Estoque;
import com.bebidas.br.service.BebidaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "onlinestore", description = "Operações para controle de bebidas do estoque")
@CrossOrigin(origins = "*")
public class BebidaControler {

	@Autowired
	BebidaService bebidaService = new BebidaService();

	@ApiOperation(value = "salvar bebida", response = Bebida.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 404, message = "O recurso que você estava tentando acessar não foi encontrado") })

	@RequestMapping(method = RequestMethod.POST, value = "/salvarBebida", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bebida> salvar(@RequestBody Bebida bebida) {
		try {
			bebidaService.salvar(bebida);
			return new ResponseEntity<Bebida>(bebida, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "excluir bebida")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 404, message = "O recurso que você estava tentando acessar não foi encontrado") })

	@RequestMapping(method = RequestMethod.DELETE, value = "/excluirBebida", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> excluir(@RequestBody Bebida bebida) {
		try {
			bebidaService.excluir(bebida);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Listar todas bebidas", response = Collection.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 404, message = "O recurso que você estava tentando acessar não foi encontrado") })

	@RequestMapping(method = RequestMethod.GET, value = "/buscarTodos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Bebida>> buscarTodos() {
		try {
			Collection<Bebida> bebidas = bebidaService.buscarTodos();
			return new ResponseEntity<Collection<Bebida>>(bebidas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Listar bebida por id", response = Bebida.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 404, message = "O recurso que você estava tentando acessar não foi encontrado") })

	@RequestMapping(method = RequestMethod.GET, value = "/buscarPorId", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bebida> buscarPorId(@PathVariable Long id) {
		try {
			Optional<Bebida> bebida = bebidaService.buscaByID(id);
			return new ResponseEntity<Bebida>(bebida.get(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Bebida>(HttpStatus.BAD_REQUEST);
		}
	}

}
