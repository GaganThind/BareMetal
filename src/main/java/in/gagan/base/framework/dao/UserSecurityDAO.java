package in.gagan.base.framework.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import in.gagan.base.framework.model.UserSecurity;

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
	public List<UserSecurity> findAll() {
		return entityManager.createNamedQuery("HQL_GET_ALL_USER_SECURITY").getResultList();
	}
	
}
