package br.com.empresa.produto.produtoauthserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.util.Assert;

import com.transempiric.webfluxTemplate.config.security.CustomAuthenticationConverter;
import com.transempiric.webfluxTemplate.service.CustomReactiveUserDetailsService;

import br.com.empresa.produto.produtoauthserver.security.jsonwebtoken.JWTAuthorizationWebFilter;
import br.com.empresa.produto.produtoauthserver.security.jsonwebtoken.JwtAuthenticationEntryPoint;
import br.com.empresa.produto.produtoauthserver.security.jsonwebtoken.WebFilterChainServerJWTAuthenticationSuccessHandler;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

	private ServerAuthenticationEntryPoint entryPoint = new JwtAuthenticationEntryPoint();

	// private final CustomReactiveUserDetailsService userDetailsService;
	// private final CustomAuthenticationConverter customAuthenticationConverter;
	public SecurityConfiguration(CustomReactiveUserDetailsService userDetailsService,
			CustomAuthenticationConverter customAuthenticationConverter) {
		Assert.notNull(userDetailsService, "userDetailsService cannot be null");
		Assert.notNull(customAuthenticationConverter, "customAuthenticationConverter cannot be null");
		this.userDetailsService = userDetailsService;
		this.customAuthenticationConverter = customAuthenticationConverter;
	}

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