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

import java.util.Locale;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import in.gagan.base.framework.controller.base.AbstractController;
import in.gagan.base.framework.entity.user.User;
import in.gagan.base.framework.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.gagan.base.framework.dto.user.UpdateUserDTO;
import in.gagan.base.framework.dto.user.UserDTO;
import in.gagan.base.framework.exception.UsernameExistException;
import in.gagan.base.framework.service.user.UserRegisterationService;
import in.gagan.base.framework.util.UserHelperUtil;
import in.gagan.base.framework.validator.EmailValidator;

/**
 * This controller class provides the functionality for the user module.
 * 
 * @author gaganthind
 *
 */
@RestController
@RequestMapping(value = "/v1/users")
@Validated
public class UserController extends AbstractController {
	
	private final UserRegisterationService userRegistrationSvc;
	
	@Autowired
	public UserController(UserRegisterationService userRegistrationSvc) {
		this.userRegistrationSvc = userRegistrationSvc;
	}
	
	/**
	 * This method is used for the registration of a new user.
	 * 
	 * @param userDTO - the user data represented in form of a DTO 
	 * @return ResponseEntity<String> - Success message
	 * @throws UsernameExistException - If the username/email already exists
	 */
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) throws UsernameExistException {
		User user = DTOMapper.convertUserDTOToUser(userDTO);
		this.userRegistrationSvc.registerNewUser(user);
		return new ResponseEntity<>(
					message.getMessage("message.registration.successful", new Object[]{userDTO.getEmail()},
							Locale.ENGLISH), HttpStatus.CREATED);
	}
	
	/**
	 * This method is used to fetch the details based on input email.
	 * 
	 * @param email - Input email to fetch user details
	 * @return ResponseEntity<UserDTO> - User details from database
	 */
	@GetMapping(value = "/{email}", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> fetchUser(@EmailValidator @PathVariable String email) {
		UserDTO userDTO = this.userRegistrationSvc.fetchUser(email);
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}
	
	/**
	 * This method is used to update the details in a user entity.
	 * 
	 * @param updateUserDTO - the user data represented in form of a DTO
	 * @return ResponseEntity<UserDTO> - User details from database
	 * @throws IllegalAccessException - Logged-In User is different from updating user
	 */
	@PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UpdateUserDTO updateUserDTO) throws IllegalAccessException {
		UserHelperUtil.actionAllowed(updateUserDTO.getEmail());
		UserDTO updatedUserDTO = this.userRegistrationSvc.updateUser(updateUserDTO);
		return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
	}
	
	/**
	 * This method is used to delete the user using the email.
	 * 
	 * @param email - Input email to delete the user
	 * @return ResponseEntity<String> - Success message
	 * @throws IllegalAccessException - Logged-In User is different from updating user
	 */
	@DeleteMapping(value = "/{email}", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteUser(@EmailValidator @PathVariable String email) throws IllegalAccessException {
		UserHelperUtil.actionAllowed(email);
		this.userRegistrationSvc.deleteUser(email);
		return new ResponseEntity<>("Deleted Successfully!!!", HttpStatus.OK);
	}
	
	/**
	 * This method is used to activate a newly added user.
	 * 
	 * @param token - Unique token string 
	 * @return ResponseEntity<String> - Success message
	 */
	@PatchMapping(value = "/register/verify/{token}", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> verifyUser(@NotNull @NotEmpty @PathVariable String token) {
		this.userRegistrationSvc.confirmUserRegisteration(token);
		return new ResponseEntity<>(message.getMessage("message.registration.user.verified",
				null,
				Locale.ENGLISH),
				HttpStatus.OK);
	}
	
}
