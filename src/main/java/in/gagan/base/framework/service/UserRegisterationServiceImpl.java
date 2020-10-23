package in.gagan.base.framework.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.entity.VerificationToken;
import in.gagan.base.framework.exception.UsernameExistException;
import in.gagan.base.framework.util.UserDataValidator;
import in.gagan.base.framework.util.UserHelperUtil;

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
	
	@Override
	public String registerNewUser(UserDTO user) throws UsernameExistException {
		UserDataValidator.validateUserDTO(user);
		
		if (this.userDataSvc.isUserPresent(user.getEmail())) {
			throw new UsernameExistException(user.getEmail());
		}
		
		insertUser(user);
		
		return user.getUsername();
	}
	
	@Override
	public UserDTO fetchUser(String email) {
		UserDataValidator.validateEmail(email);
		UserDTO userDTO = new UserDTO();
		User user = this.userDataSvc.fetchUserByEmail(email);
		UserHelperUtil.convertUserToUserDTO(user, userDTO);
		return userDTO;
	}
	
	@Override
	public UserDTO updateOrCreateUser(UserDTO user) {
		if (this.userDataSvc.isUserPresent(user.getEmail())) {
			UserDataValidator.validateUserDTOforUpdate(user);
			updateUser(user);
		} else {
			UserDataValidator.validateUserDTO(user);
			insertUser(user);
		}
		
		return user;
	}
	
	@Override
	public void deleteUser(String email) {
		UserDataValidator.validateEmail(email);
		this.userDataSvc.deleteUser(email);
	}
	
	@Override
	public void hardDeleteUser(String email) {
		UserDataValidator.validateEmail(email);
		this.userDataSvc.hardDeleteUser(email);
	}
	
	private void insertUser(UserDTO userDTO) {
		User user = new User();
		UserHelperUtil.convertUserDTOToUser(userDTO, user);
		this.userDataSvc.saveUser(user);
		
		// Generate random verification token
		String token = this.verificationTokenSvc.generateTokenForUser(user);
		
		// Send verification email
		sendAccountVerificationEmail(user.getEmail(), token);
	}
	
	@Override
	public void confirmUserRegisteration(String token) {
		VerificationToken verificationToken = this.verificationTokenSvc.fetchByToken(token);
		User user = verificationToken.getUser();
		user.setActiveSw(ApplicationConstants.CHAR_Y);
		this.userDataSvc.saveUser(user);
		
		// Send confirmation email
		sendSuccessfullAccountVerificationEmail(user.getEmail());
	}
	
	@Override
	public void sendAccountVerificationEmail(String email, String token) {
		String subject = "Account Verification email";
		String message = "";
		this.emailSvc.sendEmail(email, subject, message);
	}

	@Override
	public void sendSuccessfullAccountVerificationEmail(String email) {
		String subject = "Account Successfully Verified";
		String message = "";
		this.emailSvc.sendEmail(email, subject, message);
	}
	
	private void updateUser(UserDTO userDTO) {
		User user = new User();
		UserHelperUtil.convertUserDTOToUser(userDTO, user);
		this.userDataSvc.updateUser(user);
	}

}
