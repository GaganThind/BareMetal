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

	@SuppressWarnings("unchecked")
	@Override
	public Optional<VerificationToken> fetchByToken(String token) {
		Query query = entityManager.createQuery("from " + getPersistentClass().getSimpleName() + " where token = :token ");
		query.setParameter("token", token);
		List<VerificationToken> verificationTokens = (List<VerificationToken>) query.getResultList();
		
		return !CollectionUtils.isEmpty(verificationTokens) ? Optional.of(verificationTokens.get(0)) : Optional.empty();
	}
	
}
