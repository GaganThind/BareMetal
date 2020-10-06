package in.gagan.base.framework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.exception.UsernameExistException;
import in.gagan.base.framework.service.UserRegisterationService;
import in.gagan.base.framework.service.UserDataService;

@RestController
@RequestMapping(value = "/v1")
public class UserController {
	
	private final UserDataService userDataSvc;
	
	private final UserRegisterationService userRegistrationSvc;
	
	@Autowired
	public UserController(UserDataService userDataSvc, UserRegisterationService userRegistrationSvc) {
		this.userDataSvc = userDataSvc;
		this.userRegistrationSvc = userRegistrationSvc;
	}
	
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String registerUser(@RequestBody UserDTO user) throws UsernameExistException {
		return this.userRegistrationSvc.registerNewUser(user);
	}
	
	@GetMapping(value = "/user/{email}", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDTO fetchUserByEmail(@PathVariable String email) {
		return this.userDataSvc.fetchUserDetailsByEmail(email);
	}
	
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserDTO> fetchAllUsers() {
		return this.userDataSvc.fetchAllUsers();
	}
	
}
