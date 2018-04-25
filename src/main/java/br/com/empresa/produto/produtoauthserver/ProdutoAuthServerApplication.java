package br.com.empresa.produto.produtoauthserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication
@EnableWebFluxSecurity
public class ProdutoAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdutoAuthServerApplication.class, args);
	}
}
