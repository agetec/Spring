package com.bebidas.br;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.ws.rs.core.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bebidas.br.model.Bebida;
import com.bebidas.br.model.TipoBebida;
import com.bebidas.br.util.JsonDateSerializer;
import com.google.gson.Gson;

public class WsBebidasTests {

	/**
	 * 
	 * @param bebida
	 *            informe o nome da bebida
	 * @param tp
	 *            informe o tp do tipo da bebida ('NA'= NÃO ALCOOÓLICA,
	 *            'A'=ALCOÓLICA e etc...)
	 * @param volume
	 *            informe o volume(exemplo 1l=1.00 / 2l=2.00 / 2,5l=2.5)
	 * @param wsbebidasApplicationTests 
	 */
	public void salvarBebida(String nome, String tipo, Double volume, JsonDateSerializer json, MockMvc mockMvc,
			WsbebidasApplicationTests teste) {
		TipoBebida tipoBebida;
		tipoBebida = teste.buscarTipo(tipo);
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

}
