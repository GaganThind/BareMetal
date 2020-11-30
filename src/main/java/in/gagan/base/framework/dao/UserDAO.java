package in.gagan.base.framework.dao;

import java.util.List;
import java.util.Optional;

import in.gagan.base.framework.entity.User;

/**
 * This DAO interface provides the CRUD operations for USERS table.
 * 
 * @author gaganthind
 *
 */
public interface UserDAO extends BaseDAO<User, Long> {
	
	/**
	 * Method used to fetch a user by email.
	 * 
	 * @param email - email of user
	 * @return Optional<User> - User object
	 */
	public Optional<User> findUserByEmail(String email);
	
	/**
	 * Method used to unlock account of multiple users by email(s).
	 * 
	 * @param emails - email of user(s)
	 */
	public void unlockUsers(List<String> emails);
	
	/**
	 * Method used to soft delete multiple users by email(s).
	 * 
	 * @param emails - email of user(s)
	 */
	public void deleteUsers(List<String> emails);
	
	/**
	 * Method used to hard delete multiple users by email(s).
	 * 
	 * @param emails - email of user(s)
	 */
	public void hardDeleteUsers(List<String> emails);

}
