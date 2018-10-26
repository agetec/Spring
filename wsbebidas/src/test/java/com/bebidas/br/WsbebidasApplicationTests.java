package com.bebidas.br;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bebidas.br.model.Bebida;
import com.bebidas.br.model.Sessao;
import com.bebidas.br.model.TipoBebida;
import com.bebidas.br.service.SessaoService;
import com.bebidas.br.util.JsonDateSerializer;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WsbebidasApplicationTests {

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	SessaoService sService = new SessaoService();
	
	@Autowired
	private JsonDateSerializer json;
	
	WsTipoBebidaTests tipoBebidaTests=new WsTipoBebidaTests();
	
	WsBebidasTests bebidasTests=new WsBebidasTests();	
	 
	WsSessaoTests sessaoTests=new WsSessaoTests();	
	 
	WsEstoqueTests estoqueTests=new WsEstoqueTests();
	
	WsHistoricoBebidaTests historicoBebidaTests=new WsHistoricoBebidaTests();

	@Test
	public void contextLoads() {
		//tipoBebidaTests.salvarTipoBebida("Bebidas Alcoólica com gás","AG",json,mockMvc);
		//bebidasTests.salvarBebida("Vodka 0,6l", "A", 0.6,json,mockMvc,this);
		//sessaoTests.salvarSessao("Sessão 6", "NA", 400.00,json,mockMvc,this);
		//estoqueTests.entradaBebidas("Cerveja 1l", 100, "Sessão 3", "Lucas","E",json,mockMvc,this);
		//estoqueTests.saidabebidas("Pinga 1,5l", 50, "Sessão 3", "Lucas","S",json,mockMvc,this);
		estoqueTests.buscarTodosEstoque(json,mockMvc,this);
		estoqueTests.buscarEstoqueByTipo(json,mockMvc,this);
		historicoBebidaTests.buscarHistorico("A", "Sessão 3", "datahis,h.sessao.idSessao",json,mockMvc,this);
	}

	@Before
	public void setup() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
	}

	public TipoBebida buscarTipo(String tp) {
		String response = null;
		TipoBebida tipoBebida = null;
		try {
			response = mockMvc
					.perform(MockMvcRequestBuilders.get("/buscarTipoTp").content(tp)
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andReturn().getResponse().getContentAsString();
			Gson gson = new Gson();
			tipoBebida = gson.fromJson(response, TipoBebida.class);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tipoBebida;

	}

	public Sessao buscarSessaoByDescricao(String descricao) {
		String response = null;
		Sessao sessao = null;
		try {
			response = mockMvc
					.perform(MockMvcRequestBuilders.get("/buscarByDescricao").content(descricao)
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andReturn().getResponse().getContentAsString();
			Gson gson = new Gson();
			sessao = gson.fromJson(response, Sessao.class);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sessao;

	}

	public Bebida buscarNomeBebida(String nome) {
		String response = null;
		Bebida bebida = null;
		try {
			response = mockMvc
					.perform(MockMvcRequestBuilders.get("/buscarBebidaByNome").content(nome)
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
			Gson gson = new Gson();
			bebida = gson.fromJson(response, Bebida.class);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bebida;
	}
	
	public Collection<Sessao> buscarTipoSessao(Integer tipo) {
		return sService.findTipo(tipo);
	}

}
