package com.bebidas.br.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bebidas.br.model.Bebida;
import com.bebidas.br.repository.BebidaRepository;

@Service
public class BebidaService {

	@Autowired
	BebidaRepository bebidaRepository;

	public Bebida salvarEstado(Bebida bebida) {
		return bebidaRepository.save(bebida);
	}

	public Collection<Bebida> buscarTodos() {
		return bebidaRepository.findAll();
	}

	public Optional<Bebida> buscaByID(Long id) {
		return bebidaRepository.findById(id);
	}

	public void excluir(Bebida bebida) {
		bebidaRepository.delete(bebida);
	}
}
