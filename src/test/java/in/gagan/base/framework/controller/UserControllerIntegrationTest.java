package in.gagan.base.framework.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import in.gagan.base.framework.SpringBootHibernateFrameworkApplication;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.dto.UserRoleDTO;
import in.gagan.base.framework.dto.UsernamePasswordAuthDTO;
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
	
	private static final String INTEGRATION_TEST_USER = "integrationtesting@e.com";
	
	private static final String INTEGRATION_USER_PASSWORD = "TestUser@123";
	
	private static final String SLASH = "/";
	
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
		String url = new StringBuilder(USER_BASE_URL).append("/register").toString();
		ResponseEntity<String> responseEntity = postEntity(userDTO, url);
		
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
		userDTO.setMatchingPassword(null);
		
		String url = new StringBuilder(USER_BASE_URL).append("/register").toString();
		ResponseEntity<String> responseEntity = postEntity(userDTO, url);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	/**
	 * Method used to test if a particular user exists in the application.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testFetchUser() throws Exception {
		String url = new StringBuilder(USER_BASE_URL).append(SLASH).append(INTEGRATION_TEST_USER).toString();
		ResponseEntity<UserDTO> responseEntity = getEntity(url);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(INTEGRATION_TEST_USER, responseEntity.getBody().getEmail());
		assertEquals("Integration Testing", responseEntity.getBody().getUsername());
	}
	
	/**
	 * Method used to test if a particular user exists in the application using someelse's credentials.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testFetchUser_WithOtherCredentials() throws Exception {
		String url = new StringBuilder(USER_BASE_URL).append(SLASH).append("dummytesting@e.com").toString();
		ResponseEntity<UserDTO> responseEntity = getEntity(url);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("dummytesting@e.com", responseEntity.getBody().getEmail());
		assertEquals("Dummy Testing", responseEntity.getBody().getUsername());
	}
	
	/**
	 * Method used to test if a particular user doesn't exist in the application.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testFetchUser_WithNonRegisteredEmail() throws Exception {
		String url = new StringBuilder(USER_BASE_URL).append(SLASH).append("asd@e.com").toString();
		ResponseEntity<UserDTO> responseEntity = getEntity(url);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	/**
	 * Method used to test if an email with incorrect syntax throws Bad_Request.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testFetchUser_WithIncorrectEmail() throws Exception {
		String url = new StringBuilder(USER_BASE_URL).append(SLASH).append("asd@ecom").toString();
		ResponseEntity<UserDTO> responseEntity = getEntity(url);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	/**
	 * Method used to post data to UserController
	 * 
	 * @param <T> - Input DTO class
	 * @param inputUserDTO - User DTO object with user details
	 * @return ResponseEntity<String> - Message from server
	 */
	private <T> ResponseEntity<String> postEntity(T inputDTO, String url) {
		HttpHeaders requestHeaders = createHttpHeader(MediaType.APPLICATION_JSON);
		HttpEntity<T> httpEntity = new HttpEntity<T>(inputDTO, requestHeaders);
		ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(url, httpEntity, String.class);
		return responseEntity;
	}
	
	/**
	 * Method used to send a get request for fetching user detaiils.
	 * 
	 * @param url - Url to hit
	 * @return ResponseEntity<UserDTO> - User details DTO
	 */
	private ResponseEntity<UserDTO> getEntity(String url) {
		String bearerToken = getBearerToken();
		HttpHeaders headers = createHttpHeader(MediaType.TEXT_PLAIN, bearerToken);
		HttpEntity<UserDTO> httpEntity = new HttpEntity<>(headers);
		
		return this.restTemplate.exchange(url, HttpMethod.GET, httpEntity, UserDTO.class);
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
	
	/**
	 * Method to create a bearer token for user authentication.
	 * 
	 * @return String - Bearer token
	 */
	private String getBearerToken() {
		UsernamePasswordAuthDTO usernamePasswordAuthDTO = new UsernamePasswordAuthDTO(INTEGRATION_TEST_USER, INTEGRATION_USER_PASSWORD);
		
		ResponseEntity<String> responseEntity = postEntity(usernamePasswordAuthDTO, "/login");

		HttpHeaders responseHeaders = responseEntity.getHeaders();
		if (null == responseHeaders) {
			return "";
		}
		
		String authorizationHeader = responseHeaders.getFirst("Authorization");
		return authorizationHeader.replace("Bearer ", "");
	}

	/**
	 * Method to create a header with contentType as parameter type.
	 * 
	 * @param mediaType - Media type for header creation
	 * @return HttpHeaders  -HttpHeaders object
	 */
	private HttpHeaders createHttpHeader(MediaType mediaType) {
		return createHttpHeader(mediaType, null); 
	}
	
	/**
	 * Method to create a header with contentType as parameter type.
	 * 
	 * @param mediaType - Media type for header creation
	 * @param bearerToken - bearer token
	 * @return HttpHeaders  -HttpHeaders object
	 */
	private HttpHeaders createHttpHeader(MediaType mediaType, String bearerToken) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(mediaType);
		if (StringUtils.isNotBlank(bearerToken)) {
			requestHeaders.setBearerAuth(bearerToken);
		}
		return requestHeaders;
	}
	
	private static void deleteUser() {
		System.out.println("Deleted if present");
	}
	
}
