package in.gagan.base.framework.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import in.gagan.base.framework.entity.User;

/**
 * This Service interface provides the basic CRUD operations on a User table from user account perspective.
 * 
 * @author gaganthind
 *
 */
public interface UserDataService {
	
	/**
	 * This method is used to fetch user data based on provided email.
	 * 
	 * @param email - Email address of the user
	 * @return User - User object from the database
	 * @throws UsernameNotFoundException - If user with the provided email doesn't exist 
	 */
	public User fetchUserByEmail(String email) throws UsernameNotFoundException;
	
	/**
	 * This method is used to save the User record.
	 * 
	 * @param user - user record
	 */
	public void saveUser(User user);
	
	/**
	 * This method is used to update the User record.
	 * 
	 * @param user - user record
	 */
	public void updateUser(User user);
	
	/**
	 * This method is used to soft delete the User record.
	 * 
	 * @param email - email address of user
	 */
	public void deleteUser(String email);
	
	/**
	 * This method is used to check if a User record exists with the provided email.
	 * 
	 * @param email - email address of user
	 * @return boolean - if user is present in the system
	 */
	public boolean isUserPresent(String email);

}
