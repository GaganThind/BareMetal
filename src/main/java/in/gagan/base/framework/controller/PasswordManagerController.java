package in.gagan.base.framework.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.gagan.base.framework.dto.PasswordResetDTO;
import in.gagan.base.framework.service.PasswordManagerService;
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
	@PutMapping(value = "/reset", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> resetPassword(@Valid @RequestBody PasswordResetDTO passwordResetDTO) 
			throws IllegalAccessException {
		String email = passwordResetDTO.getEmail();
		UserHelperUtil.actionAllowed(email);
		this.passwordManagerService.resetPassword(passwordResetDTO, email);
		return new ResponseEntity<String>("Password Reset Successfull!!!", HttpStatus.OK);
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
		return new ResponseEntity<String>("Password Reset Token Successfully Generated!!!", HttpStatus.OK);
	}
	
	/**
	 * Method used to reset user password.
	 * 
	 * @param passwordResetDTO - Object to transfer password and confirm password
	 * @param token - Unique token string 
	 * @return ResponseEntity<String> - Success message
	 */
	@PutMapping(value = "/forgot/{token}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> forgotPassword(@Valid @RequestBody PasswordResetDTO passwordResetDTO,@NotNull @NotEmpty @PathVariable String token) {
		this.passwordManagerService.forgotPassword(passwordResetDTO, token);
		return new ResponseEntity<String>("Password Reset Successfull!!!", HttpStatus.OK);
	}

}
