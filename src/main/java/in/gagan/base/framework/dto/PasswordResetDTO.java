package in.gagan.base.framework.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import in.gagan.base.framework.validator.Password;
import in.gagan.base.framework.validator.PasswordMatches;

@PasswordMatches
public class PasswordResetDTO implements Serializable {
	
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -7115804187656427800L;

	@Password
	@NotNull(message = "{message.registration.password}")
	@Size(min = 8, message = "{message.registration.password}")
	private String password;
	
	@NotNull(message = "{message.registration.password}")
	@Size(min = 1, message = "{message.registration.password}")
	private String matchingPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	} 
	
}
