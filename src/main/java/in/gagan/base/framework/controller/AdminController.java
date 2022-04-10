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

package in.gagan.base.framework.controller;

import in.gagan.base.framework.controller.base.AbstractController;
import in.gagan.base.framework.dto.user.UserDTO;
import in.gagan.base.framework.entity.user.User;
import in.gagan.base.framework.service.admin.AdminService;
import in.gagan.base.framework.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * This controller class provides the functionality for the admin module.
 * 
 * @author gaganthind
 *
 */
@RestController
@Validated
@RequestMapping(path = "/v1/admin")
public class AdminController extends AbstractController {
	
	private final AdminService adminSvc;

	@Autowired
	public AdminController(AdminService adminSvc) {
		this.adminSvc = adminSvc;
	}

	/**
	 * Method used to return all the users currently present in the system.
	 * 
	 * @return list of all the users in the system.
	 */
	@GetMapping(value = "/users", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDTO>> fetchAllUsers() {
		final Iterable<User> users = this.adminSvc.fetchAllUsers()
													.orElse(Collections.emptyList());
		List<UserDTO> userDTOs = StreamSupport.stream(users.spliterator(), false)
												.map(DTOMapper::convertUserToUserDTO)
												.collect(Collectors.toList());

		return new ResponseEntity<>(userDTOs, HttpStatus.OK);
	}

	/**
	 * Method used to unlock user account based on provided emails.
	 *
	 * @param emails - emails of the accounts to be unlocked
	 * @return success message
	 */
	@PatchMapping(value = "/account/unlock", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> unlockUserAccounts(@Valid @NotNull @NotEmpty @RequestBody List<String> emails) {
		this.adminSvc.unlockUserAccounts(emails);
		final String message = getMessage("message.admin.users.unlock");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	/**
	 * Method used to delete user accounts based on provided emails.
	 *
	 * @param emails - emails of the accounts to be deleted
	 * @return success message
	 */
	@DeleteMapping(value = "/account/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteUserAccounts(@Valid @NotNull @NotEmpty @RequestBody List<String> emails) {
		this.adminSvc.deleteUsers(emails);
		final String message = getMessage("message.admin.users.delete");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	/**
	 * Method used to permanently delete user accounts based on provided emails.
	 *
	 * @param emails - emails of the accounts to be permanently deleted
	 * @return success message
	 */
	@DeleteMapping(value = "/account/delete/hard", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> hardDeleteUserAccounts(@Valid @NotNull @NotEmpty @RequestBody List<String> emails) {
		this.adminSvc.hardDeleteUsers(emails);
		final String message = getMessage("message.admin.users.perm.delete");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

}
