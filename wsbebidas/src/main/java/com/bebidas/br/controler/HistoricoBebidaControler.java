package com.bebidas.br.controler;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bebidas.br.model.HistoricoBebida;
import com.bebidas.br.service.HistoricoBebidaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "onlinestore", description = "Operações para controle de historico de bebidas do estoque")
@CrossOrigin(origins = "*")
public class HistoricoBebidaControler {
	@Autowired
	HistoricoBebidaService service = new HistoricoBebidaService();

	@ApiOperation(value = "busca histórico por sessão e tipo", response = Collection.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })
	
	@RequestMapping(method = RequestMethod.GET, value = "/buscarByTipoSessao", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity buscarByTipoSessao(@RequestParam(name="idSessao") Integer idSessao, @RequestParam(name="tipo") 
	Integer tipo,String ordenacao) {
		try {
			Collection<HistoricoBebida> historicoBebidas = service.buscarByTipoSessao(idSessao,tipo,ordenacao);
			return new ResponseEntity<Collection<HistoricoBebida>>(historicoBebidas, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}

	}

	
}
