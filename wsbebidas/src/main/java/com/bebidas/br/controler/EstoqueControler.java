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

import com.bebidas.br.model.Sessao;

import io.swagger.annotations.Api;

@RestController
@Api(value = "onlinestore", description = "Operações para controle de estoque da sessão de bebidas")
@CrossOrigin(origins = "*")
public class EstoqueControler {

	@RequestMapping(method = RequestMethod.POST, value = "/salvarEstoque", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sessao> salvar(@RequestBody Sessao sessao) {
		return new ResponseEntity<Sessao>(sessao, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/excluirEstoque", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void excluir(@RequestBody Sessao sessao) {

	}

	@RequestMapping(method = RequestMethod.GET, value = "/buscarTodosEstoque", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Sessao>> buscarTodos() {
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/buscarPorIdEstoque", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Sessao>> buscarPorId(Integer Id) {
		return null;
	}

}
