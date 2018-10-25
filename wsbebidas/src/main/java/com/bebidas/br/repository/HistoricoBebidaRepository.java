package com.bebidas.br.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bebidas.br.model.HistoricoBebida;

public interface HistoricoBebidaRepository extends JpaRepository<HistoricoBebida, Long> {

	@Query("SELECT  h FROM  HistoricoBebida  h " + "INNER JOIN Estoque e on e.idEstoque=h.estoque.idEstoque"
			+ "INNER JOIN Bebida b on b.idBebida=e.bebida.idBebida" + "where e.sessao.idSessao=:sessao and"
			+ "b.tipoBebida.idTipoBebida = :tipo")
	Collection<HistoricoBebida> buscarByTipoSessao(@Param("sessao") Integer sessao, @Param("tipo") Integer tipo);
}
