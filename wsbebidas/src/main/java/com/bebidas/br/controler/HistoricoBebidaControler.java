package com.bebidas.br.controler;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bebidas.br.model.HistoricoBebida;

import io.swagger.annotations.Api;

@RestController
@Api(value = "onlinestore", description = "Operações para controle de historico de bebidas do estoque")
@CrossOrigin(origins="*")
public class HistoricoBebidaControler {

	@RequestMapping(method = RequestMethod.POST, value = "/salvarHisBebida",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HistoricoBebida> salvar(@RequestBody HistoricoBebida historicoBebida) {
		return new ResponseEntity<HistoricoBebida>(historicoBebida, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/excluirHisBebida", 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void excluir(@RequestBody HistoricoBebida historicoBebida) {

	}

	@RequestMapping(method = RequestMethod.GET, value = "/buscarTodosHis", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<HistoricoBebida>> buscarTodos() {
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/buscarPorIdHis", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<HistoricoBebida>> buscarPorId(Integer Id) {
		return null;
	}
}