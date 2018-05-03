package br.com.empresa.produto.produtoauthserver.security.jsonwebtoken;

import java.util.function.Function;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

public class JWTAuthorizationWebFilter implements WebFilter {

	private ServerAuthenticationSuccessHandler authenticationSuccessHandler = new WebFilterChainServerAuthenticationSuccessHandler();
	private final ReactiveAuthenticationManager authenticationManager = new JWTReactiveAuthenticationManager();
	private ServerWebExchangeMatcher requiresAuthenticationMatcher = ServerWebExchangeMatchers.pathMatchers("/api/**");
	private Function<ServerWebExchange, Mono<Authentication>> authenticationConverter = new ServerHttpBearerAuthenticationConverter();
	private ServerSecurityContextRepository securityContextRepository = NoOpServerSecurityContextRepository
			.getInstance();

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return this.requiresAuthenticationMatcher.matches(exchange).filter(matchResult -> matchResult.isMatch())
				.flatMap(matchResult -> this.authenticationConverter.apply(exchange))
				.switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
				.flatMap(token -> authenticate(exchange, chain, token));
	}

	private Mono<Void> authenticate(ServerWebExchange exchange, WebFilterChain chain, Authentication token) {
		WebFilterExchange webFilterExchange = new WebFilterExchange(exchange, chain);
		return this.authenticationManager.authenticate(token)
				.flatMap(authentication -> onAuthenticationSuccess(authentication, webFilterExchange));
	}

	private Mono<Void> onAuthenticationSuccess(Authentication authentication, WebFilterExchange webFilterExchange) {
		ServerWebExchange exchange = webFilterExchange.getExchange();
		SecurityContextImpl securityContext = new SecurityContextImpl();
		securityContext.setAuthentication(authentication);
		return this.securityContextRepository.save(exchange, securityContext)
				.then(this.authenticationSuccessHandler.onAuthenticationSuccess(webFilterExchange, authentication))
				.subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
	}
}
