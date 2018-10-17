package com.estados.br.service;

import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.estados.br.model.Estados;
import com.estados.br.repository.EstadosRepository;

@Service
public class EstadosService {

	@Autowired
	EstadosRepository estadosRepository;

	public Estados salvarEstado(Estados estados) {
		return estadosRepository.save(estados);
	}

	public Collection<Estados> buscarTodos() {
		return estadosRepository.findAll();
	}

	public Optional<Estados> buscaByID(Integer id) {
		return estadosRepository.findById(id);
	}

	public void excluir(Estados estados) {
		estadosRepository.delete(estados);
	}
}
