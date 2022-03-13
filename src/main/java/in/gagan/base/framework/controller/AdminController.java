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

package in.gagan.base.framework.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.util.DTOMapper;
import in.gagan.base.framework.util.UserHelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.service.AdminService;

/**
 * This controller class provides the functionality for the admin module.
 * 
 * @author gaganthind
 *
 */
@RestController
@RequestMapping(path = "/v1/admin")
public class AdminController {
	
	private final AdminService adminSvc;

	@Autowired
	public AdminController(AdminService adminSvc) {
		this.adminSvc = adminSvc;
	}

	/**
	 * This method returns all the users currently present in the system.
	 * 
	 * @return ResponseEntity<List<UserDTO>> - List of all the users in the system.
	 */
	@GetMapping(value = "/users", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDTO>> fetchAllUsers() {
		List<User> users = (List<User>) this.adminSvc.fetchAllUsers()
													.orElse(Collections.emptyList());

		List<UserDTO> userDTOs = users.stream()
									.map(DTOMapper::convertUserToUserDTO)
									.collect(Collectors.toList());

		return new ResponseEntity<>(userDTOs, HttpStatus.OK);
	}
	
	@PatchMapping(value = "/account/unlock", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> unlockUserAccounts(@RequestBody List<String> emails) {
		this.adminSvc.unlockUserAccounts(emails);
		return new ResponseEntity<>("User Account(s) Unlocked Successfully!!!", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/account/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteUserAccounts(@RequestBody List<String> emails) {
		this.adminSvc.deleteUsers(emails);
		return new ResponseEntity<>("User Accounts(s) Deleted Successfully!!!", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/account/hardDelete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> hardDeleteUserAccounts(@RequestBody List<String> emails) {
		this.adminSvc.hardDeleteUsers(emails);
		return new ResponseEntity<>("User Accounts(s) Hard Deleted Successfully!!!", HttpStatus.OK);
	}

}
