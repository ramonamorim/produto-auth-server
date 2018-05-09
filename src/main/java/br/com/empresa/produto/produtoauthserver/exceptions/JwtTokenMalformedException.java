package br.com.empresa.produto.produtoauthserver.exceptions;

import org.springframework.security.core.AuthenticationException;
/**
 * Thrown when token cannot be parsed
 */
public class JwtTokenMalformedException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2391965960382444599L;

	public JwtTokenMalformedException(String msg) {
        super(msg);
    }

    public JwtTokenMalformedException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}