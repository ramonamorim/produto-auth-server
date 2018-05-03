package br.com.empresa.produto.produtoauthserver.security.jsonwebtoken;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

public class WebFilterChainServerJWTAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

	@Override
	public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
		ServerWebExchange exchange = webFilterExchange.getExchange();

		exchange.getResponse().getHeaders().add(org.springframework.http.HttpHeaders.AUTHORIZATION,
				getHttpAuthHeaderValue(authentication));
		return webFilterExchange.getChain().filter(exchange);
	}

	private static String getHttpAuthHeaderValue(Authentication authentication) {
		return String.join(" ", "Bearer", tokenFromAuthentication(authentication));
	}

	private static String tokenFromAuthentication(Authentication authentication) {
		return new JWTTokenService().generateToken(authentication.getName(), authentication.getCredentials(),
				authentication.getAuthorities());
	}
}
