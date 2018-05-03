package br.com.empresa.produto.produtoauthserver.security.jsonwebtoken;

import java.text.ParseException;

import com.nimbusds.jwt.SignedJWT;

import reactor.core.publisher.Mono;

public class VerifySignedJWT {

	public static Mono<SignedJWT> check(String token) {
		try {
			return Mono.just(SignedJWT.parse(token));
		} catch (ParseException e) {
			return Mono.empty();
		}
	}
}
