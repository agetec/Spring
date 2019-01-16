package com.estados.br.controler;

import com.estados.br.service.EstadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.estados.br.model.Estados;

import java.util.Collection;
import java.util.Optional;

@RestController
@CrossOrigin(origins ="*")
public class EstadosControler {
    @Autowired
    EstadosService estadosService;

	@RequestMapping(method = RequestMethod.POST, value = "/salvar", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity salvar(@RequestBody Estados estado) {
        try {
            estadosService.salvarEstado(estado);
            return new ResponseEntity<>("Estado cadastrado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao tentar cadastrar estado!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/excluir/{id}")
	public ResponseEntity excluir(@PathVariable Integer id) {
		try {
			Optional<Estados> estados=estadosService.buscaByID(id);
			estadosService.excluir(estados.get());
			return new ResponseEntity<>("Estado excluído com sucesso!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Erro ao tentar excluir estado!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/buscarTodos", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Estados>> buscarTodos() {
		Collection<Estados> estadosCollecrtion=estadosService.buscarTodos();
		return  new ResponseEntity<Collection<Estados>> (estadosCollecrtion, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/buscarPorId",
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Estados> buscarPorId(Integer Id) {
		return null;		
	}

}
