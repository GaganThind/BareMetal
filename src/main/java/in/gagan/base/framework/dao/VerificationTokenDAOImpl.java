package in.gagan.base.framework.dao;

import java.util.List;
import java.util.Optional;

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

	@SuppressWarnings("unchecked")
	@Override
	public Optional<VerificationToken> fetchByToken(String token) {
		List<VerificationToken> tokens = 
				(List<VerificationToken>) entityManager.createQuery(ITERAL_FROM + getPersistentClass().getSimpleName() 
						+ " where token = '" + token + "'").getResultList();
		return !CollectionUtils.isEmpty(tokens) ? Optional.of(tokens.get(0)) : Optional.empty();
	}
	
}
