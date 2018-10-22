package com.bebidas.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bebidas.br.model.TipoBebida;

public interface TipoBebidaRepository extends JpaRepository<TipoBebida, Integer> {
	
	@Query(value="select tp from #{#entityName} tp where tp.tipo=:tipo")
	TipoBebida findTipo(@Param("tipo") String tipo);
	
}
