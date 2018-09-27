package br.com.servico.spc.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.servico.spc.model.EmhOpr;
import br.com.servico.spc.repository.OprRepository;

@Service
public class OprService {

	@Autowired
	OprRepository  oprRepository;

	public EmhOpr salvar(EmhOpr emhOpr) {		
		return oprRepository.save(emhOpr);			
	}
	public Collection<EmhOpr> buscarTodos(){
		return oprRepository.findAll(); 
	}
	public Optional<EmhOpr> buscaByID(Integer id) {
		return oprRepository.findById(id);
	}
	public void excluir(EmhOpr emhOpr) {
		oprRepository.delete(emhOpr);
	}
}
