package in.gagan.base.framework.controller;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	private static final String USER_BASE_URL = "/v1/users";
	
	private static final String TEST_USER_EMAIL = "testRegisterUser@e.com";
	
	private static final String TEST_USER_PASSWORD = "TestUser@123";
	
	private static final String INTEGRATION_TEST_USER = "integrationtesting@e.com";
	
	private static final String INTEGRATION_USER_PASSWORD = "TestUser@123";
	
	private static final String SLASH = "/";
	
	/**
	 * Method used to test if all mandatory data is passed to the controller class, the new user is registered successfully.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testRegisterUser() throws Exception {
		UserDTO userDTO = createUser();
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
		UserDTO userDTO = createUser();
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
	 * Method used to test if changes are getting persisted if email and changes are sent to server.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testUpdateUser() throws Exception {
		LocalDate dob = LocalDate.of(1984, Month.JUNE, 6);
		
		UserDTO inputUserDTO = createUser(INTEGRATION_TEST_USER, dob);
		
		ResponseEntity<UserDTO> responseEntity = putEntity(inputUserDTO);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(INTEGRATION_TEST_USER, responseEntity.getBody().getEmail());
		assertEquals(dob, responseEntity.getBody().getDob());
	}
	
	/**
	 * Method used to test if non-admin account tries to update another users account.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testUpdateUser_WithOtherRegisteredEmail_NonAdmin() throws Exception {
		UserDTO inputUserDTO = createUser("dummytesting@e.com", LocalDate.of(1984, Month.JUNE, 3));
		
		ResponseEntity<UserDTO> responseEntity = putEntity(inputUserDTO);
		
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	/**
	 * Method used to test if changes of another account are getting persisted if email and changes are sent to server and user is an admin.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testUpdateUser_WithOtherRegisteredEmail_Admin() throws Exception {
		LocalDate dob = LocalDate.of(1984, Month.AUGUST, 9);
		
		UserDTO inputUserDTO = createUser("dummytesting@e.com", dob);
		
		String bearerToken = getBearerToken("admintesting@e.com", "TestUser@123");
		HttpHeaders headers = createHttpHeader(MediaType.APPLICATION_JSON, bearerToken);
		HttpEntity<UserDTO> httpEntity = new HttpEntity<>(inputUserDTO, headers);
		
		ResponseEntity<UserDTO> responseEntity = this.restTemplate.exchange(USER_BASE_URL, HttpMethod.PUT, httpEntity, UserDTO.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("dummytesting@e.com", responseEntity.getBody().getEmail());
		assertEquals(dob, responseEntity.getBody().getDob());
	}
	
	/**
	 * Method used to test if un-registered account tries to update its users account.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testUpdateUser_WithOtherUnRegisteredEmail() throws Exception {
		UserDTO inputUserDTO = createUser("unregisteres@e.com", LocalDate.of(1984, Month.AUGUST, 9));
		
		ResponseEntity<UserDTO> responseEntity = putEntity(inputUserDTO);
		
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	/**
	 * Method used to test if a user can be deleted by itself.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testDeleteUser() throws Exception {
		StringBuilder urlBuilder = new StringBuilder(USER_BASE_URL).append(SLASH).append("deletetesting@e.com");
		URI url = new URI(urlBuilder.toString());
		ResponseEntity<String> responseEntity = deleteEntity(url, "deletetesting@e.com", "TestUser@123");
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Deleted Successfully!!!", responseEntity.getBody());
	}
	
	/**
	 * Method used to test if a user CANNOT be deleted by other non-admin user.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testDeleteUser_NonAdmin() throws Exception {
		StringBuilder urlBuilder = new StringBuilder(USER_BASE_URL).append(SLASH).append("deletetesting1@e.com");
		URI url = new URI(urlBuilder.toString());
		ResponseEntity<String> responseEntity = deleteEntity(url, INTEGRATION_TEST_USER, INTEGRATION_USER_PASSWORD);
		
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	/**
	 * Method used to test if a user CAN be deleted by other admin user.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testDeleteUser_Admin() throws Exception {
		StringBuilder urlBuilder = new StringBuilder(USER_BASE_URL).append(SLASH).append("deletetesting1@e.com");
		URI url = new URI(urlBuilder.toString());
		ResponseEntity<String> responseEntity = deleteEntity(url, "admintesting@e.com", "TestUser@123");
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Deleted Successfully!!!", responseEntity.getBody());
	}
	
	/**
	 * Method used to test if a user can be verified.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	//@Test
	public void testVerifyUser() throws Exception {
		//TODO Need a logic to add the verification token automatically
		
		String verifyUrl = new StringBuilder(USER_BASE_URL).append("/register/verify/").append("").toString();
		HttpHeaders headers = createHttpHeader(MediaType.TEXT_PLAIN);
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		
		ResponseEntity<String> responseEntity = this.restTemplate.exchange(verifyUrl, HttpMethod.PUT, httpEntity, String.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("User Verified Successfully!!!", responseEntity.getBody());
	}
	
	/**
	 * Method used to test if an invalid token is provided, then an error is thrown.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	@Test
	public void testVerifyUser_EmptyToken() throws Exception {
		String verifyUrl = new StringBuilder(USER_BASE_URL).append("/register/verify/").toString();
		HttpHeaders headers = createHttpHeader(MediaType.TEXT_PLAIN);
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		
		ResponseEntity<String> responseEntity = this.restTemplate.exchange(verifyUrl, HttpMethod.PUT, httpEntity, String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
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
	 * Method used to send a get request for fetching user detaiils.
	 * 
	 * @param url - Url to hit
	 * @return ResponseEntity<String> - Server message
	 */
	private ResponseEntity<String> deleteEntity(URI url, String username, String password) {
		String bearerToken = getBearerToken(username, password);
		HttpHeaders headers = createHttpHeader(MediaType.TEXT_PLAIN, bearerToken);
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		
		return this.restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, String.class);
	}
	
	/**
	 * Method used to update already existing user details.
	 * 
	 * @param inputUserDTO - UserDTO object
	 * @return ResponseEntity<UserDTO> - Updated User Details object
	 */
	private ResponseEntity<UserDTO> putEntity(UserDTO inputUserDTO) {
		String bearerToken = getBearerToken();
		HttpHeaders headers = createHttpHeader(MediaType.APPLICATION_JSON, bearerToken);
		HttpEntity<UserDTO> httpEntity = new HttpEntity<>(inputUserDTO, headers);
		
		return this.restTemplate.exchange(USER_BASE_URL, HttpMethod.PUT, httpEntity, UserDTO.class);
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
		
		return new UserDTO("Test", "User", TEST_USER_EMAIL, TEST_USER_PASSWORD, LocalDate.of(1982, Month.APRIL, 1), 
				Gender.M, userRoles, 0, "ABC", "DEF", "GHI", 456478, "123", "345");
	}
	
	/**
	 * Method used to create a new UserDTO object with email and dob.
	 * 
	 * @return UserDTO - User details object
	 */
	private UserDTO createUser(String email, LocalDate dob) {
		UserDTO inputUserDTO = new UserDTO();
		inputUserDTO.setEmail(email);
		inputUserDTO.setDob(dob);
		return inputUserDTO;
	}
	
	/**
	 * Method to create a bearer token for user authentication with provided username and password.
	 * 
	 * @param username - email id of user
	 * @param password - password of user
	 * @return String - Bearer token
	 */
	private String getBearerToken(String username, String password) {
		UsernamePasswordAuthDTO usernamePasswordAuthDTO = new UsernamePasswordAuthDTO(username, password);
		
		ResponseEntity<String> responseEntity = postEntity(usernamePasswordAuthDTO, "/login");

		HttpHeaders responseHeaders = responseEntity.getHeaders();
		if (null == responseHeaders) {
			return "";
		}
		
		String authorizationHeader = responseHeaders.getFirst("Authorization");
		return authorizationHeader.replace("Bearer ", "");
	}
	
	/**
	 * Method to create a bearer token for user authentication.
	 * 
	 * @return String - Bearer token
	 */
	private String getBearerToken() {
		return getBearerToken(INTEGRATION_TEST_USER, INTEGRATION_USER_PASSWORD);
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
	
}
