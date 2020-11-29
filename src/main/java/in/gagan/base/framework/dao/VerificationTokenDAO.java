package in.gagan.base.framework.dao;

import java.util.Optional;

import in.gagan.base.framework.entity.VerificationToken;

/**
 * This DAO interface provides the CRUD operations for VERIFICATION_TOKEN table.
 * 
 * @author gaganthind
 *
 */
public interface VerificationTokenDAO  extends BaseDAO<VerificationToken, Long> {
	
	/**
	 * Method used to fetch a token record.
	 * 
	 * @param token - token to fetch record
	 * @return Optional<VerificationToken> - VerificationToken record
	 */
	public Optional<VerificationToken> fetchByToken(String token);
	
}
