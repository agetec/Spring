package com.bebidas.br.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bebidas.br.model.TipoBebida;
import com.bebidas.br.repository.TipoBebidaRepository;

@Service
public class TipoBebidaService {

	@Autowired
	TipoBebidaRepository tipoBebidaRepository;

	public TipoBebida salvar(TipoBebida tipoBebida) {
		return tipoBebidaRepository.save(tipoBebida);
	}

	public Collection<TipoBebida> buscarTodos() {
		return tipoBebidaRepository.findAll();
	}

	public Optional<TipoBebida> buscaByID(Integer id) {
		return tipoBebidaRepository.findById(id);
	}

	public void excluir(TipoBebida tipoBebida) {
		tipoBebidaRepository.delete(tipoBebida);
	}
	public TipoBebida buscarTipoBebdidaByTipo(String tipo) {
		return tipoBebidaRepository.findAll(tipo);	
	}
}
