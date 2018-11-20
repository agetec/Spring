package br.com.servico.spc.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.servico.spc.model.Spc;
import br.com.servico.spc.repository.SpcRepository;

@Service
public class SpcService {

	@Autowired
	SpcRepository  spcRepository;

	public Spc salvar(Spc spc) {		
		return spcRepository.save(spc);			
	}
	public Collection<Spc> buscarTodos(){
		return spcRepository.findAll();
	}
	public Optional<Spc> buscaByID(Long id) {
		return spcRepository.findById(id);
	}
	public void excluir(Spc spc) {
		spcRepository.delete(spc);
	}
}
