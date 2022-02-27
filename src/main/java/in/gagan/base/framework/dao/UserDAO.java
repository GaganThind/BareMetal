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

package in.gagan.base.framework.dao;

import java.util.List;
import java.util.Optional;

import in.gagan.base.framework.entity.User;

/**
 * This DAO interface provides the CRUD operations for USERS table.
 * 
 * @author gaganthind
 *
 */
public interface UserDAO extends BaseDAO<User, Long> {
	
	/**
	 * Method used to fetch a user by email.
	 * 
	 * @param email - email of user
	 * @return Optional<User> - User object
	 */
	public Optional<User> findUserByEmail(String email);
	
	/**
	 * Method used to unlock account of multiple users by email(s).
	 * 
	 * @param emails - email of user(s)
	 */
	public void unlockUsers(List<String> emails);
	
	/**
	 * Method used to soft delete multiple users by email(s).
	 * 
	 * @param emails - email of user(s)
	 */
	public void deleteUsers(List<String> emails);
	
	/**
	 * Method used to hard delete multiple users by email(s).
	 * 
	 * @param emails - email of user(s)
	 */
	public void hardDeleteUsers(List<String> emails);

}
