package com.estados.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.estados.br.model.Estados;

@Repository
public interface EstadosRepository extends JpaRepository<Estados, Integer> {

}
