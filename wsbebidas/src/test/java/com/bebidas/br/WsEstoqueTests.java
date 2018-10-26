package com.bebidas.br;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bebidas.br.model.Bebida;
import com.bebidas.br.model.Estoque;
import com.bebidas.br.model.HistoricoBebida;
import com.bebidas.br.model.Sessao;
import com.bebidas.br.model.TipoBebida;
import com.bebidas.br.util.JsonDateSerializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WsEstoqueTests {

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
	 * @param wsbebidasApplicationTests
	 * @param mockMvc
	 * @param json
	 */
	public void entradaBebidas(String b, Integer qtd, String s, String responsavel, String tipoMovimento,
			JsonDateSerializer json, MockMvc mockMvc, WsbebidasApplicationTests teste) {
		Bebida bebida = new Bebida();
		Collection<Sessao> sess = new ArrayList<Sessao>();
		bebida = teste.buscarNomeBebida(b);
		if (bebida != null) {
			sess = teste.buscarTipoSessao(bebida.getTipoBebida().getIdTipoBebida());
			for (Sessao sessao : sess) {
				if (sessao.getDescricao().equals(s)) {
					salvarEstoque(popularEstoque(qtd, sessao, bebida, responsavel, tipoMovimento,mockMvc),mockMvc,json);
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
	 * @param teste
	 * @param mockMvc
	 * @param json
	 */
	public void saidabebidas(String b, Integer qtdSaida, String s, String responsavel, String tipoMovimento,
			JsonDateSerializer json, MockMvc mockMvc, WsbebidasApplicationTests teste) {
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
						salvarEstoque(salvarHistorico(tipoMovimento, responsavel, estoque, qtdSaida),mockMvc,json);
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
			String tipoMovimento, MockMvc mockMvc) {
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

	public void salvarEstoque(HistoricoBebida historicoBebida, MockMvc mockMvc, JsonDateSerializer json) {
		ResultActions response = null;
		Gson gson = new Gson();
		try {
			response = mockMvc
					.perform(MockMvcRequestBuilders.post("/salvarEstoque").content(json.asJsonString(historicoBebida))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));			
			String result = response.andReturn().getResponse().getContentAsString();
			if (response.andReturn().getResponse().getStatus() == 400) {
				System.out.println(result);
			} else if (response.andReturn().getResponse().getStatus() == 200) {
				System.out.println(result);
			}else if (response.andReturn().getResponse().getStatus() == 201) {
				HistoricoBebida historicoBebidar = gson.fromJson(result, HistoricoBebida.class);
				if (historicoBebidar.getEstoque().getIdEstoque() != null)
					System.out.println("Entrada/Saída feita com sucesso.");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void buscarTodosEstoque(JsonDateSerializer json, MockMvc mockMvc,
			WsbebidasApplicationTests wsbebidasApplicationTests) {
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
						System.out.println("Qtd em Estoque da bebida na sessão: " + estoque.getQtd());
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

	public void buscarEstoqueByTipo(JsonDateSerializer json, MockMvc mockMvc,
			WsbebidasApplicationTests wsbebidasApplicationTests) {
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

}
