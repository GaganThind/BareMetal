package in.gagan.base.framework.service;

import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.exception.UsernameExistException;

/**
 * User Registration service interface defines the basic actions that a user can perform.
 * 
 * @author thindgagan
 *
 */
public interface UserRegisterationService {
	
	public String registerNewUser(UserDTO user) throws UsernameExistException;
	
	public UserDTO fetchUser(String email);
	
	public UserDTO updateOrCreateUser(UserDTO user);
	
	public void deleteUser(String email);
	
	public void hardDeleteUser(String email);

	public void createAdminUser();

}
