package br.com.servico.spc.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.servico.spc.model.EmhOpr;
import br.com.servico.spc.model.EmhSpc;
import br.com.servico.spc.service.SpcService;

@RestController
public class SpcControler {
	@Autowired
	SpcService spcService = new SpcService();

	@RequestMapping(method = RequestMethod.POST, value = "/incluirSpc", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmhSpc> incluir(@RequestBody EmhSpc EmhSpc, @RequestBody EmhOpr emhOpr) {
		EmhSpc spcCadastrado = spcService.salvar(EmhSpc);
		return new ResponseEntity<EmhSpc>(spcCadastrado, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/excluirSpc", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmhSpc> excluir(@RequestBody EmhSpc EmhSpc, @RequestBody EmhOpr emhOpr) {
		EmhSpc spcCadastrado = spcService.salvar(EmhSpc);
		return new ResponseEntity<EmhSpc>(spcCadastrado, HttpStatus.CREATED);
	}
}
