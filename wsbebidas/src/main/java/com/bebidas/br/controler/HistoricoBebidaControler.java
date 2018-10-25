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

import com.bebidas.br.model.HistoricoBebida;
import com.bebidas.br.service.EstoqueService;
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

	@ApiOperation(value = "salvar histórico do estoque de bebidas", response = HistoricoBebida.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successo na requisição, com seguinte retorno"),
			@ApiResponse(code = 400, message = "servidor não conseguiu entender a requisição devido à sintaxe inválida") })

	@RequestMapping(method = RequestMethod.POST, value = "/salvarHisBebida", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity salvar(@RequestBody HistoricoBebida historicoBebida) {
		try {
			if (validaQtdEstoque(historicoBebida)) {
				if (historicoBebida.getTipoMovimento().equals("S"))
					historicoBebida.getEstoque().setQtdEstocar(historicoBebida.getEstoque().getQtdEstocar() * (-1));
				historicoBebida.getEstoque().setQtd(somaEstoque(historicoBebida));
				service.salvar(historicoBebida);
				return new ResponseEntity<HistoricoBebida>(historicoBebida, HttpStatus.CREATED);
			}
			return new ResponseEntity<>("qtd indisponível para entrada/saída", HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("servidor não entendeu a requisição", HttpStatus.BAD_REQUEST);
		}
	}

	public boolean validaQtdEstoque(HistoricoBebida historicoBebida) {
		if (historicoBebida.getTipoMovimento().equals("S")) {
			if (historicoBebida.getEstoque().getQtd() > historicoBebida.getEstoque().getQtdEstocar())
				return true;
		} else if (historicoBebida.getTipoMovimento().equals("E")) {
			if (somaEstocar(historicoBebida) < historicoBebida.getEstoque().getSessao().getCapacidade())
				return true;
		}
		return false;
	}

	public Integer somaEstocar(HistoricoBebida historicoBebida) {
		if (historicoBebida.getEstoque().getQtd() == null)
			historicoBebida.getEstoque().setQtd(0);
		return new EstoqueService().countQtqEstoque(historicoBebida.getEstoque().getSessao().getIdSessao())
				+ historicoBebida.getEstoque().getQtdEstocar();
	}

	public Integer somaEstoque(HistoricoBebida historicoBebida) {
		return historicoBebida.getEstoque().getQtd() + historicoBebida.getEstoque().getQtdEstocar();
	}

}
