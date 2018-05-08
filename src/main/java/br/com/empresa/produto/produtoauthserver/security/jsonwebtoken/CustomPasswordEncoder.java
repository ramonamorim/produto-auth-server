package br.com.empresa.produto.produtoauthserver.security.jsonwebtoken;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence password) {
		SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);

		return BCrypt.hashpw(password.toString(), BCrypt.gensalt(14, random));
	}

	@Override
	public boolean matches(CharSequence password, String encodedPassword) {
        String decodedString  = new String(Base64.getDecoder().decode(password.toString()));
        return BCrypt.checkpw(decodedString, encodedPassword);
    }

}
