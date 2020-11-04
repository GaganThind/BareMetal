package in.gagan.base.framework.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.exception.UsernameExistException;
import in.gagan.base.framework.service.UserRegisterationService;
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
public class UserController {
	
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
		String userName = this.userRegistrationSvc.registerNewUser(userDTO);
		return new ResponseEntity<String>(userName + ": user Registration Successfull!!!", HttpStatus.OK);
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
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	
	/**
	 * This method is used to fetch the details based on input email.
	 * 
	 * @param email - Input email to fetch user details
	 * @return ResponseEntity<UserDTO> - User details from database
	 */
	@GetMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> fetchUserWithRequestBody(@EmailValidator @RequestBody String email) {
		UserDTO userDTO = this.userRegistrationSvc.fetchUser(email);
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	
	/**
	 * This method is used to update the details in a user entity.
	 * 
	 * @param userDTO - the user data represented in form of a DTO
	 * @return ResponseEntity<UserDTO> - User details from database
	 */
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
		UserDTO updatedUserDTO = this.userRegistrationSvc.updateOrCreateUser(userDTO);
		return new ResponseEntity<UserDTO>(updatedUserDTO, HttpStatus.OK);
	}
	
	/**
	 * This method is used to delete the user using the email.
	 * 
	 * @param email - Input email to delete the user
	 * @return ResponseEntity<String> - Success message
	 */
	@DeleteMapping(value = "/{email}", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteUser(@EmailValidator @PathVariable String email) {
		this.userRegistrationSvc.deleteUser(email);
		return new ResponseEntity<String>("Deleted Successfully!!!", HttpStatus.OK);
	}
	
	/**
	 * This method is used to delete the user using the email.
	 * 
	 * @param email - Input email to delete the user
	 * @return ResponseEntity<String> - Success message
	 */
	@DeleteMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteUserWithRequestBody(@EmailValidator @RequestBody String email) {
		this.userRegistrationSvc.deleteUser(email);
		return new ResponseEntity<String>("Deleted Successfully!!!", HttpStatus.OK);
	}
	
	/**
	 * This method is used to activate a newly added user.
	 * 
	 * @param token - Unique token string 
	 * @return ResponseEntity<String> - Success message
	 */
	@PutMapping(value = "/register/verify/{token}", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> verifyUser(@EmailValidator @PathVariable String token) {
		this.userRegistrationSvc.confirmUserRegisteration(token);
		return new ResponseEntity<String>("User Verified Successfully!!!", HttpStatus.OK);
	}
	
}
