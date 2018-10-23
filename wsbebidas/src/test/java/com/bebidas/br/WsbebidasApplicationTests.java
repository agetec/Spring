package com.bebidas.br;

import java.util.ArrayList;
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
import com.bebidas.br.service.TipoBebidaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WsbebidasApplicationTests {

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	TipoBebidaService tpService = new TipoBebidaService();

	@Test
	public void contextLoads() {
		salvarTipoBebida();
		salvarBebida();
		salvarSessao();
	}

	@Before
	public void setup() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
	}

	public void salvarTipoBebida() {
		try {
			Collection<TipoBebida> tipoBebidas = new ArrayList<TipoBebida>();
			TipoBebida tipoBebida = new TipoBebida();
			tipoBebida.setDescricao("Bebidas Alcoólicas");
			tipoBebida.setTipo("A");
			tipoBebidas.add(tipoBebida);

			TipoBebida tipoBebida1 = new TipoBebida();
			tipoBebida1.setDescricao("Bebidas não Alcoólicas");
			tipoBebida1.setTipo("NA");
			tipoBebidas.add(tipoBebida1);

			for (TipoBebida tipoBebida2 : tipoBebidas) {
				String response = mockMvc
						.perform(MockMvcRequestBuilders.post("/salvarTpBebida").content(asJsonString(tipoBebida2))
								.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
						.andReturn().getResponse().getContentAsString();
				Gson gson = new Gson();
				TipoBebida tipoBebida3 = gson.fromJson(response, TipoBebida.class);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void salvarBebida() {
		Collection<Bebida> bebidas = new ArrayList<Bebida>();
		TipoBebida tipoBebida;

		tipoBebida =buscarTipo("NA");
		Bebida bebida = new Bebida();
		bebida.setNome("Coca-cola");
		bebida.setTipoBebida(tipoBebida);
		bebida.setVolume(2.00);
		bebidas.add(bebida);
		Bebida bebida2 = new Bebida();
		bebida2.setNome("Fanta");
		bebida2.setTipoBebida(tipoBebida);
		bebida2.setVolume(2.00);
		bebidas.add(bebida2);

		tipoBebida = buscarTipo("A");
		Bebida bebida3 = new Bebida();
		bebida3.setNome("Cerveja");
		bebida3.setTipoBebida(tipoBebida);
		bebida3.setVolume(1.00);
		bebidas.add(bebida3);
		Bebida bebida4 = new Bebida();
		bebida4.setNome("Pinga");
		bebida4.setTipoBebida(tipoBebida);
		bebida4.setVolume(1.5);
		bebidas.add(bebida4);

		for (Bebida bebi : bebidas) {
			try {
				mockMvc.perform(MockMvcRequestBuilders.post("/salvarBebida").content(asJsonString(bebi))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void salvarSessao() {
		Sessao  sessao1=new Sessao(); 
		Sessao  sessao2=new Sessao(); 
		Sessao  sessao3=new Sessao(); 
		Sessao  sessao4=new Sessao(); 
		Sessao  sessao5=new Sessao(); 
		Collection<Sessao> sessaos = new ArrayList<Sessao>();
		sessao1.setDescricao("Sessão 1");
		sessao1.setTipoBebida(buscarTipo("NA"));
		sessao1.setCapacidade(400.00);
		
		sessao2.setDescricao("Sessão 2");
		sessao2.setTipoBebida(buscarTipo("NA"));
		sessao2.setCapacidade(400.00);
		
		sessao3.setDescricao("Sessão 3");
		sessao3.setTipoBebida(buscarTipo("A"));
		sessao3.setCapacidade(500.00);
		
		sessao4.setDescricao("Sessão 4");
		sessao4.setTipoBebida(buscarTipo("A"));
		sessao4.setCapacidade(500.00);
		
		sessao5.setDescricao("Sessão 5");
		sessao5.setTipoBebida(buscarTipo("A"));
		sessao5.setCapacidade(500.00);
		
		sessaos.add(sessao1);
		sessaos.add(sessao2);
		sessaos.add(sessao3);
		sessaos.add(sessao4);
		sessaos.add(sessao5);
		for (Sessao sess : sessaos) {
			try {
				mockMvc.perform(MockMvcRequestBuilders.post("/salvarSessao").content(asJsonString(sess))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	public void entradaBebidas() {
		
	}
	public TipoBebida buscarTipo(String tipo) {
		return tpService.buscarTipoBebdidaByTipo(tipo);
	}
	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
