package com.bebidas.br;

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
import org.springframework.data.repository.query.Param;
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
		salvarTipoBebida("Bebida não Alcoólica", "NA");
		salvarBebida("Coca-cola", "NA", 2.00);
		salvarSessao("Sessão 1", "NA", 400.00);
		entradaBebidas("Coca-cola 1l", 200, "Sessão 1");
		buscarTodosEstoque();
		buscarEstoqueByTipo();
	}

	@Before
	public void setup() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
	}
	/**
	 * 
	 * @param desc
	 *            Infome a descrição do tipo
	 * @param tp
	 *            Informe a tipo ('NA'= NÃO ALCOOÓLICA 'A'=ALCOÓLICA)
	 */
	public void salvarTipoBebida(String desc, String tipo) {
		try {
			TipoBebida tipoBebida = new TipoBebida();
			tipoBebida.setDescricao(desc);
			tipoBebida.setTipo(tipo);
			String response = mockMvc
					.perform(MockMvcRequestBuilders.post("/salvarTpBebida").content(asJsonString(tipoBebida))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andReturn().getResponse().getContentAsString();
			Gson gson = new Gson();
			TipoBebida tipoBebid = gson.fromJson(response, TipoBebida.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param bebida
	 *            informe o nome da bebida
	 * @param tipo
	 *            informe o nome da bebida
	 * @param volume
	 *            informe o volume(exemplo 1l=1.00 / 2l=2.00 / 2,5l=2.5)
	 */
	public void salvarBebida(String nome, String tipo, Double volume) {
		TipoBebida tipoBebida;
		tipoBebida = buscarTipo(tipo);
		if (tipoBebida != null) {
			Bebida bebida = new Bebida();
			bebida.setNome(nome);
			bebida.setTipoBebida(tipoBebida);
			bebida.setVolume(volume);
			try {
				String response = mockMvc
						.perform(MockMvcRequestBuilders.post("/salvarBebida").content(asJsonString(bebida))
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

	/**
	 * 
	 * @param desc
	 *            Infome a descrição da sessão
	 * @param tp
	 *            Informe a tipo da bebida('NA'= NÃO ALCOOÓLICA 'A'=ALCOÓLICA)
	 * @param Capacidade
	 *            Informe a capacidade da sessão
	 */
	public void salvarSessao(String desc, String tp, Double Capacidade) {
		Sessao sessao = new Sessao();

		sessao.setDescricao(desc);
		sessao.setTipoBebida(buscarTipo(tp));
		sessao.setCapacidade(Capacidade);
		try {
			String response = mockMvc
					.perform(MockMvcRequestBuilders.post("/salvarSessao").content(asJsonString(sessao))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andReturn().getResponse().getContentAsString();
			Gson gson = new Gson();
			Sessao sess = gson.fromJson(response, Sessao.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param b
	 *            Infome a bebida
	 * @param qtd
	 *            Informe a quantidade
	 * @param s
	 *            Informe a sessão
	 */
	public void entradaBebidas(String b, Integer qtd, String s) {
		Bebida bebida = new Bebida();
		Collection<Sessao> sess = new ArrayList<Sessao>();
		bebida = buscarNomeBebida(b);
		if (bebida != null) {
			sess = buscarTipoSessao(bebida.getTipoBebida().getIdTipoBebida());
			for (Sessao sessao : sess) {
				if (sessao.getDescricao().equals(s)) {
					salvarEstoque(popularEstoque(qtd, sessao, bebida));
				}
			}
		}
	}

	/**
	 * 
	 * @param b
	 *            Infome a bebida
	 * @param qtd
	 *            Informe a quantidade
	 * @param s
	 *            Informe a sessão
	 */
	public void saidabebidas(String b, Integer qtdSaida, String s) {
		String response = null;
		Gson gson = new Gson();
		Type listTypeE = null;
		try {
			response = mockMvc
					.perform(MockMvcRequestBuilders.get("/buscarTodosEstoque").contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
			listTypeE = new TypeToken<ArrayList<Estoque>>() {
			}.getType();
			Collection<Estoque> estoques = gson.fromJson(response, listTypeE);
			for (Estoque estoque : estoques) {
				if (estoque.getSessao().getDescricao().equals(s)) {
					if (estoque.getBebida().getNome().equals(b)) {
						estoque.setQtdEstocar(qtdSaida);
						HistoricoBebida historicoBebida = new HistoricoBebida();
						historicoBebida.setDatahis(new Date());
						historicoBebida.setTipoMovimento("S");
						historicoBebida.setResponsavel("Lucas");
						historicoBebida.setEstoque(estoque);
						historicoBebida.setQtd(qtdSaida);
						salvarEstoque(historicoBebida);
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HistoricoBebida popularEstoque(Integer qtd, Sessao sessao, Bebida bebida) {
		String response = null;
		Estoque estoque = new Estoque();
		estoque.setSessao(sessao);
		estoque.setBebida(bebida);
		try {
			response = mockMvc
					.perform(MockMvcRequestBuilders.get("/buscaEstoqueBebida").content(asJsonString(estoque))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andReturn().getResponse().getContentAsString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();
		estoque = gson.fromJson(response, Estoque.class);
		if (estoque == null) {
			estoque = new Estoque();
			estoque.setBebida(bebida);
			estoque.setQtdEstocar(qtd);
			estoque.setSessao(sessao);
		} else {
			estoque.setQtdEstocar(qtd);
		}
		HistoricoBebida historicoBebida = new HistoricoBebida();
		historicoBebida.setDatahis(new Date());
		historicoBebida.setTipoMovimento("E");
		historicoBebida.setEstoque(estoque);
		historicoBebida.setResponsavel("Lucas");
		historicoBebida.setQtd(qtd);
		return historicoBebida;
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
		Double vltEstoque = null;
		try {
			responseSessao = mockMvc
					.perform(MockMvcRequestBuilders.get("/buscarTodosSess").contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
			listType = new TypeToken<ArrayList<Sessao>>() {
			}.getType();
			Collection<Sessao> sessaos = gson.fromJson(responseSessao, listType);

			System.out.println("--Estoque por sessão--\r\n");
			for (Sessao sessao : sessaos) {
				System.out.println("--" + sessao.getDescricao() + "--");
				if (sessao.getTipoBebida() != null) {
					System.out.println("Tipo da Bebida:" + sessao.getTipoBebida().getDescricao());
					response = mockMvc
							.perform(MockMvcRequestBuilders.get("/buscarTodosEstoqueBySessao")
									.content(asJsonString(sessao.getIdSessao())).contentType(MediaType.APPLICATION_JSON)
									.accept(MediaType.APPLICATION_JSON))
							.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
					listTypeE = new TypeToken<ArrayList<Estoque>>() {
					}.getType();
					Collection<Estoque> estoques = gson.fromJson(response, listTypeE);

					for (Estoque estoque : estoques) {
						vltEstoque = 0.00;
						System.out.println("Bebida:" + estoque.getBebida().getNome());
						System.out.println("Volume da bebida: " + estoque.getBebida().getVolume() + " Litros");
						System.out.println("Qtd em Estoque da sessão: " + estoque.getQtd());
						vltEstoque = vltEstoque + (estoque.getQtd() * estoque.getBebida().getVolume());
					}
				}
				System.out.println("Capacidade da Sessão: " + sessao.getCapacidade() + " Litros");
				System.out.println("Volume total do estoque da sessão: " + vltEstoque + " Litros" + "\r\n");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void buscarEstoqueByTipo() {
		String responsetTipo = null;
		String response = null;
		Gson gson = new Gson();
		Type listType = null;
		Type listTypeE = null;
		try {
			responsetTipo = mockMvc
					.perform(MockMvcRequestBuilders.get("/buscarTodosTp").contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
			listType = new TypeToken<ArrayList<TipoBebida>>() {
			}.getType();
			Collection<TipoBebida> tipoBebidas = gson.fromJson(responsetTipo, listType);

			System.out.println("\r\n--Estoque por tipo--");
			for (TipoBebida tipoBebida : tipoBebidas) {
				Double vltEstoque = 0.00;
				System.out.println("\r\n--" + tipoBebida.getDescricao() + "--");
				response = mockMvc
						.perform(MockMvcRequestBuilders.get("/buscarTodosEstoqueByTipo")
								.content(asJsonString(tipoBebida.getIdTipoBebida()))
								.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
				listTypeE = new TypeToken<ArrayList<Estoque>>() {
				}.getType();
				Collection<Estoque> estoques = gson.fromJson(response, listTypeE);
				for (Estoque estoque : estoques) {
					vltEstoque = vltEstoque + (estoque.getQtd() * estoque.getBebida().getVolume());
				}
				System.out.println("Volume total do estoque do tipo: " + vltEstoque + " Litros");
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
		String response = null;
		TipoBebida tipoBebida = null;
		try {
			response = mockMvc
					.perform(MockMvcRequestBuilders.get("/buscarTipoTp").content(tipo)
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

	public Bebida buscarNomeBebida(String nome) {
		String response = null;
		Bebida bebida = null;
		try {
			response = mockMvc
					.perform(MockMvcRequestBuilders.get("/buscarBebidaByNome").content(nome)
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andReturn().getResponse().getContentAsString();
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
