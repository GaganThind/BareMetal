package in.gagan.base.framework.service;

import in.gagan.base.framework.dto.PasswordResetDTO;

/**
 * This Service interface defines the basic actions that is used to manage password actions.
 * 
 * @author gaganthind
 *
 */
public interface PasswordManagerService {
	
	/**
	 * This method is used to reset password of existing user.
	 * 
	 * @param passwordResetDTO - Object to transfer password and confirm password
	 * @param  email - User email address
	 * @throws IllegalAccessException 
	 */
	public void resetPassword(PasswordResetDTO passwordResetDTO, String email) throws IllegalAccessException;
	
	/**
	 * This method is used to reset password in case a user has forgotten the password.
	 * 
	 * @param passwordResetDTO - Object to transfer password and confirm password
	 * @param token - Unique token string 
	 */
	public void forgotPassword(PasswordResetDTO passwordResetDTO, String token);

	/**
	 * This method is used to send password successfully re-setted email.
	 * 
	 * @param email - Email address of user
	 */
	public void sendPasswordResetSuccessfulEmail(String email);

}
