package com.bebidas.br.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bebidas.br.model.Sessao;
import com.bebidas.br.repository.SessaoRepository;

@Service
public class SessaoService {

	@Autowired
	SessaoRepository sessaoRepository;

	public Sessao salvar(Sessao sessao) {
		return sessaoRepository.save(sessao);
	}

	public Collection<Sessao> buscarTodos() {
		return sessaoRepository.findAll();
	}

	public Optional<Sessao> buscaByID(Integer id) {
		return sessaoRepository.findById(id);
	}
	
	public Collection<Sessao> findTipo(Integer tipo) {
		return sessaoRepository.findTipo(tipo);
	}
	

	public void excluir(Sessao sessao) {
		sessaoRepository.delete(sessao);
	}
}
