package in.gagan.base.framework.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * This class provides the password related properties 
 * and the data is loaded from the properties file (password.properties)
 * 
 * @author gaganthind
 *
 */
@Component
@PropertySource(value = { "classpath:password.properties" })
public class PasswordProps {
	
	@Value("${attempts.maxfailed}")
	private short MAX_FAILED_LOGIN_ATTEMPTS;
	
	@Value("${expiry}")
	private int PASSWORD_EXPIRE_DAYS;
	
	public short getMaxFailedLoginAttempts() {
		return this.MAX_FAILED_LOGIN_ATTEMPTS;
	}
	
	public int getPasswordExpireDays() {
		return this.PASSWORD_EXPIRE_DAYS;
	}
	
}
