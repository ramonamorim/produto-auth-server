package br.com.empresa.produto.produtoauthserver.models.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document
public class Role implements GrantedAuthority {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4358600995401335013L;
	@Id
	private String id;

	@Override
	public String getAuthority() {
		return id;
	}
}
