package br.com.empresa.produto.produtoauthserver.exceptions;

import org.springframework.security.core.AuthenticationException;
/**
 * Thrown when token cannot be found in the request header
 */
public class JwtTokenMissingException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2014889867180089797L;

	public JwtTokenMissingException(String msg) {
        super(msg);
    }
}