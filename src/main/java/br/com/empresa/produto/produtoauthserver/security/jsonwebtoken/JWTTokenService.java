package br.com.empresa.produto.produtoauthserver.security.jsonwebtoken;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class JWTTokenService {

	public String generateToken(String name, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().subject(name).issuer("ramon.io")
				.expirationTime(new Date(new Date().getTime() + 60 * 1000))
				.claim("auths", authorities.parallelStream().map(auth -> (GrantedAuthority) auth)
						.map(a -> a.getAuthority()).collect(Collectors.joining(",")))
				.build();

		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

		try {
			signedJWT.sign(new JWTSignerProvider().getSigner());
		} catch (JOSEException e) {
			e.printStackTrace();
		}

		return signedJWT.serialize();
	}

}
