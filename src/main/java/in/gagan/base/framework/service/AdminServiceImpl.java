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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.component.AdminAccount;
import in.gagan.base.framework.dao.UserDAO;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.util.UserHelperUtil;

/**
 * This class provides the implementation of AdminService interface and provides operations for Admin functionality.
 * 
 * @author gaganthind
 *
 */
@Transactional
@Service
public class AdminServiceImpl implements AdminService {
	
	private final AdminAccount adminAccount;
	
	private final UserDAO userDAO;
	
	@Autowired
	public AdminServiceImpl(AdminAccount adminAccount, UserDAO userDAO) {
		this.adminAccount = adminAccount;
		this.userDAO = userDAO;
	}

	/**
	 * Method used to create Admin account on application startup.
	 */
	@Override
	public void createAdminUser() {
		User user = this.adminAccount.getAdminDetails();
		
		if (!this.userDAO.findUserByEmail(user.getEmail()).isPresent()) {
			this.userDAO.save(user);
		}
	}
	
	/**
	 * Method used to fetch all the users of the application.
	 * 
	 * @return List<UserDTO> - list of all users
	 */
	@Override
	public List<UserDTO> fetchAllUsers() {
		List<User> users = (List<User>) this.userDAO.findAll()
													.orElse(Collections.emptyList());
		return users.stream()
					.map(UserHelperUtil::convertUserToUserDTO)
					.collect(Collectors.toList());
	}

	/**
	 * Method used to unlock user account(s).
	 * 
	 * @param emails - User emails to unlock the account
	 */
	@Override
	public void unlockUserAccounts(List<String> emails) {
		this.userDAO.unlockUsers(emails);
	}

	/**
	 * Method used to delete user account(s).
	 * 
	 * @param emails - User emails to soft delete the account
	 */
	@Override
	public void deleteUsers(List<String> emails) {
		this.userDAO.deleteUsers(emails);
	}

	/**
	 * Method used to hard delete user account(s).
	 * 
	 * @param emails - User emails to hard delete the account
	 */
	@Override
	public void hardDeleteUsers(List<String> emails) {
		this.userDAO.hardDeleteUsers(emails);
	}

}
