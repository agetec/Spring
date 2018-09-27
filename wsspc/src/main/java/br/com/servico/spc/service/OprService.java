package br.com.servico.spc.service;

import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.servico.spc.model.Operador;
import br.com.servico.spc.repository.OprRepository;

@Service
public class OprService {

	@Autowired
	OprRepository oprRepository;

	public Operador salvar(Operador emhOpr) {
		return oprRepository.save(emhOpr);
	}

	public Collection<Operador> buscarTodos() {
		return oprRepository.findAll();
	}

	public Optional<Operador> buscaByID(Integer id) {
		return oprRepository.findById(id);
	}

	public void excluir(Operador emhOpr) {
		oprRepository.delete(emhOpr);
	}
}
