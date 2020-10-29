package in.gagan.base.framework.component;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

@Component
@PropertySource(value = { "classpath:jwtToken.properties" })
public class JWTProps {
	
	@Value("${secretKey}")
	private String secretKey;
	
	@Value("${tokenExpirationInWeeks}")
	private int tokenExpirationInWeeks;

	@Value("${tokenPrefix}")
	private String tokenPrefix;

	public SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}

	public int getTokenExpirationInWeeks() {
		return tokenExpirationInWeeks;
	}

	public String getTokenPrefix() {
		return tokenPrefix;
	}
	
}
