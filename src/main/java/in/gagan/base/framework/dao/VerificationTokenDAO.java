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
	
	public Optional<VerificationToken> fetchByToken(String token);
	
}
