package in.gagan.base.framework.service;

import java.util.List;

import in.gagan.base.framework.dto.UserDTO;

/**
 * This Service interface provides the operations for Admin functionality.
 * 
 * @author gaganthind
 *
 */
public interface AdminService {
	
	/**
	 * Method used to create Admin account on application startup.
	 */
	public void createAdminUser();
	
	/**
	 * Method used to fetch all the users of the application.
	 * 
	 * @return List<UserDTO> - list of all users
	 */
	public List<UserDTO> fetchAllUsers();

}
