package br.com.fabricadeprogramador.ws.controler;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabricadeprogramador.ws.model.Cliente;
import br.com.fabricadeprogramador.ws.service.ClienteService;

@RestController
public class ClienteControler {
	
	@Autowired
	ClienteService clienteService=new ClienteService();
	
	@RequestMapping(
			method=RequestMethod.POST, 
			value="/clientes",
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
		Cliente clienteCadastrado= clienteService.salvar(cliente);
		return new ResponseEntity<Cliente>(clienteCadastrado,HttpStatus.CREATED);
	}
	
	@RequestMapping(
			method=RequestMethod.GET, 
			value="/clientes",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Cliente>> buscarTodosClientes() {
		Collection<Cliente> clienteCadastrados= clienteService.buscarTodos();
		return new ResponseEntity<Collection<Cliente>>(clienteCadastrados,HttpStatus.CREATED);
	}
	@RequestMapping(
			method=RequestMethod.DELETE, 
			value="/clientes/{id}")
	public ResponseEntity<Cliente> excluirCliente(@PathVariable Integer id) {
		Optional<Cliente> clienteEncontrato= clienteService.buscaByID(id);
		if(clienteEncontrato==null)  return new ResponseEntity<>(HttpStatus.NOT_FOUND);		
		else {
			clienteService.excluir(clienteEncontrato.get());
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
	}
	@RequestMapping(
			method=RequestMethod.PUT, 
			value="/clientes",
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cliente> alterarCliente(@RequestBody Cliente cliente) {
		Cliente clienteAlterado= clienteService.salvar(cliente);
		return new ResponseEntity<Cliente>(clienteAlterado,HttpStatus.CREATED);
	}
	


}
