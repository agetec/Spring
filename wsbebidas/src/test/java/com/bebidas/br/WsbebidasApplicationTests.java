package com.bebidas.br;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import com.bebidas.br.model.Estoque;
import com.bebidas.br.model.HistoricoBebida;
import com.bebidas.br.model.Sessao;
import com.bebidas.br.model.TipoBebida;
import com.bebidas.br.service.BebidaService;
import com.bebidas.br.service.SessaoService;
import com.bebidas.br.service.TipoBebidaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WsbebidasApplicationTests {

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	TipoBebidaService tpService = new TipoBebidaService();
	@Autowired
	BebidaService bService = new BebidaService();

	@Autowired
	SessaoService sService = new SessaoService();

	@Test
	public void contextLoads() {
		salvarTipoBebida();
		salvarBebida();
		salvarSessao();
		entradaBebidasNalcoolica();
		entradaBebidasAlcoolica();
		buscarTodosEstoque();
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

		tipoBebida = buscarTipo("NA");
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
				String response = mockMvc
						.perform(MockMvcRequestBuilders.post("/salvarBebida").content(asJsonString(bebi))
								.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
						.andReturn().getResponse().getContentAsString();
				Gson gson = new Gson();
				Bebida bebida5 = gson.fromJson(response, Bebida.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void salvarSessao() {
		Sessao sessao1 = new Sessao();
		Sessao sessao2 = new Sessao();
		Sessao sessao3 = new Sessao();
		Sessao sessao4 = new Sessao();
		Sessao sessao5 = new Sessao();
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
				String response = mockMvc
						.perform(MockMvcRequestBuilders.post("/salvarSessao").content(asJsonString(sess))
								.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
						.andReturn().getResponse().getContentAsString();
				Gson gson = new Gson();
				Sessao sessao = gson.fromJson(response, Sessao.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void entradaBebidasNalcoolica() {
		Bebida bebida1 = new Bebida();
		Bebida bebida2 = new Bebida();
		Collection<Sessao> sess = new ArrayList<Sessao>();
		bebida1 = buscarNomeBebida("Coca-cola");
		bebida2 = buscarNomeBebida("Fanta");
		if (bebida1 != null && bebida2 != null) {
			sess = buscarTipoSessao(bebida1.getTipoBebida().getIdTipoBebida());
			for (Sessao sessao : sess) {
				if (sessao.getDescricao().equals("Sessão 1")) {
					Estoque estoque = new Estoque();
					estoque.setBebida(bebida1);
					estoque.setQtd(200);
					estoque.setSessao(sessao);
					HistoricoBebida historicoBebida = new HistoricoBebida();
					historicoBebida.setDatahis(new Date());
					historicoBebida.setTipoMovimento("E");
					historicoBebida.setResponsavel("Lucas");
					historicoBebida.setEstoque(estoque);
					salvarEstoque(historicoBebida);
				} else if (sessao.getDescricao().equals("Sessão 2")) {
					Estoque estoque = new Estoque();
					estoque.setBebida(bebida2);
					estoque.setQtd(200);
					estoque.setSessao(sessao);
					HistoricoBebida historicoBebida = new HistoricoBebida();
					historicoBebida.setDatahis(new Date());
					historicoBebida.setTipoMovimento("E");
					historicoBebida.setResponsavel("Lucas");
					historicoBebida.setEstoque(estoque);
					salvarEstoque(historicoBebida);

				}
			}
		}
	}

	public void entradaBebidasAlcoolica() {
		Bebida bebida1 = new Bebida();
		Bebida bebida2 = new Bebida();
		Collection<Sessao> sess = new ArrayList<Sessao>();
		bebida1 = buscarNomeBebida("Cerveja");
		bebida2 = buscarNomeBebida("Pinga");
		if (bebida1 != null && bebida2 != null) {
			sess = buscarTipoSessao(bebida1.getTipoBebida().getIdTipoBebida());
			for (Sessao sessao : sess) {
				if (sessao.getDescricao().equals("Sessão 3")) {
					Estoque estoque = new Estoque();
					estoque.setBebida(bebida1);
					estoque.setQtd(200);
					estoque.setSessao(sessao);
					HistoricoBebida historicoBebida = new HistoricoBebida();
					historicoBebida.setDatahis(new Date());
					historicoBebida.setTipoMovimento("E");
					historicoBebida.setResponsavel("Lucas");
					historicoBebida.setEstoque(estoque);
					salvarEstoque(historicoBebida);
				} else if (sessao.getDescricao().equals("Sessão 4")) {
					Estoque estoque = new Estoque();
					estoque.setBebida(bebida2);
					estoque.setQtd(200);
					estoque.setSessao(sessao);
					HistoricoBebida historicoBebida = new HistoricoBebida();
					historicoBebida.setDatahis(new Date());
					historicoBebida.setTipoMovimento("E");
					historicoBebida.setEstoque(estoque);
					historicoBebida.setResponsavel("Lucas");
					salvarEstoque(historicoBebida);
				}
			}
		}
	}

	public void salvarEstoque(HistoricoBebida historicoBebida) {
		String response = null;
		try {
			response = mockMvc
					.perform(MockMvcRequestBuilders.post("/salvarHisBebida").content(asJsonString(historicoBebida))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andReturn().getResponse().getContentAsString();
			Gson gson = new Gson();
			HistoricoBebida historicoBebidar = gson.fromJson(response, HistoricoBebida.class);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void buscarTodosEstoque() {
		String responseSessao = null;
		String response = null;
		Gson gson = new Gson();
		Type listType = null;
		Type listTypeE = null;
		try {
			responseSessao = mockMvc.perform(MockMvcRequestBuilders.get("/buscarTodosSess")					
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andReturn()
					.getResponse().getContentAsString();			
			listType = new TypeToken<ArrayList<Sessao>>() {	}.getType();			
			Collection<Sessao> sessaos = gson.fromJson(responseSessao, listType);
			
			System.out.println("--Estoque por sessão--");
			for (Sessao sessao : sessaos) {
				Double vltEstoque = 0.00;
				System.out.println("--"+sessao.getDescricao()+"--");
				System.out.println("Tipo da Bebida:" + sessao.getTipoBebida().getDescricao());
				
				response = mockMvc.perform(MockMvcRequestBuilders.get("/buscarTodosEstoqueBySessao")
						.content(asJsonString(sessao.getIdSessao()))						
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isCreated()).andReturn()
						.getResponse().getContentAsString();
				listTypeE = new TypeToken<ArrayList<Estoque>>() {}.getType();
				Collection<Estoque> estoques = gson.fromJson(response, listTypeE);
				
				for (Estoque estoque : estoques) {
					System.out.println("Bebida:" + estoque.getBebida().getNome());
					System.out.println("Volume da bebida:" + estoque.getBebida().getVolume() + "Litros");
					System.out.println("Qtd em Estoque da sessão:" + estoque.getQtd());
					vltEstoque = vltEstoque + (estoque.getQtd() * estoque.getBebida().getVolume());
				}
				System.out.println("Volume total do estoque da sessão:" + vltEstoque);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TipoBebida buscarTipo(String tipo) {
		return tpService.buscarTipoBebdidaByTipo(tipo);
	}

	public Bebida buscarTipoBebida(Integer tipo) {
		return bService.findTipo(tipo);
	}

	public Bebida buscarNomeBebida(String nome) {
		return bService.findNome(nome);
	}

	public Collection<Sessao> buscarTipoSessao(Integer tipo) {
		return sService.findTipo(tipo);
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
