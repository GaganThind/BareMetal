package in.gagan.base.framework.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import in.gagan.base.framework.entity.UserSecurity;

/**
 * This class is used to CRUD operations on the UserSecurity table using DAO pattern
 * 
 * @author gaganthind
 *
 */
@Repository
public class UserSecurityDAO extends AbstractBaseDAO<UserSecurity, Long> {
	
	/**
	 * Find all user
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Optional<Iterable<UserSecurity>> findAll() {
		return Optional.ofNullable(entityManager.createNamedQuery("HQL_GET_ALL_USER_SECURITY").getResultList());
	}
	
}
