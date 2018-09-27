package br.com.servico.spc.controler;
import java.util.Collection;
import javax.xml.soap.SOAPMessage;
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
	public ResponseEntity<Spc> incluir(@RequestBody Collection<Spc> spc, @RequestBody Operador opr) {	
		SOAPMessage message=new SoapSpcControler().callSoapWebServiceInclusao(spc, opr);
		for (Spc spc2 : spc) {
			spcService.salvar(spc2);
		}		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/excluirSpc", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Spc> excluir(@RequestBody Collection<Spc> spc, @RequestBody Operador opr) {		
		SOAPMessage message=new SoapSpcControler().callSoapWebServiceExclusao(spc, opr);
		for (Spc spc2 : spc) {
			spcService.salvar(spc2);
		}		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
