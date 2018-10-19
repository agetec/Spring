package com.bebidas.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bebidas.br.model.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long>{

}
