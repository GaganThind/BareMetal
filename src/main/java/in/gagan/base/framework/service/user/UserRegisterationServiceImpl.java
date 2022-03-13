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

import in.gagan.base.framework.service.EmailService;
import in.gagan.base.framework.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.dto.UpdateUserDTO;
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
	public String registerNewUser(User user) throws UsernameExistException {
		if (this.userDataSvc.isUserPresent(user.getEmail())) {
			throw new UsernameExistException(user.getEmail());
		}

		this.userDataSvc.saveUser(user);

		// Generate random verification token
		String token = this.verificationTokenSvc.generateTokenForUser(user);

		// Send verification email
		sendAccountVerificationEmail(user.getEmail(), token);
		
		return UserHelperUtil.getUsername(user);
	}
	
	/**
	 * This method returns the user data from provided email.
	 * 
	 * @param email - User email address
	 * @return UserDTO - User DTO object with user details
	 */
	@Override
	public UserDTO fetchUser(String email) {
		User user = this.userDataSvc.fetchUserByEmail(email.toLowerCase());
		return DTOMapper.convertUserToUserDTO(user);
	}
	
	/**
	 * This method is used to either update the record(if present) or insert the record.
	 * 
	 * @param updateUserDTO - User DTO object with user details to update
	 * @return UserDTO - User DTO object with user details
	 */
	@Override
	public UserDTO updateUser(UpdateUserDTO updateUserDTO) {
		String email = updateUserDTO.getEmail().toLowerCase();
		
		User user = this.userDataSvc.fetchUserByEmail(email);
		UserHelperUtil.updateUserWithUpdateUserDTO(updateUserDTO, user);
		this.userDataSvc.updateUser(user);
		
		return DTOMapper.convertUserToUserDTO(user);
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
		sendSuccessfulAccountVerificationEmail(user.getEmail());
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
	public void sendSuccessfulAccountVerificationEmail(String email) {
		String subject = "Account Successfully Verified";
		String message = "";
		this.emailSvc.sendEmail(email, subject, message);
	}
	
}
