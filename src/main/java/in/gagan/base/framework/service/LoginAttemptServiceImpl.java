package in.gagan.base.framework.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.component.PasswordProps;
import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.dao.UserDAO;
import in.gagan.base.framework.entity.User;

/**
 * This class provides the implementation of LoginAttemptService interface and provides operations for Login Attempt functionality.
 * 
 * @author gaganthind
 *
 */
@Transactional
@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {
	
	private final UserDAO userDAO;
	
	private final PasswordProps passwordProps;
	
	@Autowired
	public LoginAttemptServiceImpl(UserDAO userDAO, PasswordProps passwordProps) {
		this.userDAO = userDAO;
		this.passwordProps = passwordProps;
	}

	/**
	 * This method is used to perform some operation when user authentication fails.
	 * 
	 * @param auth - Authentication object containing user info
	 */
	@Override
	public void loginFailed(Authentication auth) {
		
		// TODO Check if IP address tracking is needed
		// WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) auth.getDetails();
		// String ipAddress = webAuthenticationDetails.getRemoteAddress();
		
		String email = auth.getName();
		User user = this.userDAO.findUserByEmail(email).get();
		
		short failedLoginAttempts = user.getFailedLoginAttempts();
		short maxFailedLoginAttempts = this.passwordProps.getMaxFailedLoginAttempts();
		short currentFailedLoginAttempts = failedLoginAttempts < maxFailedLoginAttempts ? (short)(failedLoginAttempts + 1) : maxFailedLoginAttempts;
		
		if (currentFailedLoginAttempts == maxFailedLoginAttempts) {
			user.setAccountLocked(ApplicationConstants.CHAR_Y);
		}
		
		user.setFailedLoginAttempts(currentFailedLoginAttempts);
		
		this.userDAO.save(user);
		
	}

	/**
	 * This method is used to perform some operation when user authentication succeeds.
	 * 
	 * @param auth - Authentication object containing user info
	 */
	@Override
	public void loginSucceeded(Authentication auth) {
		// TODO Check if IP address tracking is needed
		// WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) auth.getDetails();
		// String ipAddress = webAuthenticationDetails.getRemoteAddress();
		
		String email = auth.getName();
		User user = this.userDAO.findUserByEmail(email).get();
		
		short failedLoginAttempts = user.getFailedLoginAttempts();
		
		if (0 != failedLoginAttempts) {
			user.setFailedLoginAttempts((short) 0);
			user.setAccountLocked(ApplicationConstants.CHAR_N);
			this.userDAO.save(user);
		}
		
	}

}
