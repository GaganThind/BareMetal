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
import in.gagan.base.framework.service.user.UserDataService;
import in.gagan.base.framework.util.TestRestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static in.gagan.base.framework.enums.UserRoles.*;
import static in.gagan.base.framework.util.CreateUserUtil.USER_PASSWORD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is used to test the functionality of the AdminController class.
 * 
 * @author gaganthind
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BareMetalApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private TestRestUtil testRestUtil;
	
	private static final String ADMIN_BASE_URL = "/v1/admin";
	private static final String INTEGRATION_TEST_USER = "adminintergrationtest@e.com";
	private static final String INTEGRATION_USER_PASSWORD = "TestUser@123";

	@Autowired
	private UserDataService userDataSvc;

	private static boolean initialized = false;

	@Transactional
	@Before
	public void setup() {
		if (initialized) {
			return;
		}

		User testingUser = new User("Integration", "Testing", INTEGRATION_TEST_USER, USER_PASSWORD);
		testingUser.addRole(new Role(ADMIN));
		testingUser.setActiveSw(ApplicationConstants.CHAR_Y);
		this.userDataSvc.saveUser(testingUser);

		User updateUser = new User("Update", "Testing", "updateadmintesting@e.com", USER_PASSWORD);
		updateUser.addRole(new Role(USER_BASIC));
		updateUser.setAccountLocked('Y');
		this.userDataSvc.saveUser(updateUser);

		User updateUser1 = new User("Update", "Testing", "updateadmintesting1@e.com", USER_PASSWORD);
		updateUser1.addRole(new Role(USER));
		updateUser1.setAccountLocked('Y');
		this.userDataSvc.saveUser(updateUser1);

		User nonAdminUser = new User("Integration", "Testing", "nonadminusertesting@e.com", USER_PASSWORD);
		nonAdminUser.addRole(new Role(USER));
		nonAdminUser.setActiveSw(ApplicationConstants.CHAR_Y);
		this.userDataSvc.saveUser(nonAdminUser);

		User updateUser2 = new User("Update", "Testing", "updateadmintesting2@e.com", USER_PASSWORD);
		updateUser2.addRole(new Role(USER));
		updateUser2.setAccountLocked('Y');
		this.userDataSvc.saveUser(updateUser2);

		User deleteUser = new User("Update", "Testing", "deleteuserbyadmin@e.com", USER_PASSWORD);
		this.userDataSvc.saveUser(deleteUser);

		User deleteUser2 = new User("Update", "Testing", "deleteuserbyadmin2@e.com", USER_PASSWORD);
		this.userDataSvc.saveUser(deleteUser2);

		User hardDeleteUser = new User("Update", "Testing", "harddeleteuserbyadmin@e.com", USER_PASSWORD);
		this.userDataSvc.saveUser(hardDeleteUser);

		User hardDeleteUser2 = new User("Update", "Testing", "harddeleteuserbyadmin2@e.com", USER_PASSWORD);
		this.userDataSvc.saveUser(hardDeleteUser2);

		this.initialized = true;
	}
	
	@Test
	public <T> void testFetchAllUsers() {
		ResponseEntity<List<UserDTO>> responseEntity =
				this.testRestUtil.get(new ParameterizedTypeReference<>() { }
						, "/v1/admin/users", INTEGRATION_TEST_USER, INTEGRATION_USER_PASSWORD);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(Objects.requireNonNull(responseEntity.getBody()).size() > 0);
	}

	@Test
	public <T> void testFetchAllUsersWithNonAdmin() {
		ResponseEntity<List<UserDTO>> responseEntity =
				this.testRestUtil.get(new ParameterizedTypeReference<>() { }
						, "/v1/admin/users", "nonadminusertesting@e.com", USER_PASSWORD);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	@Test
	public void testUnlockUserAccounts() {
		String url = "/v1/admin/account/unlock";
		List<String> emails = Arrays.asList("updateadmintesting@e.com", "updateadmintesting1@e.com");
		ResponseEntity<String> responseEntity =
				this.testRestUtil.patch(emails, String.class, url, INTEGRATION_TEST_USER, INTEGRATION_USER_PASSWORD);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testUnlockUserAccountsWithNullPassed() {
		String url = "/v1/admin/account/unlock";
		List<String> emails = null;
		ResponseEntity<String> responseEntity =
				this.testRestUtil.patch(emails, String.class, url, INTEGRATION_TEST_USER, INTEGRATION_USER_PASSWORD);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	public void testUnlockUserAccountsWithNoEmailsPassed() {
		String url = "/v1/admin/account/unlock";
		List<String> emails = new ArrayList<>();
		ResponseEntity<String> responseEntity =
				this.testRestUtil.patch(emails, String.class, url, INTEGRATION_TEST_USER, INTEGRATION_USER_PASSWORD);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	public void testUnlockUserAccountsWithNonAdminAccount() {
		String url = "/v1/admin/account/unlock";
		List<String> emails = Arrays.asList("updateadmintesting2@e.com");
		ResponseEntity<String> responseEntity =
				this.testRestUtil.patch(emails, String.class, url, "nonadminusertesting@e.com", USER_PASSWORD);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test
	public void testDeleteUserAccounts() {
		String url = "/v1/admin/account/delete";
		List<String> emails = Arrays.asList("deleteuserbyadmin@e.com", "deleteuserbyadmin2@e.com");
		ResponseEntity<String> responseEntity =
				this.testRestUtil.delete(emails, url, INTEGRATION_TEST_USER, INTEGRATION_USER_PASSWORD);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void hardDeleteUserAccounts() {
		String url = "/v1/admin/account/hardDelete";
		List<String> emails = Arrays.asList("harddeleteuserbyadmin@e.com", "harddeleteuserbyadmin2@e.com");
		ResponseEntity<String> responseEntity =
				this.testRestUtil.delete(emails, url, INTEGRATION_TEST_USER, INTEGRATION_USER_PASSWORD);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

}
