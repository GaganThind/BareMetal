package in.gagan.base.framework.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import in.gagan.base.framework.SpringBootHibernateFrameworkApplication;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.dto.UserRoleDTO;
import in.gagan.base.framework.enums.Gender;
import in.gagan.base.framework.enums.UserRoles;

/**
 * This class is used to test the functionality of the UserController class.
 * 
 * @author gaganthind
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootHibernateFrameworkApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Rule
    public ExpectedException expectedException = ExpectedException.none();
	
	private static final String USER_BASE_URL = "/v1/users"; 
	
	/**
	 * Method used to test if all mandatory data is passed to the controller class, the new user is registered successfully.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testRegisterUser() throws Exception {
		ResponseEntity<String> responseEntity = postResponseEntity("User", "testRegisterUser@e.com");
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
		assertEquals(responseEntity.getBody(), "Test User: user Registration Successfull!!!");
	}
	
	/**
	 * Method used to test if last name is not passed to the controller class, the user is not registered and exception is thrown.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testRegisterUser_LastNameNull() throws Exception {
		ResponseEntity<String> responseEntity = postResponseEntity(null, "testRegisterUserFail@e.com");
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Method used to create a post request for the register user functionality
	 * 
	 * @param lastName - Last name of user
	 * @param email - Email of the user
	 * @return ResponseEntity<String> - Response from the request
	 */
	private ResponseEntity<String> postResponseEntity(String lastName, String email) {
		UserDTO userDTO = createUser(lastName, email);
		
		String url = new StringBuilder(USER_BASE_URL).append("/register").toString();
		HttpEntity<UserDTO> httpEntity = new HttpEntity<UserDTO>(userDTO, new HttpHeaders());
		
		return this.restTemplate.postForEntity(url, httpEntity, String.class);
	}
	
	/**
	 * Method used to create a new userDTO object to be sent to the controller class.
	 * 
	 * @param lastName - Last name of user
	 * @param email - Email of the user
	 * @return UserDTO - UserDTO object from method
	 */
	private UserDTO createUser(String lastName, String email) {
		UserRoleDTO userRoleDTO = new UserRoleDTO(UserRoles.USER_BASIC);
		Set<UserRoleDTO> userRoles = new HashSet<>();
		userRoles.add(userRoleDTO);
		
		return new UserDTO("Test", lastName, email, "TestUser@123", LocalDate.of(1982, Month.APRIL, 1), Gender.M, userRoles);
	}
	
}
