package com.estados.br.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Collection<Estados> buscarByNome(String nome) {
        return estadosRepository.findNome(nome);
    }

    public Optional<Estados> buscaByID(Integer id) {
        return estadosRepository.findById(id);
    }

    public void excluir(Estados estados) {
        estadosRepository.delete(estados);
    }

    public Page<Estados> buscarTodos(Example<Estados> est, PageableEst pageable) {
        return estadosRepository.findAll(est, pageable);
    }
}
