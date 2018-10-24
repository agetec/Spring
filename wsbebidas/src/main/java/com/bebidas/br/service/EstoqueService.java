package com.bebidas.br.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bebidas.br.model.Estoque;
import com.bebidas.br.repository.EstoqueRepository;

@Service
public class EstoqueService {

	@Autowired
	EstoqueRepository estoqueRepository;

	public Estoque salvar(Estoque estoque) {
		return estoqueRepository.save(estoque);
	}
	
	public Integer countQtqEstoque(Integer sessao) {
		return estoqueRepository.countQtqEstoque(sessao);
	}
	
	public Collection<Estoque> buscarTodos() {
		return estoqueRepository.findAll();
	}

	public Optional<Estoque> buscaByID(Long id) {
		return estoqueRepository.findById(id);
	}

	public void excluir(Estoque estoque) {
		estoqueRepository.delete(estoque);
	}

	public Collection<Estoque> buscarTodosEstoqueByTipo(Integer tipo) {
		return estoqueRepository.findTipo(tipo);
	}

	public Collection<Estoque> buscarTodosEstoqueBySessao(Integer sessao) {
		return estoqueRepository.findSessao(sessao); 
	}
	
	public Estoque buscaEstoqueBebida(Long idBebida,Integer idSessao) {
		return estoqueRepository.buscaEstoqueBebida(idBebida,idSessao);
	}
}
