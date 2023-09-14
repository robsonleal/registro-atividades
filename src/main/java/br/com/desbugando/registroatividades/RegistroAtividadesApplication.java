package br.com.desbugando.registroatividades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RegistroAtividadesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistroAtividadesApplication.class, args);
	}

}
