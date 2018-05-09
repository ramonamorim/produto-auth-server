package br.com.empresa.produto.produtoauthserver.security.jsonwebtoken;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5057464106549482592L;
	private String token;

	public JwtAuthenticationToken(String token, String username, Collection<? extends GrantedAuthority> authorities) {
		super(username, null, authorities);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return null;
	}
}
