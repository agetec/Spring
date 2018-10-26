package com.bebidas.br;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.ws.rs.core.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bebidas.br.model.TipoBebida;
import com.bebidas.br.util.JsonDateSerializer;
import com.google.gson.Gson;

public class WsTipoBebidaTests {
	/**
	 * 
	 * @param desc
	 *            Infome a descrição do tipo
	 * @param mockMvc
	 *            classe que possiblita o teste
	 * @param json
	 *            conversor de variável para string em formato json
	 * @param tp
	 *            Informe o tp ('NA'= NÃO ALCOOÓLICA, 'A'=ALCOÓLICA e etc...)
	 */
	public void salvarTipoBebida(String desc, String tipo, JsonDateSerializer json, MockMvc mockMvc) {
		try {
			TipoBebida tipoBebida = new TipoBebida();
			Gson gson = new Gson();
			tipoBebida.setDescricao(desc);
			tipoBebida.setTipo(tipo);
			ResultActions response = mockMvc
					.perform(MockMvcRequestBuilders.post("/salvarTpBebida").content(json.asJsonString(tipoBebida))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
			String tipoResult = response.andReturn().getResponse().getContentAsString();
			if (response.andExpect(status().isBadRequest()) != null) {
				System.out.println(tipoResult);
			} else if (response.andExpect(status().isCreated()) != null) {
				TipoBebida tipoBebid = gson.fromJson(tipoResult, TipoBebida.class);
				if (tipoBebid.getIdTipoBebida() != null)
					System.out.println("Tipo salvo com sucesso.");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
