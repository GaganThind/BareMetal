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

package in.gagan.base.framework.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.dto.PasswordResetDTO;
import in.gagan.base.framework.entity.ForgotPasswordToken;
import in.gagan.base.framework.entity.User;

/**
 * This class provides the implementation of PasswordManagerService interface and provides operations for Password reset functionality.
 * 
 * @author gaganthind
 *
 */
@Transactional
@Service
public class PasswordManagerServiceImpl implements PasswordManagerService {
	
	private final UserDataService userDataSvc;
	
	private final ForgotPasswordTokenService forgotPasswordTokenService;
	
	private final EmailService emailSvc;
	
	@Autowired
	public PasswordManagerServiceImpl(UserDataService userDataSvc, ForgotPasswordTokenService forgotPasswordTokenService, EmailService emailSvc) {
		this.userDataSvc = userDataSvc;
		this.forgotPasswordTokenService = forgotPasswordTokenService;
		this.emailSvc = emailSvc;
	}

	/**
	 * This method is used to reset password of existing user.
	 * 
	 * @param passwordResetDTO - Object to transfer password and confirm password
	 * @param  email - User email address
	 */
	@Override
	public void resetPassword(PasswordResetDTO passwordResetDTO, String email) {
		User user = this.userDataSvc.fetchUserByEmail(email);
		
		user.setPassword(passwordResetDTO.getPassword());
		user.setPasswordExpireDate(null); // Let the system determine itself to add new password expiry date.
		
		this.userDataSvc.saveUser(user);
		
		// Send password successfully reset email.
		sendPasswordResetSuccessfulEmail(email);
	}
	
	/**
	 * This method is used to generate forgot password token for existing user.
	 * 
	 * @param email - User email address
	 */
	@Override
	public void generateForgotPasswordToken(String email) {
		User user = this.userDataSvc.fetchUserByEmail(email);
		String token = this.forgotPasswordTokenService.generateForgotPasswordToken(user);
		
		sendPasswordResetEmail(email, token);
	}

	/**
	 * This method is used to reset password in case a user has forgotten the password.
	 * 
	 * @param passwordResetDTO - Object to transfer password and confirm password
	 * @param token - Unique token string 
	 */
	@Override
	public void forgotPassword(PasswordResetDTO passwordResetDTO, String token) {
		ForgotPasswordToken forgotPasswordToken = this.forgotPasswordTokenService.fetchByToken(token);
		User user = forgotPasswordToken.getUser();
		
		user.setPassword(passwordResetDTO.getPassword());
		user.setPasswordExpireDate(null); // Let the system determine itself to add new password expiry date.
		
		this.userDataSvc.saveUser(user);
		
		// Remove the token from database
		this.forgotPasswordTokenService.deleteToken(forgotPasswordToken);
		
		// Send password successfully reset email.
		sendPasswordResetSuccessfulEmail(user.getEmail());

	}
	
	/**
	 * This method is used to send password successfully reset email.
	 * 
	 * @param email - Email address of user
	 */
	@Override
	public void sendPasswordResetSuccessfulEmail(String email) {
		String subject = "Password Successfully reset email";
		String message = "";
		this.emailSvc.sendEmail(email, subject, message);
	}
	
	/**
	 * This method is used to send password reset email to user with token.
	 * 
	 * @param email - Email address of user
	 * @param token - Password reset token
	 */
	@Override
	public void sendPasswordResetEmail(String email, String token) {
		String subject = "Password reset request email";
		String message = "" + token;
		this.emailSvc.sendEmail(email, subject, message);
	}

}
