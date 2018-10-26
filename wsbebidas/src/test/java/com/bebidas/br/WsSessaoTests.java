package com.bebidas.br;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bebidas.br.model.Sessao;
import com.bebidas.br.model.TipoBebida;
import com.bebidas.br.util.JsonDateSerializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WsSessaoTests {

	/**
	 * 
	 * @param desc
	 *            Infome a descrição da sessão
	 * @param tp
	 *            Informe a tp do tipo da bebida('NA'= NÃO ALCOOÓLICA 'A'=ALCOÓLICA
	 *            e etc...)
	 * @param Capacidade
	 *            Informe a capacidade da sessão
	 * @param wsbebidasApplicationTests
	 * @param mockMvc
	 * @param json
	 */
	public void salvarSessao(String desc, String tp, Double Capacidade, JsonDateSerializer json, MockMvc mockMvc,
			WsbebidasApplicationTests teste) {
		Sessao sessao = new Sessao();
		Gson gson = new Gson();
		sessao.setDescricao(desc);
		sessao.setTipoBebida(teste.buscarTipo(tp));
		sessao.setCapacidade(Capacidade);
		try {
			ResultActions response = mockMvc
					.perform(MockMvcRequestBuilders.post("/salvarSessao").content(json.asJsonString(sessao))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
			String result = response.andReturn().getResponse().getContentAsString();
			if (response.andReturn().getResponse().getStatus() == 400) {
				System.out.println(result);
			} else if (response.andReturn().getResponse().getStatus() == 201) {
				Sessao sess = gson.fromJson(result, Sessao.class);
				if (sess.getIdSessao() != null)
					System.out.println("Sessão salva com sucesso.");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param tp
	 *            tp do tipo de bebida
	 * @param qtdEstocar
	 *            qtd sugerida
	 * @param mockMvc
	 *            classe para teste
	 * @param teste
	 */
	public void buscarByArmazenar(String tp, Integer qtdEstocar, MockMvc mockMvc, WsbebidasApplicationTests teste) {
		String response = null;
		Gson gson = new Gson();
		Type listType = null;
		TipoBebida tipoBebida = teste.buscarTipo(tp);
		try {
			response = mockMvc
					.perform(MockMvcRequestBuilders.get("/buscarSessaoByArmazenar")
							.param("tipo", tipoBebida.getIdTipoBebida().toString())
							.param("qtdEstocar", qtdEstocar.toString()).contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
			listType = new TypeToken<ArrayList<Sessao>>() {
			}.getType();
			Collection<Sessao> sessaos = gson.fromJson(response, listType);
			System.out.println("--Sessão(ões) disponíveis para armazenar a quantidade informada--");
			for (Sessao sessao : sessaos) {
				System.out.println(sessao.getDescricao());
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param tp
	 *            tp do tipo de bebida
	 * @param qtdEstocar
	 *            qtd sugerida
	 * @param mockMvc
	 *            classe para teste
	 * @param teste
	 */
	public void buscarByVender(String tp, Integer qtdVender, MockMvc mockMvc, WsbebidasApplicationTests teste) {
		String response = null;
		Gson gson = new Gson();
		Type listType = null;
		TipoBebida tipoBebida = teste.buscarTipo(tp);
		try {
			response = mockMvc
					.perform(MockMvcRequestBuilders.get("/buscarSessaoByVender")
							.param("tipo", tipoBebida.getIdTipoBebida().toString())
							.param("qtdVender", qtdVender.toString()).contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
			listType = new TypeToken<ArrayList<Sessao>>() {
			}.getType();
			Collection<Sessao> sessaos = gson.fromJson(response, listType);
			System.out.println("--Sessão(ões) disponíveis para vender a quantidade informada--");
			for (Sessao sessao : sessaos) {
				System.out.println(sessao.getDescricao());
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
