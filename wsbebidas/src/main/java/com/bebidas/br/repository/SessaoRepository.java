package com.bebidas.br.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bebidas.br.model.Sessao;

public interface SessaoRepository extends JpaRepository<Sessao, Integer>{

	@Query(value="select s from #{#entityName} s where s.tipoBebida.idTipoBebida=:tipo")
	Collection<Sessao> findTipo(@Param("tipo") Integer tipo);
}
