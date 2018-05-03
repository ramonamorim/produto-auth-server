package br.com.empresa.produto.produtoauthserver.security.jsonwebtoken;

import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;

import lombok.Getter;

@Getter
public class JWTSignerProvider {

	private JWSSigner signer;

	public JWTSignerProvider() {
		JWSSigner init;
		try {
			init = new MACSigner(JWTSecrets.DEFAULT_SECRET);
		} catch (KeyLengthException e) {
			init = null;
		}
		this.signer = init;
	}

}
