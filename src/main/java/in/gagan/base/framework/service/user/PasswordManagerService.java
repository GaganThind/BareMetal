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

import in.gagan.base.framework.dto.PasswordResetDTO;

/**
 * This Service interface defines the basic actions that is used to manage password actions.
 * 
 * @author gaganthind
 *
 */
public interface PasswordManagerService {

	/**
	 * This method is used to reset password of existing user.
	 *
	 * @param password - user defined password
	 * @param  email - User email address
	 */
	void resetPassword(String password, String email);
	
	/**
	 * This method is used to generate forgot password token for existing user.
	 * 
	 * @param email - User email address
	 */
	void generateForgotPasswordToken(String email);

	/**
	 * This method is used to reset password in case a user has forgotten the password.
	 *
	 * @param password - user defined password
	 * @param token - Unique token string
	 */
	void forgotPassword(String password, String token);

	/**
	 * This method is used to send password successfully reset email.
	 * 
	 * @param email - Email address of user
	 */
	void sendPasswordResetSuccessfulEmail(String email);
	
	/**
	 * This method is used to send password reset email to user with token.
	 * 
	 * @param email - Email address of user
	 * @param token - Password reset token
	 */
	void sendPasswordResetEmail(String email, String token);

}
