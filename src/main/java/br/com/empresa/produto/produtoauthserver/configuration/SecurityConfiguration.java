package br.com.empresa.produto.produtoauthserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

	
	@Bean
	SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity httpSecurity) {
		
		httpSecurity.httpBasic().disable();
		httpSecurity.formLogin().disable();
		httpSecurity.csrf().disable();
		httpSecurity.logout().disable();
		
		
//		httpSecurity.authenticationManager(au)
		
//			httpSecurity.httpBasic().disable();
//			httpSecurity.formLogin().disable();
//			httpSecurity.csrf().disable();
//			httpSecurity.logout().disable();
//			
//			httpSecurity
//	        .authorizeExchange()
//	        .pathMatchers("/login", "/")
//	        .permitAll()
//	        .and()
//	        .addFilterAt(authenticationJWT, SecurityWebFiltersOrder.FIRST)
//	        .authorizeExchange()
//	        .pathMatchers("/api/**")
//	        .authenticated()
//	        .and()
//	        .addFilterAt(new JWTAuthorizationWebFilter(), SecurityWebFiltersOrder.HTTP_BASIC);
		
		
		return null;
	}
	
	
	
	
}