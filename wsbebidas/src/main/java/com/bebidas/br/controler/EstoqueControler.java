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

import com.bebidas.br.model.Estoque;
import com.bebidas.br.model.HistoricoBebida;
import com.bebidas.br.service.EstoqueService;
import com.bebidas.br.service.HistoricoBebidaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "onlinestore", description = "Operações para controle de estoque da sessão de bebidas")
@CrossOrigin(origins = "*")
public class EstoqueControler {
	@Autowired
	EstoqueService service = new EstoqueService();

	@Autowired
	HistoricoBebidaService hisService = new HistoricoBebidaService();

	@ApiOperation(value = "salvar  estoque de bebidas e seu historico", response = HistoricoBebida.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida"),
			@ApiResponse(code = 403, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })

	@RequestMapping(method = RequestMethod.POST, value = "/salvarEstoque", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity salvar(@RequestBody HistoricoBebida historicoBebida) {
		try {
			if (validaTipoBebida(historicoBebida.getEstoque())) {
				if (validaQtdEstoque(historicoBebida)) {
					if (historicoBebida.getTipoMovimento().equals("S"))
						historicoBebida.getEstoque().setQtdEstocar(historicoBebida.getEstoque().getQtdEstocar() * (-1));
					historicoBebida.getEstoque().setQtd(somaEstoque(historicoBebida.getEstoque()));
					service.salvar(historicoBebida.getEstoque());
					hisService.salvar(historicoBebida);

					return new ResponseEntity<Estoque>(historicoBebida.getEstoque(), HttpStatus.CREATED);
				}
				return new ResponseEntity<>("qtd indisponível para entrada/saída", HttpStatus.OK);
			}
			return new ResponseEntity<>("tipo da bebida não é o mesmo tipo de bebida da sessão", HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}
	}

	public boolean validaTipoBebida(Estoque estoque) {
		if (estoque.getSessao().getTipoBebida().getIdTipoBebida()
				.equals(estoque.getBebida().getTipoBebida().getIdTipoBebida()))
			return true;
		return false;
	}

	public boolean validaQtdEstoque(HistoricoBebida historicoBebida) {
		if (historicoBebida.getTipoMovimento().equals("S")) {
			if (historicoBebida.getEstoque().getQtd() > historicoBebida.getEstoque().getQtdEstocar())
				return true;
		} else if (historicoBebida.getTipoMovimento().equals("E")) {
			if (somaEstocar(historicoBebida.getEstoque()) < historicoBebida.getEstoque().getSessao().getCapacidade())
				return true;
		}
		return false;
	}

	public Integer somaEstocar(Estoque estoque) {
		if (estoque.getQtd() == null)
			estoque.setQtd(0);
		return service.countQtqEstoque(estoque.getSessao().getIdSessao()) + estoque.getQtdEstocar();
	}

	public Integer somaEstoque(Estoque estoque) {
		return estoque.getQtd() + estoque.getQtdEstocar();
	}

	@ApiOperation(value = "busca todas as bebidas no estoque", response = Estoque.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })

	@RequestMapping(method = RequestMethod.GET, value = "/buscarTodosEstoque", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity buscarTodos() {
		try {
			Collection<Estoque> estoques = service.buscarTodos();
			return new ResponseEntity<Collection<Estoque>>(estoques, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "busca todas as bebidas no estoque por tipo", response = Collection.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })

	@RequestMapping(method = RequestMethod.GET, value = "/buscarTodosEstoqueByTipo", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity buscarTodosEstoqueByTipo(@RequestBody Integer tipo) {
		try {
			Collection<Estoque> estoques = service.buscarTodosEstoqueByTipo(tipo);
			return new ResponseEntity<Collection<Estoque>>(estoques, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "busca todas as bebidas no estoque por sessão", response = Collection.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })

	@RequestMapping(method = RequestMethod.GET, value = "/buscarTodosEstoqueBySessao", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity buscarTodosEstoqueBySessao(@RequestBody Integer sessao) {
		try {
			Collection<Estoque> estoques = service.buscarTodosEstoqueBySessao(sessao);
			return new ResponseEntity<Collection<Estoque>>(estoques, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "buscar estoque por bebida e sessão", response = Estoque.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })

	@RequestMapping(method = RequestMethod.GET, value = "/buscaEstoqueBebida", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity buscaEstoqueBebida(@RequestBody Estoque estoque) {
		try {
			Estoque estoqueResult = service.buscaEstoqueBebida(estoque.getBebida().getIdBebida(),
					estoque.getSessao().getIdSessao());
			return new ResponseEntity<Estoque>(estoqueResult, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}

	}

}
