package br.com.empresa.produto.produtoauthserver.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class Message {

	@GetMapping("/")
	public Flux<String> hello() {
		return Flux.just("hello");
	}

	@GetMapping("/api/private")
	@PreAuthorize("hasRole('USER')")
	public Flux<String> privateMessage() {
		return Flux.just("ROLE DE USUARIO");
	}

	@GetMapping("/api/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public Flux<String> privateMessageAdmin() {
		return Flux.just("ROLE DE ADMIN");
	}

}
