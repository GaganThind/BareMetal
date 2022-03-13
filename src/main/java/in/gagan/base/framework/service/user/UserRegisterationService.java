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

import in.gagan.base.framework.dto.UpdateUserDTO;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.entity.User;
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
	String registerNewUser(User user) throws UsernameExistException;
	
	/**
	 * This method returns the user data from provided email.
	 * 
	 * @param email - User email address
	 * @return UserDTO - User DTO object with user details
	 */
	UserDTO fetchUser(String email);
	
	/**
	 * This method is used to either update the record(if present) or insert the record.
	 * 
	 * @param updateUserDTO - User DTO object with user details to update
	 * @return UserDTO - User DTO object with user details
	 */
	UserDTO updateUser(UpdateUserDTO updateUserDTO);

	/**
	 * This method is used to soft delete the user record using provided email.
	 * 
	 * @param email - Email address of user
	 */
	void deleteUser(String email);

	/**
	 * This method is used to confirm the user registration by accepting a token.
	 * 
	 * @param token - Random token generated for activating user
	 */
	void confirmUserRegisteration(String token);

	/**
	 * This method is used to send account verification email with random token for user verification.
	 * 
	 * @param email - Email address of user
	 * @param token - Random token generated for activating user
	 */
	void sendAccountVerificationEmail(String email, String token);

	/**
	 * This method is used to send account successfully verified email.
	 * 
	 * @param email - Email address of user
	 */
	void sendSuccessfulAccountVerificationEmail(String email);

}
