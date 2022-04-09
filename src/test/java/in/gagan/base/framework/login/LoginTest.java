/*
 * Copyright (C) 2020-2022  Gagan Thind
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package in.gagan.base.framework.login;

import in.gagan.base.framework.BareMetalApplication;
import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.dto.user.UsernamePasswordAuthDTO;
import in.gagan.base.framework.entity.user.Role;
import in.gagan.base.framework.entity.user.User;
import in.gagan.base.framework.service.user.UserDataService;
import in.gagan.base.framework.util.TestRestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static in.gagan.base.framework.enums.UserRoles.*;
import static in.gagan.base.framework.util.CreateUserUtil.USER_PASSWORD;

/**
 * This class is used to test the Login functionality.
 *
 * @author gaganthind
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BareMetalApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest {

    @Autowired
    private UserDataService userDataSvc;

    @Autowired
    private TestRestUtil testRestUtil;

    @Autowired
    private TestRestTemplate restTemplate;

    private static boolean initialized = false;

    @Transactional
    @Before
    public void setup() {

        if (initialized) {
            return ;
        }

        User admin = new User("Admin", "Testing", "adminuser@example.com", USER_PASSWORD);
        admin.addRole(new Role(ADMIN));
        admin.setActiveSw(ApplicationConstants.CHAR_Y);
        this.userDataSvc.saveUser(admin);

        User adminBasic = new User("AdminBasic", "Testing", "adminbasic@example.com", USER_PASSWORD);
        adminBasic.addRole(new Role(ADMIN_BASIC));
        adminBasic.setActiveSw(ApplicationConstants.CHAR_Y);
        this.userDataSvc.saveUser(adminBasic);

        User user = new User("User", "Testing", "user@example.com", USER_PASSWORD);
        user.addRole(new Role(USER));
        user.setActiveSw(ApplicationConstants.CHAR_Y);
        this.userDataSvc.saveUser(user);

        User userBasic = new User("UserBasic", "Testing", "userbasic@example.com", USER_PASSWORD);
        userBasic.addRole(new Role(USER_BASIC));
        userBasic.setActiveSw(ApplicationConstants.CHAR_Y);
        this.userDataSvc.saveUser(userBasic);

        User multipleLoginFailure = new User("NonAdmin", "Testing",
                "multiplefailures@example.com", USER_PASSWORD);
        multipleLoginFailure.addRole(new Role(USER_BASIC));
        multipleLoginFailure.setActiveSw(ApplicationConstants.CHAR_Y);
        this.userDataSvc.saveUser(multipleLoginFailure);

        User multipleLoginFailureLockingUser = new User("NonAdmin", "Testing",
                "multiplefailureslockuser@example.com", USER_PASSWORD);
        multipleLoginFailureLockingUser.addRole(new Role(USER_BASIC));
        multipleLoginFailureLockingUser.setActiveSw(ApplicationConstants.CHAR_Y);
        this.userDataSvc.saveUser(multipleLoginFailureLockingUser);

        User inactiveUser = new User("Inactive", "Testing",
                "inactiveuser@example.com", USER_PASSWORD);
        inactiveUser.addRole(new Role(USER_BASIC));
        this.userDataSvc.saveUser(inactiveUser);

        initialized = true;
    }

    @Test
    public void testLoginAdmin() {
        String bearer = this.testRestUtil.getBearerToken("adminuser@example.com", USER_PASSWORD);

        Assert.assertTrue(null != bearer && !"".equals(bearer));
    }

    @Test
    public void testLoginAdminWithIncorrectCredentials() {
        ResponseEntity<String> responseEntity = login("adminuser@example.com", "ajfdkias");

        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    public void testLoginAdminBasic() {
        String bearer = this.testRestUtil.getBearerToken("adminbasic@example.com", USER_PASSWORD);

        Assert.assertTrue(null != bearer && !"".equals(bearer));
    }

    @Test
    public void testLoginUser() {
        String bearer = this.testRestUtil.getBearerToken("user@example.com", USER_PASSWORD);

        Assert.assertTrue(null != bearer && !"".equals(bearer));
    }

    @Test
    public void testLoginUserBasic() {
        String bearer = this.testRestUtil.getBearerToken("userbasic@example.com", USER_PASSWORD);

        Assert.assertTrue(null != bearer && !"".equals(bearer));
    }

    @Test
    public void testLoginUserBasicFailures() {
        ResponseEntity<String> responseEntity = login("multiplefailures@example.com", "sdfdsf");

        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

        responseEntity = login("multiplefailures@example.com", "sdfdsfwerw");

        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

        responseEntity = login("multiplefailures@example.com", USER_PASSWORD);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testLoginUserBasicLock() {
        ResponseEntity<String> responseEntity = login("multiplefailureslockuser@example.com", "sdfdsf");

        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

        responseEntity = login("multiplefailureslockuser@example.com", "sdfdsfwerw");

        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

        responseEntity = login("multiplefailureslockuser@example.com", "sdfdsfwerwsdf");

        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

        User user = this.userDataSvc.fetchUserByEmail("multiplefailureslockuser@example.com");
        Assert.assertTrue('Y' == user.isAccountLocked());
    }

    @Test
    public void testLoginUserBasicInactive() {
        ResponseEntity<String> responseEntity = login("inactiveuser@example.com", USER_PASSWORD);

        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    private ResponseEntity<String> login(String username, String password) {
        HttpHeaders requestHeaders = this.testRestUtil.createHttpHeader(MediaType.APPLICATION_JSON);
        UsernamePasswordAuthDTO usernamePasswordAuthDTO = new UsernamePasswordAuthDTO(username, password);
        HttpEntity<UsernamePasswordAuthDTO> httpEntity = new HttpEntity<>(usernamePasswordAuthDTO, requestHeaders);
        return this.restTemplate.postForEntity("/login", httpEntity, String.class);
    }

}
