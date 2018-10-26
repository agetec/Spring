package com.bebidas.br.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bebidas.br.model.HistoricoBebida;

public interface HistoricoBebidaRepository extends JpaRepository<HistoricoBebida, Long> {

	@Query("SELECT  h FROM  HistoricoBebida h inner join Sessao s on s.idSessao=h.sessao.idSessao"
			+ " inner join TipoBebida t on t.idTipoBebida=h.tipo.idTipoBebida"
			+ " where t.idTipoBebida= :tipo and s.idSessao= :sessao"
			+ " order by"+" :ordenacao")
	Collection<HistoricoBebida> buscarByTipoSessao(@Param("sessao") Integer sessao, @Param("tipo") Integer tipo,
			@Param("ordenacao") String ordenacao);
}
