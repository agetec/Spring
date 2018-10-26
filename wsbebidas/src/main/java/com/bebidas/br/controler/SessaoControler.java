package com.bebidas.br.controler;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bebidas.br.model.Estoque;
import com.bebidas.br.model.Sessao;
import com.bebidas.br.service.EstoqueService;
import com.bebidas.br.service.SessaoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "onlinestore", description = "Operações para controle de sessão de bebidas do estoque")
@CrossOrigin(origins = "*")
public class SessaoControler {
	@Autowired
	SessaoService service;

	@Autowired
	EstoqueService estoqueService;

	@ApiOperation(value = "salvar sessão", response = Sessao.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })

	@RequestMapping(method = RequestMethod.POST, value = "/salvarSessao", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity salvar(@RequestBody Sessao sessao) {
		try {
			service.salvar(sessao);
			return new ResponseEntity<Sessao>(sessao, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "buscar todas as sessões", response = Collection.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })

	@RequestMapping(method = RequestMethod.GET, value = "/buscarTodosSess", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity buscarTodos() {
		try {
			Collection<Sessao> sessaos = service.buscarTodos();
			return new ResponseEntity<Collection<Sessao>>(sessaos, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Listar sessão por descrição", response = Sessao.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })

	@RequestMapping(method = RequestMethod.GET, value = "/buscarByDescricao", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity buscarByDescricao(@RequestBody String descricao) {
		try {
			String originalString = new String(descricao.getBytes("ISO-8859-1"), "UTF-8");
			Sessao sessao = service.findDescricao(originalString);
			return new ResponseEntity<Sessao>(sessao, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "buscar todas as sessões, para saber qual pode armazenar um volume de um determinado "
			+ "tipo de babida", response = Collection.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })

	@RequestMapping(method = RequestMethod.GET, value = "/buscarSessaoByArmazenar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity buscarSessaoByArmazenar(@RequestParam(name = "tipo") Integer tipo,
			@RequestParam(name = "qtdEstocar") Integer qtdEstocar) {
		try {
			Collection<Sessao> sessDisponiveis = new ArrayList<Sessao>();
			Collection<Sessao> sessao = service.findTipo(tipo);
			Collection<Estoque> estoques = estoqueService.buscarTodosEstoqueByTipo(tipo);
			sessDisponiveis = verificaCapacidade(estoques, qtdEstocar, sessao);
			return new ResponseEntity<Collection<Sessao>>(sessDisponiveis, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "buscar todas as sessões, para saber qual pode vender um volume de um determinado "
			+ "tipo de babida", response = Collection.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })

	@RequestMapping(method = RequestMethod.GET, value = "/buscarSessaoByVender", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity buscarSessaoByVender(@RequestParam(name = "tipo") Integer tipo,
			@RequestParam(name = "qtdVender") Integer qtdVender) {
		try {
			Collection<Sessao> sessDisponiveis = new ArrayList<Sessao>();
			Collection<Sessao> sessao = service.findTipo(tipo);
			Collection<Estoque> estoques = estoqueService.buscarTodosEstoqueByTipo(tipo);
			sessDisponiveis = verificaCapacidadeVender(estoques, qtdVender, sessao);
			return new ResponseEntity<Collection<Sessao>>(sessDisponiveis, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}
	}

	public Collection<Sessao> verificaCapacidade(Collection<Estoque> estoques, Integer qtdEstocar,
			Collection<Sessao> sessao) {
		Integer qtdEstoque;
		Collection<Sessao> sessDisponiveis = new ArrayList<Sessao>();
		for (Sessao sessao2 : sessao) {
			qtdEstoque = 0;
			for (Estoque estoque : estoques) {
				if (estoque.getSessao().getIdSessao().equals(sessao2.getIdSessao())) {
					qtdEstoque = qtdEstoque + estoque.getQtd();
				}
			}
			if ((qtdEstoque + qtdEstocar) <= sessao2.getCapacidade())
				sessDisponiveis.add(sessao2);
		}
		return sessDisponiveis;
	}

	public Collection<Sessao> verificaCapacidadeVender(Collection<Estoque> estoques, Integer qtdVender,
			Collection<Sessao> sessao) {
		Integer qtdEstoque;
		Collection<Sessao> sessDisponiveis = new ArrayList<Sessao>();
		for (Sessao sessao2 : sessao) {
			qtdEstoque = 0;
			for (Estoque estoque : estoques) {
				if (estoque.getSessao().getIdSessao().equals(sessao2.getIdSessao())) {
					qtdEstoque = qtdEstoque + estoque.getQtd();
				}
			}
			if (qtdEstoque >= qtdVender)
				sessDisponiveis.add(sessao2);
		}
		return sessDisponiveis;
	}

}
