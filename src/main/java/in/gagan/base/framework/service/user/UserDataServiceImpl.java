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

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.component.PasswordProps;
import in.gagan.base.framework.dao.user.UserDAO;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.util.ExceptionHelperUtil;

/**
 * This class provides the implementation of UserDataService interface and provides operations for User management functionality.
 * 
 * @author gaganthind
 *
 */
@Transactional
@Service
public class UserDataServiceImpl implements UserDataService {
	
	private final UserDAO userDAO;
	
	private final PasswordEncoder passwordEncoder;
	
	private final PasswordProps passwordProps;
	
	@Autowired
	public UserDataServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder, PasswordProps passwordProps) {
		this.userDAO = userDAO;
		this.passwordEncoder = passwordEncoder;
		this.passwordProps = passwordProps;
	}
	
	/**
	 * This method is used to fetch user data based on provided email.
	 * 
	 * @param email - Email address of the user
	 * @return User - User object from the database
	 * @throws UsernameNotFoundException - If user with the provided email doesn't exist 
	 */
	@Override
	public User fetchUserByEmail(String email) throws UsernameNotFoundException {
		return this.userDAO.findUserByEmail(email)
								.orElseThrow(() -> ExceptionHelperUtil.throwUserNotFound(email));
	}
	
	/**
	 * This method is used to save the User record.
	 * 
	 * @param user - user record
	 */
	@Override
	public void saveUser(User user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		if (null == user.getPasswordExpireDate()) {
			user.setPasswordExpireDate(LocalDateTime.now().plusDays(this.passwordProps.getPasswordExpireDays()));
		}
		this.userDAO.save(user);
	}
	
	/**
	 * This method is used to update the User record.
	 * 
	 * @param user - user record
	 */
	@Override
	public void updateUser(User user) {
		this.userDAO.save(user);
	}
	
	/**
	 * This method is used to soft delete the User record.
	 * 
	 * @param email - email address of user
	 */
	@Override
	public void deleteUser(String email) {
		this.userDAO.deleteUsers(List.of(email));
	}
	
	/**
	 * This method is used to check if a User record exists with the provided email.
	 * 
	 * @param email - email address of user
	 * @return boolean - if user is present in the system
	 */
	@Override
	public boolean isUserPresent(String email) {
		return this.userDAO.findUserByEmail(email).isPresent();
	}

}
