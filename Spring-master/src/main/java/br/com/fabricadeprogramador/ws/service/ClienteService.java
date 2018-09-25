package br.com.fabricadeprogramador.ws.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabricadeprogramador.ws.model.Cliente;
import br.com.fabricadeprogramador.ws.repository.ClienteRepository;
@Service
public class ClienteService {
	@Autowired
	ClienteRepository  cleinteRepository;

	public Cliente salvar(Cliente cliente) {		
		return cleinteRepository.save(cliente);			
	}
	public Collection<Cliente> buscarTodos(){
		return cleinteRepository.findAll();
	}
	public Optional<Cliente> buscaByID(Integer id) {
		return cleinteRepository.findById(id);
	}
	public void excluir(Cliente cliente) {
		cleinteRepository.delete(cliente);
	}
	
}
