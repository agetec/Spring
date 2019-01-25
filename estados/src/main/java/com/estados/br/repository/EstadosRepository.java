package com.estados.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.estados.br.model.Estados;

import java.util.List;

@Repository
public interface EstadosRepository extends JpaRepository<Estados, Integer> {

    @Query(value = "select e from #{#entityName} e where upper(e.nome) like %:nome%")
    List<Estados> findNome(@Param("nome") String nome);
}
