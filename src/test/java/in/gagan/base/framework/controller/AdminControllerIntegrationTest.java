/*
 * SpringBoot_Hibernate_Framework
 * 
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import in.gagan.base.framework.SpringBootHibernateFrameworkApplication;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.dto.UsernamePasswordAuthDTO;

/**
 * This class is used to test the functionality of the AdminController class.
 * 
 * @author gaganthind
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootHibernateFrameworkApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	private static final String ADMIN_BASE_URL = "/v1/admin";
	
	private static final String INTEGRATION_TEST_USER = "integrationtesting@e.com";
	
	private static final String INTEGRATION_USER_PASSWORD = "TestUser@123";
	
	/**
	 * Method used to test if all mandatory data is passed to the controller class, the new user is registered successfully.
	 * 
	 * @throws Exception - Throw any unwanted exception
	 */
	//@Test
	public void testFetchAllUsers() throws Exception {
		ResponseEntity<List<UserDTO>> responseEntity = getEntity(ADMIN_BASE_URL);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().size() > 0);
	}
	
	//@Test
	public void testUnlockUserAccounts() throws Exception {
		String putUrl = new StringBuilder(ADMIN_BASE_URL).append("/account/unlock").toString();
		List<String> emails = java.util.Arrays.asList("", "", "");
		ResponseEntity<String> responseEntity = putEntity(putUrl, emails);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("User Account(s) Unlocked Successfully!!!", responseEntity.getBody());
	}
	
	/**
	 * Method used to send a get request for fetching user detaiils.
	 * 
	 * @param url - Url to hit
	 * @return ResponseEntity<UserDTO> - User details DTO
	 */
	private ResponseEntity<List<UserDTO>> getEntity(String url) {
		String bearerToken = getBearerToken();
		HttpHeaders headers = createHttpHeader(MediaType.TEXT_PLAIN, bearerToken);
		HttpEntity<UserDTO> httpEntity = new HttpEntity<>(headers);
		
		return this.restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<UserDTO>>() { });
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
		return this.restTemplate.postForEntity(url, httpEntity, String.class);
	}
	
	/**
	 * Method used to update already existing user details.
	 * 
	 * @param url - url to hit
	 * @param emails - List of email ids
	 * @return ResponseEntity<String> - Success message
	 */
	private ResponseEntity<String> putEntity(String url, List<String> emails) {
		String bearerToken = getBearerToken();
		HttpHeaders headers = createHttpHeader(MediaType.APPLICATION_JSON, bearerToken);
		HttpEntity<UserDTO> httpEntity = new HttpEntity<>(headers);
		
		return this.restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
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
