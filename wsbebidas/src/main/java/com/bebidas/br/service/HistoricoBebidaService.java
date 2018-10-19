package com.bebidas.br.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bebidas.br.model.HistoricoBebida;
import com.bebidas.br.repository.HistoricoBebidaRepository;

@Service
public class HistoricoBebidaService {

	@Autowired
	HistoricoBebidaRepository historicoBebidaRepository;

	public HistoricoBebida salvarEstado(HistoricoBebida historicoBebida) {
		return historicoBebidaRepository.save(historicoBebida);
	}

	public Collection<HistoricoBebida> buscarTodos() {
		return historicoBebidaRepository.findAll();
	}

	public Optional<HistoricoBebida> buscaByID(Long id) {
		return historicoBebidaRepository.findById(id);
	}

	public void excluir(HistoricoBebida historicoBebida) {
		historicoBebidaRepository.delete(historicoBebida);
	}
}
