package com.estados.br.controler;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.estados.br.model.Estados;

@RestController
public class EstadosControler {

	@RequestMapping(method = RequestMethod.POST, value = "/salvar", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Estados> salvar(@RequestBody Estados estado) {
		return null;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/excluir", 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void excluir(@RequestBody Estados estado) {
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/buscarTodos", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Estados> buscarTodos() {
		return null;		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/buscarPorId",
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Estados> buscarPorId(Integer Id) {
		return null;		
	}

}
