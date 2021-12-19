package in.gagan.base.framework.component;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import in.gagan.base.framework.constant.ApplicationConstants;

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
	
	@Value("${server.configured.send.email}")
	private String TOKEN_SERVER_CONFIGURED_TO_SEND_EMAIL;
	
	public boolean isServerConfiguredToSendEmail() {
		return ApplicationConstants.STRING_TRUE.equals(this.TOKEN_SERVER_CONFIGURED_TO_SEND_EMAIL);
	}
	
	/**
	 * Method to get the expiry date for newly created token from current time.
	 * 
	 * @return
	 */
	public LocalDateTime getExpiryTimeFromNow() {
		return LocalDateTime.now().plusDays(getVerificationTokenExpiryDays());
	}
	
}
