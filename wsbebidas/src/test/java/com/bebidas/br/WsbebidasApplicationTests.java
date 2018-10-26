package com.bebidas.br;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bebidas.br.model.Bebida;
import com.bebidas.br.model.Estoque;
import com.bebidas.br.model.HistoricoBebida;
import com.bebidas.br.model.Sessao;
import com.bebidas.br.model.TipoBebida;
import com.bebidas.br.service.SessaoService;
import com.bebidas.br.util.JsonDateSerializer;
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
	SessaoService sService = new SessaoService();

	@Autowired
	private JsonDateSerializer json;

	@Test
	public void contextLoads() {
		//new WsTipoBebidaTests().salvarTipoBebida("Bebidas Alcoólica com gás", "AG",json,mockMvc);
		//new WsBebidasTests().salvarBebida("Pinga 1,5l", "A", 1.5,json,mockMvc,this);
		// salvarSessao("Sessão 5", "A", 500.00);
		// entradaBebidas("Cerveja 1l", 100, "Sessão 4", "Lucas", "E");
		// saidabebidas("Pinga 1,5l", 100, "Sessão 3", "Lucas", "S");
		buscarTodosEstoque();
		buscarEstoqueByTipo();
		buscarHistorico("A", "Sessão 3", "datahis,h.sessao.idSessao");
	}

	@Before
	public void setup() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
	}

	/**
	 * 
	 * @param bebida
	 *            informe o nome da bebida
	 * @param tp
	 *            informe o tp do tipo da bebida ('NA'= NÃO ALCOOÓLICA,
	 *            'A'=ALCOÓLICA e etc...)
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
						.perform(MockMvcRequestBuilders.post("/salvarBebida").content(json.asJsonString(bebida))
								.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
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
	 *            Informe a tp do tipo da bebida('NA'= NÃO ALCOOÓLICA 'A'=ALCOÓLICA
	 *            e etc...)
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
					.perform(MockMvcRequestBuilders.post("/salvarSessao").content(json.asJsonString(sessao))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
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
	 * @param responsavel
	 *            Informe o responsavel pela inclusao
	 * @param tipoMovimento
	 *            Informe o tipo do movimento E=entrada e S=saída
	 */
	public void entradaBebidas(String b, Integer qtd, String s, String responsavel, String tipoMovimento) {
		Bebida bebida = new Bebida();
		Collection<Sessao> sess = new ArrayList<Sessao>();
		bebida = buscarNomeBebida(b);
		if (bebida != null) {
			sess = buscarTipoSessao(bebida.getTipoBebida().getIdTipoBebida());
			for (Sessao sessao : sess) {
				if (sessao.getDescricao().equals(s)) {
					salvarEstoque(popularEstoque(qtd, sessao, bebida, responsavel, tipoMovimento));
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
	 * @param responsavel
	 *            Informe o responsavel pela inclusao
	 * @param tipoMovimento
	 *            Informe o tipo do movimento E=entrada e S=saída
	 */
	public void saidabebidas(String b, Integer qtdSaida, String s, String responsavel, String tipoMovimento) {
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
						salvarEstoque(salvarHistorico(tipoMovimento, responsavel, estoque, qtdSaida));
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

	public HistoricoBebida popularEstoque(Integer qtd, Sessao sessao, Bebida bebida, String responsavel,
			String tipoMovimento) {
		String response = null;
		Estoque estoque = new Estoque();
		try {
			response = mockMvc
					.perform(MockMvcRequestBuilders.get("/buscaEstoqueBebida")
							.param("idSessao", sessao.getIdSessao().toString())
							.param("idBebida", bebida.getIdBebida().toString()).contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
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
		return salvarHistorico(tipoMovimento, responsavel, estoque, qtd);

	}

	public HistoricoBebida salvarHistorico(String tipoMovimento, String responsavel, Estoque estoque, Integer qtd) {
		HistoricoBebida historicoBebida = new HistoricoBebida();
		historicoBebida.setDatahis(new Date());
		historicoBebida.setTipoMovimento(tipoMovimento);
		historicoBebida.setResponsavel(responsavel);
		historicoBebida.setEstoque(estoque);
		historicoBebida.setSessao(estoque.getSessao());
		historicoBebida.setTipo(estoque.getSessao().getTipoBebida());
		historicoBebida.setQtd(qtd);
		return historicoBebida;
	}

	public void salvarEstoque(HistoricoBebida historicoBebida) {
		String response = null;
		try {
			response = mockMvc
					.perform(MockMvcRequestBuilders.post("/salvarEstoque").content(json.asJsonString(historicoBebida))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
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
					vltEstoque = 0.00;
					System.out.println("Tipo da Bebida:" + sessao.getTipoBebida().getDescricao());
					response = mockMvc
							.perform(MockMvcRequestBuilders.get("/buscarTodosEstoqueBySessao")
									.content(json.asJsonString(sessao.getIdSessao()))
									.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
							.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
					listTypeE = new TypeToken<ArrayList<Estoque>>() {
					}.getType();
					Collection<Estoque> estoques = gson.fromJson(response, listTypeE);

					for (Estoque estoque : estoques) {

						System.out.println("Bebida:" + estoque.getBebida().getNome());
						System.out.println("Volume da bebida: " + estoque.getBebida().getVolume() + " Litros");
						System.out.println("Qtd em Estoque da sessão: " + estoque.getQtd());
						vltEstoque = vltEstoque + (estoque.getQtd() * estoque.getBebida().getVolume());
					}
				}
				System.out.println("Capacidade da Sessão: " + sessao.getCapacidade() + " Litros");
				System.out.println(vltEstoque == null ? "Volume total do estoque da sessão: " + 0.00
						: "Volume total do estoque da sessão: " + vltEstoque + " Litros" + "\r\n");
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
								.content(json.asJsonString(tipoBebida.getIdTipoBebida()))
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

	public void buscarHistorico(String tp, String sessao, String ordenacao) {
		String response = null;
		TipoBebida tipoBebida = null;
		Type listType = null;
		tipoBebida = buscarTipo(tp);
		Sessao sessao2 = buscarSessaoByDescricao(sessao);
		if (tipoBebida != null && sessao2 != null) {
			try {
				response = mockMvc
						.perform(MockMvcRequestBuilders.get("/buscarByTipoSessao")
								.param("tipo", tipoBebida.getIdTipoBebida().toString())
								.param("idSessao", sessao2.getIdSessao().toString()).param("ordenacao", ordenacao)
								.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
						.andReturn().getResponse().getContentAsString();
				Gson gson = new Gson();
				listType = new TypeToken<ArrayList<HistoricoBebida>>() {
				}.getType();
				Collection<HistoricoBebida> historicosBebida = gson.fromJson(response, listType);
				System.out.println("\r\n--Histórico--");
				Integer i = 0;
				for (HistoricoBebida historicoBebida : historicosBebida) {
					if (i == 0) {
						System.out.println("--" + historicoBebida.getSessao().getDescricao() + "--");
						System.out.println("--" + historicoBebida.getTipo().getDescricao() + "--");
					}
					System.out.println("Data e Hora:"
							+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(historicoBebida.getDatahis()));
					System.out.println("Responsavel:" + historicoBebida.getResponsavel());
					System.out.println("Volume:" + historicoBebida.getQtd());
					System.out.println("Tipo de Movimento:"
							+ (historicoBebida.getTipoMovimento().equals("S") ? "Saída\r\n" : "Entrada\r\n"));
					i++;
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

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
