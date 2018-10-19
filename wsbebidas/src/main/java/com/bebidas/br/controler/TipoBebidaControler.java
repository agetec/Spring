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

import com.bebidas.br.model.TipoBebida;
import com.bebidas.br.service.TipoBebidaService;

import io.swagger.annotations.Api;

@RestController
@Api(value = "onlinestore", description = "Operações para controle de tipos de bebidas")
@CrossOrigin(origins = "*")
public class TipoBebidaControler {
	@Autowired
	TipoBebidaService service = new TipoBebidaService();

	@RequestMapping(method = RequestMethod.POST, value = "/salvarTpBebida", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipoBebida> salvar(@RequestBody TipoBebida tipoBebida) {
		 try {
		service.salvar(tipoBebida);
		return new ResponseEntity<TipoBebida>(tipoBebida, HttpStatus.CREATED);
		 } catch (Exception e) {
	            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
	        }
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/excluirTpBebida", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void excluir(@RequestBody TipoBebida tipoBebida) {

	}

	@RequestMapping(method = RequestMethod.GET, value = "/buscarTodosTp", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TipoBebida>> buscarTodos() {
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/buscarPorIdTp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TipoBebida>> buscarPorId(Integer Id) {
		return null;
	}

}
