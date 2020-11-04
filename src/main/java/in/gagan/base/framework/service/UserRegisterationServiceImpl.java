package in.gagan.base.framework.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.entity.VerificationToken;
import in.gagan.base.framework.exception.UsernameExistException;
import in.gagan.base.framework.util.UserHelperUtil;

/**
 * This class provides the implementation of UserRegisterationService interface and provides operations for User Registeration functionality.
 * 
 * @author gaganthind
 *
 */
@Transactional
@Service
public class UserRegisterationServiceImpl implements UserRegisterationService {
	
	private final UserDataService userDataSvc;
	
	private final VerificationTokenService verificationTokenSvc;
	
	private final EmailService emailSvc;
	
	@Autowired
	public UserRegisterationServiceImpl(UserDataService userDataSvc, VerificationTokenService verificationTokenSvc, EmailService emailSvc) {
		this.userDataSvc = userDataSvc;
		this.verificationTokenSvc = verificationTokenSvc;
		this.emailSvc = emailSvc;
	}
	
	/**
	 * This method is used to register a new user in the system.
	 * 
	 * @param user - User DTO object with mandatory details
	 * @return String - username is returned
	 * @throws UsernameExistException - If User with email already present
	 */
	@Override
	public String registerNewUser(UserDTO user) throws UsernameExistException {
		if (this.userDataSvc.isUserPresent(user.getEmail())) {
			throw new UsernameExistException(user.getEmail());
		}
		
		insertUser(user);
		
		return user.getUsername();
	}
	
	/**
	 * This method returns the user data from provided email.
	 * 
	 * @param email - User email address
	 * @return UserDTO - User DTO object with user details
	 */
	@Override
	public UserDTO fetchUser(String email) {
		UserDTO userDTO = new UserDTO();
		User user = this.userDataSvc.fetchUserByEmail(email.toLowerCase());
		UserHelperUtil.convertUserToUserDTO(user, userDTO);
		return userDTO;
	}
	
	/**
	 * This method is used to either update the record(if present) or insert the record.
	 * 
	 * @param user - User DTO object with user details to update
	 * @return UserDTO - User DTO object with user details
	 */
	@Override
	public UserDTO updateOrCreateUser(UserDTO user) {
		if (this.userDataSvc.isUserPresent(user.getEmail())) {
			updateUser(user);
		} else {
			insertUser(user);
		}
		
		return user;
	}
	
	/**
	 * This method is used to soft delete the user record using provided email.
	 * 
	 * @param email - Email address of user
	 */
	@Override
	public void deleteUser(String email) {
		this.userDataSvc.deleteUser(email.toLowerCase());
	}
	
	/**
	 * This method is used to hard delete the user record using provided email.
	 * 
	 * @param email - Email address of user
	 */
	@Override
	public void hardDeleteUser(String email) {
		this.userDataSvc.hardDeleteUser(email.toLowerCase());
	}
	
	/**
	 * This method is used to register a new user in the system.
	 * 
	 * @param userDTO - User DTO object with user details to insert
	 */
	private void insertUser(UserDTO userDTO) {
		User user = new User();
		UserHelperUtil.convertUserDTOToUser(userDTO, user);
		this.userDataSvc.saveUser(user);
		
		// Generate random verification token
		String token = this.verificationTokenSvc.generateTokenForUser(user);
		
		// Send verification email
		sendAccountVerificationEmail(user.getEmail(), token);
	}
	
	/**
	 * This method is used to confirm the user registration by accepting a token.
	 * 
	 * @param token - Random token generated for activating user
	 */
	@Override
	public void confirmUserRegisteration(String token) {
		VerificationToken verificationToken = this.verificationTokenSvc.fetchByToken(token);
		User user = verificationToken.getUser();
		user.setActiveSw(ApplicationConstants.CHAR_Y);
		this.userDataSvc.saveUser(user);
		
		// Remove the token from database
		this.verificationTokenSvc.deleteToken(verificationToken);
		
		// Send confirmation email
		sendSuccessfullAccountVerificationEmail(user.getEmail());
	}
	
	/**
	 * This method is used to send account verification email with random token for user verification.
	 * 
	 * @param email - Email address of user
	 * @param token - Random token generated for activating user
	 */
	@Override
	public void sendAccountVerificationEmail(String email, String token) {
		String subject = "Account Verification email";
		String message = "";
		this.emailSvc.sendEmail(email, subject, message);
	}

	/**
	 * This method is used to send account successfully verified email.
	 * 
	 * @param email - Email address of user
	 */
	@Override
	public void sendSuccessfullAccountVerificationEmail(String email) {
		String subject = "Account Successfully Verified";
		String message = "";
		this.emailSvc.sendEmail(email, subject, message);
	}
	
	/**
	 * This method is used to update the record.
	 * 
	 * @param userDTO - User DTO object with user details to update
	 */
	private void updateUser(UserDTO userDTO) {
		User user = new User();
		UserHelperUtil.convertUserDTOToUser(userDTO, user);
		this.userDataSvc.updateUser(user);
	}

}
