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
import com.bebidas.br.model.Estoque;
import com.bebidas.br.service.EstoqueService;

import io.swagger.annotations.Api;

@RestController
@Api(value = "onlinestore", description = "Operações para controle de estoque da sessão de bebidas")
@CrossOrigin(origins = "*")
public class EstoqueControler {
	@Autowired
	EstoqueService service = new EstoqueService();
	
	@RequestMapping(method = RequestMethod.GET, value = "/buscarTodosEstoque", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Estoque>> buscarTodos() {
		try {
			Collection<Estoque> estoques = service.buscarTodos();
			return new ResponseEntity<Collection<Estoque>>(estoques, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		 
	}

	@RequestMapping(method = RequestMethod.GET, value = "/buscarPorIdEstoque", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Estoque>> buscarPorId(Integer Id) {
		return null;
	}

}
