package com.bebidas.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bebidas.br.model.HistoricoBebida;

public interface HistoricoBebidaRepository extends JpaRepository<HistoricoBebida,
Long> {

}
