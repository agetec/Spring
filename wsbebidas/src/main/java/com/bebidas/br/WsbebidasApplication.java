package com.bebidas.br;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WsbebidasApplication {

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WsbebidasApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WsbebidasApplication.class, args);
	}
}
