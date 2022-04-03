/*
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

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import in.gagan.base.framework.entity.user.User;

/**
 * This Service interface provides the basic CRUD operations on a User table from user account perspective.
 * 
 * @author gaganthind
 *
 */
public interface UserDataService {
	
	/**
	 * This method is used to fetch user data based on provided email.
	 * 
	 * @param email - Email address of the user
	 * @return User - User object from the database
	 * @throws UsernameNotFoundException - If user with the provided email doesn't exist 
	 */
	User fetchUserByEmail(String email) throws UsernameNotFoundException;
	
	/**
	 * This method is used to save the User record.
	 * 
	 * @param user - user record
	 */
	void saveUser(User user);
	
	/**
	 * This method is used to update the User record.
	 * 
	 * @param user - user record
	 */
	void updateUser(User user);
	
	/**
	 * This method is used to soft delete the User record.
	 * 
	 * @param email - email address of user
	 */
	void deleteUser(String email);

	/**
	 * This method is used to permanently delete the User record.
	 *
	 * @param email - email address of user
	 */
	void hardDeleteUser(String email);
	
	/**
	 * This method is used to check if a User record exists with the provided email.
	 * 
	 * @param email - email address of user
	 * @return boolean - if user is present in the system
	 */
	boolean isUserPresent(String email);

}
