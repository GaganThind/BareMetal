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

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.gagan.base.framework.dto.PasswordResetDTO;
import in.gagan.base.framework.service.user.PasswordManagerService;
import in.gagan.base.framework.util.UserHelperUtil;
import in.gagan.base.framework.validator.EmailValidator;

/**
 * This controller class provides the functionality for the Password reset module.
 * 
 * @author gaganthind
 *
 */
@RestController
@RequestMapping(value = "/v1/password")
@Validated
public class PasswordManagerController {
	
	private final PasswordManagerService passwordManagerService;
	
	public PasswordManagerController(PasswordManagerService passwordManagerService) {
		this.passwordManagerService = passwordManagerService;
	}

	/**
	 * Method used to reset user password.
	 * 
	 * @param passwordResetDTO - Object to transfer password and confirm password
	 * @return ResponseEntity<String> - Success message
	 * @throws IllegalAccessException - If user is not the intended user
	 */
	@PatchMapping(value = "/reset", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> resetPassword(@Valid @RequestBody PasswordResetDTO passwordResetDTO) 
			throws IllegalAccessException {
		String email = passwordResetDTO.getEmail();
		UserHelperUtil.actionAllowed(email);
		this.passwordManagerService.resetPassword(passwordResetDTO.getPassword(), email);
		return new ResponseEntity<>("Password Reset Successful!!!", HttpStatus.OK);
	}
	
	/**
	 * Method used to send forgot password token to user.
	 * 
	 * @param email - Email id of user
	 * @return ResponseEntity<String> - Success message
	 */
	@PostMapping(value = "/token/{email}", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> generateForgotPasswordToken(@EmailValidator @PathVariable String email) {
		this.passwordManagerService.generateForgotPasswordToken(email);
		return new ResponseEntity<>("Password Reset Token Successfully Generated!!!", HttpStatus.OK);
	}
	
	/**
	 * Method used to reset user password.
	 * 
	 * @param passwordResetDTO - Object to transfer password and confirm password
	 * @param token - Unique token string 
	 * @return ResponseEntity<String> - Success message
	 */
	@PatchMapping(value = "/forgot/{token}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> forgotPassword(@Valid @RequestBody PasswordResetDTO passwordResetDTO,@NotNull @NotEmpty @PathVariable String token) {
		this.passwordManagerService.forgotPassword(passwordResetDTO.getPassword(), token);
		return new ResponseEntity<>("Password Reset Successful!!!", HttpStatus.OK);
	}

}
