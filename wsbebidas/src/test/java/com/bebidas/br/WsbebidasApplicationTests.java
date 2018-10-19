package com.bebidas.br;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
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

import com.bebidas.br.model.TipoBebida;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WsbebidasApplicationTests {

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;

	@Test
	public void contextLoads() {

	}

	@Before
	public void setup() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
	}

	@Test
	public void salvarTipoBebida() {
		try {
			Collection<TipoBebida> tipoBebidas = new ArrayList<TipoBebida>();
			TipoBebida tipoBebida = new TipoBebida();
			tipoBebida.setDescricao("Bebidas Alcoólicas");
			tipoBebidas.add(tipoBebida);
			TipoBebida tipoBebida1 = new TipoBebida();
			tipoBebida1.setDescricao("Bebidas não Alcoólicas");
			tipoBebidas.add(tipoBebida1);
			for (TipoBebida tipoBebida2 : tipoBebidas) {
				OutputStream outputStream = mockMvc
						.perform(MockMvcRequestBuilders.post("/salvarTpBebida").content(asJsonString(tipoBebida2))
								.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
						.andReturn().getResponse().getOutputStream();				
				ByteArrayOutputStream byte1 = new ByteArrayOutputStream();
				outputStream.write(byte1.toByteArray());
				Gson gson = new Gson();
				TipoBebida tipoBebida3 = gson.fromJson(outputStream.toString(), TipoBebida.class);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String write(int result) {
		StringBuilder mBuf = null;
		return mBuf.append((char) result).toString();
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
