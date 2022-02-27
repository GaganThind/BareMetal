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

import java.util.List;

import in.gagan.base.framework.dto.UserDTO;

/**
 * This Service interface provides the operations for Admin functionality.
 * 
 * @author gaganthind
 *
 */
public interface AdminService {
	
	/**
	 * Method used to create Admin account on application startup.
	 */
	public void createAdminUser();
	
	/**
	 * Method used to fetch all the users of the application.
	 * 
	 * @return List<UserDTO> - list of all users
	 */
	public List<UserDTO> fetchAllUsers();
	
	/**
	 * Method used to unlock user account(s).
	 * 
	 * @param emails - User emails to unlock the account
	 */
	public void unlockUserAccounts(List<String> emails);
	
	/**
	 * This method is used to soft delete the User record(s).
	 * 
	 * @param emails - email address of user(s)
	 */
	public void deleteUsers(List<String> emails);
	
	/**
	 * This method is used to hard delete the User record(s).
	 * 
	 * @param emails - email address of user(s)
	 */
	public void hardDeleteUsers(List<String> emails);

}
