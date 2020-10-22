package in.gagan.base.framework.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.component.PasswordSettings;
import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.dao.UserDAO;
import in.gagan.base.framework.entity.User;

@Transactional
@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {
	
	private final UserDAO userDAO;
	
	private final PasswordSettings passwordSettings;
	
	@Autowired
	public LoginAttemptServiceImpl(UserDAO userDAO, PasswordSettings passwordSettings) {
		this.userDAO = userDAO;
		this.passwordSettings = passwordSettings;
	}

	@Override
	public void loginFailed(Authentication auth) {
		
		// TODO Check if IP address tracking is needed
		// WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) auth.getDetails();
		// String ipAddress = webAuthenticationDetails.getRemoteAddress();
		
		String email = auth.getName();
		User user = this.userDAO.findUserByEmail(email).get();
		
		short failedLoginAttempts = user.getFailedLoginAttempts();
		short maxFailedLoginAttempts = this.passwordSettings.getMaxFailedLoginAttempts();
		short currentFailedLoginAttempts = failedLoginAttempts < maxFailedLoginAttempts ? (short)(failedLoginAttempts + 1) : maxFailedLoginAttempts;
		
		if (currentFailedLoginAttempts == maxFailedLoginAttempts) {
			user.setAccountLocked(ApplicationConstants.CHAR_Y);
		}
		
		user.setFailedLoginAttempts(currentFailedLoginAttempts);
		
		this.userDAO.save(user);
		
	}

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
