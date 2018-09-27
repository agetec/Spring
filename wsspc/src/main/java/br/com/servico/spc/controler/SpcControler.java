package br.com.servico.spc.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.servico.spc.model.Operador;
import br.com.servico.spc.model.Spc;
import br.com.servico.spc.service.SpcService;

@RestController
public class SpcControler {
	@Autowired
	SpcService spcService = new SpcService();

	@RequestMapping(method = RequestMethod.POST, value = "/incluirSpc", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Spc> incluir(@RequestBody Spc spc, @RequestBody Operador emhOpr) {
		Spc spcCadastrado = spcService.salvar(spc);
		return new ResponseEntity<Spc>(spcCadastrado, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/excluirSpc", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Spc> excluir(@RequestBody Spc spc, @RequestBody Operador emhOpr) {
		Spc spcCadastrado = spcService.salvar(spc);
		return new ResponseEntity<Spc>(spcCadastrado, HttpStatus.CREATED);
	}
}
