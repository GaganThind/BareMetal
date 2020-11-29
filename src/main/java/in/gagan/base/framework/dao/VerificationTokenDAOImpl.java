package in.gagan.base.framework.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import in.gagan.base.framework.entity.VerificationToken;

/**
 * This class provides CRUD operations on the VERIFICATION_TOKEN table using DAO pattern.
 * 
 * @author gaganthind
 *
 */
@Repository
public class VerificationTokenDAOImpl extends AbstractBaseDAO<VerificationToken, Long> implements VerificationTokenDAO {
	
	private final static String WHERE_TOKEN_CLAUSE = " where token = :token ";
	private final static String TOKEN = "token";

	/**
	 * Method used to fetch a token record.
	 * 
	 * @param token - token to fetch record
	 * @return Optional<VerificationToken> - VerificationToken record
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Optional<VerificationToken> fetchByToken(String token) {
		String selectQuery = new StringBuilder(LITERAL_FROM).append(getTableName()).append(WHERE_TOKEN_CLAUSE).toString();
		Query query = entityManager.createQuery(selectQuery);
		query.setParameter(TOKEN, token);
		List<VerificationToken> verificationTokens = (List<VerificationToken>) query.getResultList();
		
		return !CollectionUtils.isEmpty(verificationTokens) ? Optional.of(verificationTokens.get(0)) : Optional.empty();
	}
	
}
