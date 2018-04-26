package br.com.empresa.produto.produtoauthserver.model.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document
public class Role implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2142754933159187046L;
	@Id
	private String id;

	@Override
	public String getAuthority() {
		return id;
	}

}
