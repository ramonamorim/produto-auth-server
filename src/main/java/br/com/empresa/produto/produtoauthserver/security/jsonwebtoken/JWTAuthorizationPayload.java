package br.com.empresa.produto.produtoauthserver.security.jsonwebtoken;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

public class JWTAuthorizationPayload {

	public static String extract(ServerWebExchange exchange) {
		return exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

	}

}
