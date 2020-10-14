package in.gagan.base.framework.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasswordSettings {
	
	@Value("${application.user.password.attempts.maxfailed}")
	private short MAX_FAILED_LOGIN_ATTEMPTS;
	
	@Value("${application.user.password.expiry}")
	private int PASSWORD_EXPIRE_DAYS;
	
	public short getMaxFailedLoginAttempts() {
		return this.MAX_FAILED_LOGIN_ATTEMPTS;
	}
	
	public int getPasswordExpireDays() {
		return this.PASSWORD_EXPIRE_DAYS;
	}
	
}
