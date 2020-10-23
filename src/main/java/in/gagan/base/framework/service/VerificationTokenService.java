package in.gagan.base.framework.service;

import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.entity.VerificationToken;

/**
 * Verification Token service interface defines the basic CRUD operations on a VERIFICATION_TOKEN table.
 * 
 * @author gaganthind
 *
 */
public interface VerificationTokenService {
	
	public String generateTokenForUser(User user);
	
	public VerificationToken fetchByToken(String token);

}
