package in.gagan.base.framework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping(value = "/v1/users")
public class UserController {
	
	private final UserRegisterationService userRegistrationSvc;
	
	@Autowired
	public UserController(UserRegisterationService userRegistrationSvc) {
		this.userRegistrationSvc = userRegistrationSvc;
	}
	
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> registerUser(@RequestBody UserDTO user) throws UsernameExistException {
		String userName = this.userRegistrationSvc.registerNewUser(user);
		return new ResponseEntity<String>(userName + ": user Registration Successfull!!!", HttpStatus.OK);
	}
	
	@GetMapping(value = "/{email}", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> fetchUser(@PathVariable String email) {
		UserDTO userDTO = this.userRegistrationSvc.fetchUser(email);
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	
	@GetMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> fetchUserWithRequestBody(@RequestBody String email) {
		UserDTO userDTO = this.userRegistrationSvc.fetchUser(email);
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user) {
		UserDTO userDTO = this.userRegistrationSvc.updateOrCreateUser(user);
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{email}", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteUser(@PathVariable String email) {
		this.userRegistrationSvc.deleteUser(email);
		return new ResponseEntity<String>("Deleted Successfully!!!", HttpStatus.OK);
	}
	
	@DeleteMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteUserWithRequestBody(@RequestBody String email) {
		this.userRegistrationSvc.deleteUser(email);
		return new ResponseEntity<String>("Deleted Successfully!!!", HttpStatus.OK);
	}
	
}
