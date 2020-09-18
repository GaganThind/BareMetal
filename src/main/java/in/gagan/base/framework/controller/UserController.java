package in.gagan.base.framework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.exception.UsernameExistException;
import in.gagan.base.framework.service.UserSecurityService;

@RestController
@RequestMapping(value = "/v1")
public class UserController {
	
	@Autowired
	private UserSecurityService userSecuritySvc;
	
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String registerUser(@RequestBody UserDTO user) throws UsernameExistException {
		return this.userSecuritySvc.registerNewUser(user);
	}
	
	@PostMapping(value = "/user", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDTO fetchUserByEmail(@RequestBody String email) {
		return this.userSecuritySvc.fetchUserDetailsByEmail(email);
	}
	
}
