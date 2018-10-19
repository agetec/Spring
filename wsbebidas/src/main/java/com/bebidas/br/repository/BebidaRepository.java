package com.bebidas.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bebidas.br.model.Bebida;

public interface BebidaRepository extends JpaRepository<Bebida, Long>{

}
