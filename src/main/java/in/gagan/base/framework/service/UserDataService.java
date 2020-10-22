package in.gagan.base.framework.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import in.gagan.base.framework.entity.User;

/**
 * User Data service interface defines the basic CRUD operations on a User table.
 * 
 * @author gaganthind
 *
 */
public interface UserDataService {
	
	public User fetchUserByEmail(String email) throws UsernameNotFoundException;
	
	public void saveUser(User user);
	
	public void updateUser(User user);
	
	public void deleteUser(String email);
	
	public void hardDeleteUser(String email);
	
	public boolean isUserPresent(String email);

}
