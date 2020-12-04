package in.gagan.base.framework.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import in.gagan.base.framework.entity.ForgotPasswordToken;

/**
 * This class provides CRUD operations on the FORGOT_PASSWORD_TOKEN table using DAO pattern.
 * 
 * @author gaganthind
 *
 */
@Repository
public class ForgotPasswordTokenDAOImpl extends AbstractBaseDAO<ForgotPasswordToken, Long> implements ForgotPasswordTokenDAO {
	
	private final static String WHERE_TOKEN_CLAUSE = " where token = :token ";
	private final static String TOKEN = "token";

	/**
	 * Method used to fetch a token record.
	 * 
	 * @param token - token to fetch record
	 * @return Optional<ForgotPasswordToken> - ForgotPasswordToken record
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Optional<ForgotPasswordToken> fetchByToken(String token) {
		String selectQuery = new StringBuilder(LITERAL_FROM).append(getTableName()).append(WHERE_TOKEN_CLAUSE).toString();
		Query query = entityManager.createQuery(selectQuery);
		query.setParameter(TOKEN, token);
		List<ForgotPasswordToken> forgotPasswordTokens = (List<ForgotPasswordToken>) query.getResultList();
		
		return !CollectionUtils.isEmpty(forgotPasswordTokens) ? Optional.of(forgotPasswordTokens.get(0)) : Optional.empty();
	}

}
