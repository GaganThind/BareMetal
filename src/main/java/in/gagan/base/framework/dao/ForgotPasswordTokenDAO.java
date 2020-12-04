package in.gagan.base.framework.dao;

import java.util.Optional;

import in.gagan.base.framework.entity.ForgotPasswordToken;

/**
 * This DAO interface provides the CRUD operations for FORGOT_PASSWORD_TOKEN table.
 * 
 * @author gaganthind
 *
 */
public interface ForgotPasswordTokenDAO extends BaseDAO<ForgotPasswordToken, Long> {
	
	/**
	 * Method used to fetch a token record.
	 * 
	 * @param token - token to fetch record
	 * @return Optional<ForgotPasswordToken> - ForgotPasswordToken record
	 */
	public Optional<ForgotPasswordToken> fetchByToken(String token);

}
