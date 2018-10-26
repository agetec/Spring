package com.bebidas.br;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bebidas.br.model.HistoricoBebida;
import com.bebidas.br.model.Sessao;
import com.bebidas.br.model.TipoBebida;
import com.bebidas.br.util.JsonDateSerializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WsHistoricoBebidaTests {

	public void buscarHistorico(String tp, String sessao, String ordenacao, JsonDateSerializer json, MockMvc mockMvc,
			WsbebidasApplicationTests teste) {
		String response = null;
		TipoBebida tipoBebida = null;
		Type listType = null;
		tipoBebida = teste.buscarTipo(tp);
		Sessao sessao2 = teste.buscarSessaoByDescricao(sessao);
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
}
