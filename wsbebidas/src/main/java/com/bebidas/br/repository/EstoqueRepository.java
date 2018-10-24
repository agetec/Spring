package com.bebidas.br.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bebidas.br.model.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long>{

	
	@Query("SELECT  e FROM  Estoque e INNER JOIN Bebida b ON e.bebida.idBebida = b.idBebida WHERE  b.tipoBebida.idTipoBebida ="
			+ " :tipo")
	Collection<Estoque> findTipo(@Param("tipo") Integer tipo);
	
	@Query("SELECT  e FROM  Estoque e INNER JOIN Sessao s ON e.sessao.idSessao = s.idSessao WHERE  s.idSessao = :sessao" )
	Collection<Estoque> findSessao(@Param("sessao") Integer sessao);
	
	
	@Query("SELECT  sum(e.qtd) FROM  Estoque e INNER JOIN Sessao s ON e.sessao.idSessao = s.idSessao WHERE  s.idSessao = :sessao" )
	Integer countQtqEstoque(@Param("sessao") Integer sessao);
	
	
}
