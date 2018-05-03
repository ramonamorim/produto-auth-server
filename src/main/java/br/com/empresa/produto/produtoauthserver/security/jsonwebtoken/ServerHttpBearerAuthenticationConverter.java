package br.com.empresa.produto.produtoauthserver.security.jsonwebtoken;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

public class ServerHttpBearerAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {

	private static final String BEARER = "Bearer ";
	private static final Predicate<String> matchBearerLength = authValue -> authValue.length() > BEARER.length();
	private static final Function<String, String> isolateBearerValue = authValue -> authValue.substring(BEARER.length(),
			authValue.length());

	@Override
	public Mono<Authentication> apply(ServerWebExchange serverWebExchange) {
		return Mono.justOrEmpty(serverWebExchange).map(JWTAuthorizationPayload::extract).filter(Objects::nonNull)
				.filter(matchBearerLength).map(isolateBearerValue).map(VerifySignedJWT::check)
				.map(UsernamePasswordAuthenticationFromJWTToken::create).filter(Objects::nonNull);
	}

}
