package br.com.empresa.produto.produtoauthserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

import br.com.empresa.produto.produtoauthserver.security.jsonwebtoken.JWTAuthorizationWebFilter;
import br.com.empresa.produto.produtoauthserver.security.jsonwebtoken.WebFilterChainServerJWTAuthenticationSuccessHandler;

@Configuration
public class SecurityConfiguration {

	@SuppressWarnings("deprecation")
	@Bean
	public MapReactiveUserDetailsService userDetailsRepository() {
		UserDetails user = User.withDefaultPasswordEncoder().username("user").password("user").roles("USER", "ADMIN")
				.build();
		return new MapReactiveUserDetailsService(user);
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity httpSecurity) {
		AuthenticationWebFilter authenticationJWT;

		authenticationJWT = new AuthenticationWebFilter(
				new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsRepository()));
		authenticationJWT.setAuthenticationSuccessHandler(new WebFilterChainServerJWTAuthenticationSuccessHandler());

		httpSecurity.authorizeExchange().pathMatchers("/login", "/").permitAll().and()
				.addFilterAt(authenticationJWT, SecurityWebFiltersOrder.FIRST).authorizeExchange()
				.pathMatchers("/api/**").authenticated().and()
				.addFilterAt(new JWTAuthorizationWebFilter(), SecurityWebFiltersOrder.HTTP_BASIC);

		return httpSecurity.build();
	}

}