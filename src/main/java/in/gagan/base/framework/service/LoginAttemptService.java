package in.gagan.base.framework.service;

import org.springframework.security.core.Authentication;

/**
 * This Service interface provides the operations for Login Attempt functionality.
 * 
 * @author gaganthind
 *
 */
public interface LoginAttemptService {
	
	/**
	 * This method is used to perform some operation when user authentication fails.
	 * 
	 * @param auth - Authentication object containing user info
	 */
	public void loginFailed(Authentication auth);

	/**
	 * This method is used to perform some operation when user authentication succeeds.
	 * 
	 * @param auth - Authentication object containing user info
	 */
	public void loginSucceeded(Authentication auth);

}
