package in.gagan.base.framework.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * This class provides the verification token properties 
 * and the data is loaded from the properties file (verificationToken.properties)
 * 
 * @author gaganthind
 *
 */
@Component
@PropertySource(value = "classpath:verificationToken.properties")
public class VerificationTokenProps {
	
	@Value("${token.expiry}")
	private int VERIFICATION_TOKEN_EXPIRY_DAYS;
	
	public int getVerificationTokenExpiryDays() {
		return this.VERIFICATION_TOKEN_EXPIRY_DAYS;
	}
	
}
