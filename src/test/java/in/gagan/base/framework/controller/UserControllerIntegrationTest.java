/*
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

import in.gagan.base.framework.BareMetalApplication;
import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.dto.user.UserDTO;
import in.gagan.base.framework.entity.user.Role;
import in.gagan.base.framework.entity.user.User;
import in.gagan.base.framework.exception.InvalidTokenException;
import in.gagan.base.framework.exception.UsernameExistException;
import in.gagan.base.framework.service.user.UserDataService;
import in.gagan.base.framework.service.user.UserRegisterationService;
import in.gagan.base.framework.service.user.VerificationTokenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static in.gagan.base.framework.util.TestHelperUtil.*;
import static in.gagan.base.framework.enums.UserRoles.*;
import static org.junit.Assert.assertEquals;

/**
 * This class is used to test the functionality of the UserController class.
 * 
 * @author gaganthind
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BareMetalApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest extends AbstractBaseTester {
	private static final String USER_BASE_URL = "/v1/users";
	private static final String REGISTER_USER = "/v1/users/register";
	private static final String FETCH_USER_BASE_URL = "/v1/users/";
	private static final String DELETE_USER_BASE_URL = "/v1/users/";
	private static final String VERIFY_USER_BASE_URL = "/v1/users/register/verify/";
	private static final String UPDATE_USER_BASE_URL = "/v1/users/";

	private static final String INTEGRATION_TEST_USER = "integrationtesting@e.com";
	private static final String DUMMY_TEST_USER = "dummytesting@e.com";

	@Autowired
	private UserDataService userDataSvc;

	@Autowired
	private VerificationTokenService verificationTokenSvc;

	@Autowired
	private UserRegisterationService userRegisterationSvc;

	private static boolean initialized = false;

	@Transactional
	@Before
	public void setup() {
		if (initialized) {
			return;
		}

		// Added for PATCH http method to work
		//restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

		User testingUser = new User("Integration", "Testing", INTEGRATION_TEST_USER, USER_PASSWORD);
		testingUser.addRole(new Role(USER));
		testingUser.setActiveSw(ApplicationConstants.CHAR_Y);
		this.userDataSvc.saveUser(testingUser);

		User dummyFetchUser = new User("Dummy", "Testing", DUMMY_TEST_USER, USER_PASSWORD);
		dummyFetchUser.addRole(new Role(USER_BASIC));
		dummyFetchUser.setActiveSw(ApplicationConstants.CHAR_Y);
		this.userDataSvc.saveUser(dummyFetchUser);

		User adminUser = new User("Admin", "Testing", "admintesting@e.com", USER_PASSWORD);
		adminUser.addRole(new Role(ADMIN));
		adminUser.setActiveSw(ApplicationConstants.CHAR_Y);
		this.userDataSvc.saveUser(adminUser);

		User deleteUser = new User("Delete", "Testing", "deletetesting@e.com", USER_PASSWORD);
		deleteUser.addRole(new Role(USER));
		deleteUser.setActiveSw(ApplicationConstants.CHAR_Y);
		this.userDataSvc.saveUser(deleteUser);

		User deleteUser1 = new User("Delete", "Testing", "deletetesting1@e.com", USER_PASSWORD);
		deleteUser1.addRole(new Role(USER_BASIC));
		deleteUser1.setActiveSw(ApplicationConstants.CHAR_Y);
		this.userDataSvc.saveUser(deleteUser1);

		User deleteUser2 = new User("Delete", "Testing", "deletetesting2@e.com", USER_PASSWORD);
		deleteUser2.addRole(new Role(USER_BASIC));
		deleteUser2.setActiveSw(ApplicationConstants.CHAR_Y);
		this.userDataSvc.saveUser(deleteUser2);

		User updateUser = new User("Update", "Testing", "testupdateuser@e.com", USER_PASSWORD);
		updateUser.addRole(new Role(USER));
		updateUser.setActiveSw(ApplicationConstants.CHAR_Y);
		this.userDataSvc.saveUser(updateUser);

		User verifyUser = new User("Verify", "Testing", "verifyuser@e.com", USER_PASSWORD);
		verifyUser.setActiveSw(ApplicationConstants.CHAR_Y);
		verifyUser.setPasswordExpireDate(LocalDateTime.now().plusDays(2));
		try {
			this.userRegisterationSvc.registerNewUser(verifyUser);
		} catch (UsernameExistException e) {
			// Nothing should happen
		}

		initialized = true;
	}

	@Test
	public void testRegisterUserWithMandatoryData() {
		UserDTO userDTO = createDummyUserDTO("testRegisterUserWithMandatoryData@email.com");
		ResponseEntity<String> responseEntity = registerUserPostRequest(userDTO, REGISTER_USER);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}

	@Test
	public void testRegisterUserWithAllData() {
		UserDTO userDTO = createDummyUserDTOWithAllDetails("testRegisterUserWithAllData@email.com");
		ResponseEntity<String> responseEntity = registerUserPostRequest(userDTO, REGISTER_USER);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}

	@Test
	public void testRegisterUser_LastNameNull() {
		UserDTO userDTO = createDummyUserDTO("testRegisterUser_LastNameNull@email.com");
		userDTO.setLastName(null);
		userDTO.setMatchingPassword(null);
		ResponseEntity<String> responseEntity = registerUserPostRequest(userDTO, REGISTER_USER);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	public void testRegisterUserWithExistingUser() {
		UserDTO userDTO = createDummyUserDTO(DUMMY_TEST_USER);
		ResponseEntity<String> responseEntity = registerUserPostRequest(userDTO, REGISTER_USER);

		assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
	}

	private <T> ResponseEntity<String> registerUserPostRequest(T inputDTO, String url) {
		HttpHeaders requestHeaders = createHttpHeader(MediaType.APPLICATION_JSON);
		HttpEntity<T> httpEntity = new HttpEntity<>(inputDTO, requestHeaders);
		return this.restTemplate.postForEntity(url, httpEntity, String.class);
	}

	@Test
	public void testFetchUserWithLoggedInUserFetchingItself() {
		String url = FETCH_USER_BASE_URL + INTEGRATION_TEST_USER;
		ResponseEntity<UserDTO> responseEntity = getEntity(UserDTO.class, url);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(INTEGRATION_TEST_USER, responseEntity.getBody().getEmail());
		assertEquals("Integration Testing", responseEntity.getBody().getUsername());
	}

	@Test
	public void testFetchUserWithLoggedInUserFetchingDifferentRegisteredUser() {
		String url = FETCH_USER_BASE_URL + DUMMY_TEST_USER;
		ResponseEntity<UserDTO> responseEntity = getEntity(UserDTO.class, url);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test
	public void testFetchUserWithLoggedInUserFetchingNonRegisteredEmail() {
		String url = FETCH_USER_BASE_URL + "asd@e.com";
		ResponseEntity<UserDTO> responseEntity = getEntity(UserDTO.class, url);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test
	public void testFetchUserWithLoggedInUserFetchingIncorrectEmailFormat() {
		String url = FETCH_USER_BASE_URL + "asd@ecom";
		ResponseEntity<UserDTO> responseEntity = getEntity(UserDTO.class, url);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	private <T> ResponseEntity<T> getEntity(Class<T> output, String url) {
		return get(output,url, INTEGRATION_TEST_USER, USER_PASSWORD);
	}

	@Test
	public void testDeleteUserWithItself() {
		String url = DELETE_USER_BASE_URL + "deletetesting@e.com";
		ResponseEntity<String> responseEntity = delete(url, "deletetesting@e.com", USER_PASSWORD);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testDeleteUserWithUserRoleDeletingOtherUser() {
		String url = DELETE_USER_BASE_URL + "deletetesting1@e.com";
		ResponseEntity<String> responseEntity = delete(url, INTEGRATION_TEST_USER, USER_PASSWORD);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test
	public void testDeleteUser_Admin() {
		String url = DELETE_USER_BASE_URL + "deletetesting2@e.com";
		ResponseEntity<String> responseEntity = delete(url, "admintesting@e.com", USER_PASSWORD);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testVerifyUser() throws InvalidTokenException {
		String token = this.verificationTokenSvc.fetchByEmail("verifyuser@e.com").getToken();
		String url = VERIFY_USER_BASE_URL + token;
		ResponseEntity<String> responseEntity = verifyTokenUsingPatchMethod(url);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testVerifyUserWithExpiredToken() {
		String url = VERIFY_USER_BASE_URL + "skjdjgaksd";
		ResponseEntity<String> responseEntity = verifyTokenUsingPatchMethod(url);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	public void testVerifyUser_WithEmptyToken() {
		String url = VERIFY_USER_BASE_URL + "";
		ResponseEntity<String> responseEntity = verifyTokenUsingPatchMethod(url);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	private ResponseEntity<String> verifyTokenUsingPatchMethod(String url) {
		HttpHeaders headers = createHttpHeader(MediaType.TEXT_PLAIN);
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);

		ResponseEntity<String> responseEntity = this.restTemplate.exchange(url, HttpMethod.PATCH, httpEntity, String.class);
		return responseEntity;
	}

	private <T, R> ResponseEntity<R> patchEntity(T inputDTO, Class<R> output, String url) {
		return patch(inputDTO, output, url, INTEGRATION_TEST_USER, USER_PASSWORD);
	}

	@Test
	public void testUpdateUser() {
		LocalDate dob = LocalDate.of(1976, Month.JUNE, 6);
		final String email = "testupdateuser@e.com";
		UserDTO inputUserDTO = createDummyUserDTO(email, dob);
		String url = UPDATE_USER_BASE_URL + email;
		ResponseEntity<UserDTO> responseEntity = patch(inputUserDTO, UserDTO.class, url, email, USER_PASSWORD);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(email, responseEntity.getBody().getEmail());
		assertEquals(dob, responseEntity.getBody().getDob());
	}

	@Test
	public void testUpdateUser_WithOtherRegisteredEmail_NonAdmin() {
		LocalDate dob = LocalDate.of(1976, Month.JUNE, 3);
		UserDTO inputUserDTO = createDummyUserDTO("dummytesting@e.com", dob);
		String url = UPDATE_USER_BASE_URL + "dummytesting@e.com";
		ResponseEntity<UserDTO> responseEntity = patchEntity(inputUserDTO, UserDTO.class, url);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test
	public void testUpdateUser_WithOtherRegisteredEmail_Admin() {
		LocalDate dob = LocalDate.of(1976, Month.AUGUST, 9);
		UserDTO inputUserDTO = createDummyUserDTO("dummytesting@e.com", dob);
		String url = UPDATE_USER_BASE_URL + "dummytesting@e.com";
		ResponseEntity<UserDTO> responseEntity =
				patch(inputUserDTO, UserDTO.class, url, "admintesting@e.com", "TestUser@123");

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("dummytesting@e.com", responseEntity.getBody().getEmail());
		assertEquals(dob, responseEntity.getBody().getDob());
	}

	@Test
	public void testUpdateUser_WithOtherUnRegisteredEmail() {
		UserDTO inputUserDTO = createDummyUserDTO("unregisteres@e.com", LocalDate.of(1976, Month.AUGUST, 9));
		String url = UPDATE_USER_BASE_URL + "unregisteres@e.com";
		ResponseEntity<UserDTO> responseEntity = patchEntity(inputUserDTO, UserDTO.class, url);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

}
