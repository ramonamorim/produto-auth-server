package br.com.empresa.produto.produtoauthserver.model.user;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2142754933159187046L;

	private String id;

	@Override
	public String getAuthority() {
		return id;
	}

}
