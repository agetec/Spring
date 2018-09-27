package br.com.servico.spc.service;

import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.servico.spc.model.EmhSpc;
import br.com.servico.spc.repository.SpcRepository;

@Service
public class SpcService {

	@Autowired
	SpcRepository  spcRepository;

	public EmhSpc salvar(EmhSpc emhSpc) {		
		return spcRepository.save(emhSpc);			
	}
	public Collection<EmhSpc> buscarTodos(){
		return spcRepository.findAll();
	}
	public Optional<EmhSpc> buscaByID(Long id) {
		return spcRepository.findById(id);
	}
	public void excluir(EmhSpc emhSpc) {
		spcRepository.delete(emhSpc);
	}
}
