package br.com.servico.spc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.servico.spc.model.EmhOpr;

@Repository
public interface OprRepository extends JpaRepository<EmhOpr, Integer> {

}
