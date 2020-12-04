package in.gagan.base.framework.service;

import in.gagan.base.framework.entity.ForgotPasswordToken;
import in.gagan.base.framework.entity.User;

/**
 * This Service interface defines the basic CRUD operations on a FORGOT_PASSWORD_TOKEN table.
 * 
 * @author gaganthind
 *
 */
public interface ForgotPasswordTokenService {
	
	/**
	 * This method generates random token for account verification.
	 * 
	 * @param User - User entity object
	 * @return String - Random password token
	 */
	public String generateForgotPasswordToken(User user);
	
	/**
	 * This method is used to fetch the provided token in the system.
	 * 
	 * @param token - Random verification token already sent in email
	 * @return ForgotPasswordToken - ForgotPasswordToken record from database
	 */
	public ForgotPasswordToken fetchByToken(String token);
	
	/**
	 * This method is used to delete the token once successfully verified.
	 * 
	 * @param forgotPasswordToken - ForgotPasswordToken record from database
	 */
	void deleteToken(ForgotPasswordToken forgotPasswordToken);

}
