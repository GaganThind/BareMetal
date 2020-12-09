package in.gagan.base.framework.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
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
	
	private static final String TEST_USER_EMAIL = "testRegisterUser@e.com";
	
	private static final String TEST_USER_PASSWORD = "TestUser@123";
	
	private UserDTO userDTO;
	
	@Before
	public void setup() {
		userDTO = createUser();
	}
	
	@AfterClass
	public static void cleanUp() {
		deleteUser();
	}
	
	/**
	 * Method used to test if all mandatory data is passed to the controller class, the new user is registered successfully.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testRegisterUser() throws Exception {
		ResponseEntity<String> responseEntity = postEntity(userDTO);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Test User: user Registration Successfull!!!", responseEntity.getBody());
	}
	
	/**
	 * Method used to test if last name is not passed to the controller class, the user is not registered and exception is thrown.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testRegisterUser_LastNameNull() throws Exception {
		// Failure point
		userDTO.setLastName(null);
		
		ResponseEntity<String> responseEntity = postEntity(userDTO);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	/**
	 * Method used to post data to UserController
	 * 
	 * @param inputUserDTO - User DTO object with user details
	 * @return ResponseEntity<String> - Message from server
	 */
	private ResponseEntity<String> postEntity(UserDTO inputUserDTO) {
		HttpEntity<UserDTO> httpEntity = new HttpEntity<UserDTO>(inputUserDTO, new HttpHeaders());
		
		String url = new StringBuilder(USER_BASE_URL).append("/register").toString();
		ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(url, httpEntity, String.class);
		return responseEntity;
	}
	
	/**
	 * Method used to create a new userDTO object to be sent to the controller class.
	 * 
	 * @return UserDTO - UserDTO object from method
	 */
	private UserDTO createUser() {
		UserRoleDTO userRoleDTO = new UserRoleDTO(UserRoles.USER_BASIC);
		Set<UserRoleDTO> userRoles = new HashSet<>();
		userRoles.add(userRoleDTO);
		
		return new UserDTO("Test", "User", TEST_USER_EMAIL, TEST_USER_PASSWORD, LocalDate.of(1982, Month.APRIL, 1), Gender.M, userRoles);
	}
	
	private static void deleteUser() {
		System.out.println("Deleted if present");
	}
	
}
