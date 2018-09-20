package br.com.fabricadeprogramador.ws.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeControler {
	
	@RequestMapping("/home")
	public String irParaHome() {
		return "index";
	}
	

}
