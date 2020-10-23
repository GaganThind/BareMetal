package in.gagan.base.framework.dao;

import java.util.Optional;

import in.gagan.base.framework.entity.User;

/**
 * This DAO interface provides the CRUD operations for USERS table.
 * 
 * @author gaganthind
 *
 */
public interface UserDAO extends BaseDAO<User, Long> {
	
	public Optional<User> findUserByEmail(String email);

}
