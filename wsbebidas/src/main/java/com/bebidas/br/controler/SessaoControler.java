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

import com.bebidas.br.model.Bebida;
import com.bebidas.br.model.Sessao;
import com.bebidas.br.service.SessaoService;

import io.swagger.annotations.Api;

@RestController
@Api(value = "onlinestore", description = "Operações para controle de sessão de bebidas do estoque")
@CrossOrigin(origins = "*")
public class SessaoControler {

	SessaoService service = new SessaoService();

	@RequestMapping(method = RequestMethod.POST, value = "/salvarSessao", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sessao> salvar(@RequestBody Sessao sessao) {
		try {
			service.salvar(sessao);
			return new ResponseEntity<Sessao>(sessao, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/excluirSessao", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void excluir(@RequestBody Sessao sessao) {

	}

	@RequestMapping(method = RequestMethod.GET, value = "/buscarTodosSess", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Sessao>> buscarTodos() {
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/buscarPorIdSess", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Sessao>> buscarPorId(Integer Id) {
		return null;
	}

}
