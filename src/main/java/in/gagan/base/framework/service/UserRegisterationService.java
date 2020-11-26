package in.gagan.base.framework.service;

import in.gagan.base.framework.dto.UpdateUserDTO;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.exception.UsernameExistException;

/**
 * This Service interface defines the basic actions that a user can perform.
 * 
 * @author gaganthind
 *
 */
public interface UserRegisterationService {
	
	/**
	 * This method is used to register a new user in the system.
	 * 
	 * @param user - User DTO object with mandatory details
	 * @return String - username is returned
	 * @throws UsernameExistException - If User with email already present
	 */
	public String registerNewUser(UserDTO user) throws UsernameExistException;
	
	/**
	 * This method returns the user data from provided email.
	 * 
	 * @param email - User email address
	 * @return UserDTO - User DTO object with user details
	 */
	public UserDTO fetchUser(String email);
	
	/**
	 * This method is used to either update the record(if present) or insert the record.
	 * 
	 * @param user - User DTO object with user details to update
	 * @return UserDTO - User DTO object with user details
	 */
	public UserDTO updateUser(UpdateUserDTO user);

	/**
	 * This method is used to soft delete the user record using provided email.
	 * 
	 * @param email - Email address of user
	 */
	public void deleteUser(String email);
	
	/**
	 * This method is used to hard delete the user record using provided email.
	 * 
	 * @param email - Email address of user
	 */
	public void hardDeleteUser(String email);

	/**
	 * This method is used to confirm the user registration by accepting a token.
	 * 
	 * @param token - Random token generated for activating user
	 */
	public void confirmUserRegisteration(String token);

	/**
	 * This method is used to send account verification email with random token for user verification.
	 * 
	 * @param email - Email address of user
	 * @param token - Random token generated for activating user
	 */
	public void sendAccountVerificationEmail(String email, String token);

	/**
	 * This method is used to send account successfully verified email.
	 * 
	 * @param email - Email address of user
	 */
	public void sendSuccessfullAccountVerificationEmail(String email);

}
