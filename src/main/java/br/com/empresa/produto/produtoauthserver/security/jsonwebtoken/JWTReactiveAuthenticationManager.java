package br.com.empresa.produto.produtoauthserver.security.jsonwebtoken;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;

import reactor.core.publisher.Mono;

public class JWTReactiveAuthenticationManager implements ReactiveAuthenticationManager {

	
	
	
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		return Mono.just(authentication);
	}

}
