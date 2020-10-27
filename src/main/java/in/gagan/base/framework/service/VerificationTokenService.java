package in.gagan.base.framework.service;

import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.entity.VerificationToken;

/**
 * This Service interface defines the basic CRUD operations on a VERIFICATION_TOKEN table.
 * 
 * @author gaganthind
 *
 */
public interface VerificationTokenService {
	
	/**
	 * This method generates random token for account verification.
	 * 
	 * @param user - User record to insert
	 * @return String - Random verification token
	 */
	public String generateTokenForUser(User user);
	
	/**
	 * This method is used to fetch the provided token in the system.
	 * 
	 * @param token - Random verification token already sent in email
	 * @return VerificationToken - Verification_Token record from database
	 */
	public VerificationToken fetchByToken(String token);

}
