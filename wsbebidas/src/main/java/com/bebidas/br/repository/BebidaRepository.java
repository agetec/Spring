package com.bebidas.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bebidas.br.model.Bebida;

public interface BebidaRepository extends JpaRepository<Bebida, Long>{

	@Query(value="select b from #{#entityName} b where b.tipoBebida.idTipoBebida=:tipo")
	Bebida findTipo(@Param("tipo") Integer tipo);
	
	@Query(value="select b from #{#entityName} b where b.nome=:nome")
	Bebida findNome(@Param("nome") String nome);
}
