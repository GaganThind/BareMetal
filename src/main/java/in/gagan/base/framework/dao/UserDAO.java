package in.gagan.base.framework.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import in.gagan.base.framework.entity.User;

/**
 * This class is used to CRUD operations on the User table using DAO pattern
 * 
 * @author gaganthind
 *
 */
@Repository
public class UserDAO extends AbstractBaseDAO<User, Long> {
	
	@SuppressWarnings("unchecked")
	public Optional<User> findUserByEmail(String email) {
		List<User> usersBasedOnEmail = 
				(List<User>) entityManager.createQuery(ITERAL_FROM + getPersistentClass().getSimpleName() + " where email = '" + email + "'").getResultList();
		return !CollectionUtils.isEmpty(usersBasedOnEmail) ? Optional.of(usersBasedOnEmail.get(0)) : Optional.empty();
	}
	
	
	
}
