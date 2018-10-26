package com.bebidas.br;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.ws.rs.core.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bebidas.br.model.Sessao;
import com.bebidas.br.util.JsonDateSerializer;
import com.google.gson.Gson;

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

		sessao.setDescricao(desc);
		sessao.setTipoBebida(teste.buscarTipo(tp));
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

}
