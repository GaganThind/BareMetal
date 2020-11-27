package in.gagan.base.framework.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import in.gagan.base.framework.entity.User;

/**
 * This class provides CRUD operations on the USERS table using DAO pattern.
 * 
 * @author gaganthind
 *
 */
@Repository
public class UserDAOImpl extends AbstractBaseDAO<User, Long> implements UserDAO {
	
	/**
	 * Find the user by email.
	 * 
	 * @return Optional<User> - User object
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Optional<User> findUserByEmail(String email) {
		Query query = entityManager.createQuery("from " + getPersistentClass().getSimpleName() + " where email = :email ");
		query.setParameter("email", email);
		List<User> users = (List<User>) query.getResultList();
		return !CollectionUtils.isEmpty(users) ? Optional.of(users.get(0)) : Optional.empty();
	}
	
	
	
}
