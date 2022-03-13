/*
 * SpringBoot_Hibernate_Framework
 * 
 * Copyright (C) 2020-2022  Gagan Thind

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package in.gagan.base.framework.service.user;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.component.PasswordProps;
import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.dao.user.UserDAO;
import in.gagan.base.framework.entity.user.User;
import in.gagan.base.framework.util.ExceptionHelperUtil;

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
		User user = this.userDAO.findUserByEmail(email).orElseThrow(() -> ExceptionHelperUtil.throwUserNotFound(email));
		
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
