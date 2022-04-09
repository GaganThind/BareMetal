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
import in.gagan.base.framework.dto.user.PasswordResetDTO;
import in.gagan.base.framework.entity.user.Role;
import in.gagan.base.framework.entity.user.User;
import in.gagan.base.framework.exception.UsernameExistException;
import in.gagan.base.framework.service.user.ForgotPasswordTokenService;
import in.gagan.base.framework.service.user.PasswordManagerService;
import in.gagan.base.framework.service.user.UserDataService;
import in.gagan.base.framework.service.user.UserRegisterationService;
import in.gagan.base.framework.util.TestRestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static in.gagan.base.framework.enums.UserRoles.ADMIN;
import static in.gagan.base.framework.enums.UserRoles.USER;
import static in.gagan.base.framework.util.CreateUserUtil.USER_PASSWORD;
import static org.junit.Assert.assertEquals;

/**
 * This class is used to test the functionality of the PasswordManagerController class.
 *
 * @author gaganthind
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BareMetalApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PasswordManagerIntegrationTest {

    private static final String PASSWORD_BASE_URL = "/v1/password";
    private static final String INTEGRATION_TEST_USER = "passwordintergrationtest@e.com";

    @Autowired
    private TestRestUtil testRestUtil;

    @Autowired
    private UserDataService userDataSvc;

    @Autowired
    private UserRegisterationService userRegisterationSvc;

    @Autowired
    private ForgotPasswordTokenService forgotPasswordTokenSvc;

    @Autowired
    private PasswordManagerService passwordManagerSvc;

    private static boolean initialized = false;

    @Transactional
    @Before
    public void setup() {
        if (initialized) {
            return;
        }

        User testingUser = new User("Integration", "Testing", INTEGRATION_TEST_USER, USER_PASSWORD);
        testingUser.setActiveSw(ApplicationConstants.CHAR_Y);
        testingUser.addRole(new Role(ADMIN));
        this.userDataSvc.saveUser(testingUser);

        User passwordUpdate = new User("Password", "Testing", "updatepasswordtesting@e.com",
                USER_PASSWORD);
        passwordUpdate.setActiveSw(ApplicationConstants.CHAR_Y);
        this.userDataSvc.saveUser(passwordUpdate);

        User passwordUpdate2 = new User("Password", "Testing", "updatepasswordtesting2@e.com",
                USER_PASSWORD);
        passwordUpdate2.setActiveSw(ApplicationConstants.CHAR_Y);
        this.userDataSvc.saveUser(passwordUpdate2);

        User nonAdminUser = new User("NonAdmin", "Testing", "nonadminpasswordtesting@e.com",
                USER_PASSWORD);
        nonAdminUser.addRole(new Role(USER));
        nonAdminUser.setActiveSw(ApplicationConstants.CHAR_Y);
        this.userDataSvc.saveUser(nonAdminUser);

        User passwordUpdate3 = new User("Password", "Testing", "updatepasswordtesting3@e.com",
                USER_PASSWORD);
        passwordUpdate3.addRole(new Role(USER));
        passwordUpdate3.setActiveSw(ApplicationConstants.CHAR_Y);
        this.userDataSvc.saveUser(passwordUpdate3);

        User generateForgotPassword = new User("Generate", "ForgotPT",
                "generateforgotpassowrdtesting@e.com", USER_PASSWORD);
        generateForgotPassword.addRole(new Role(USER));
        generateForgotPassword.setActiveSw(ApplicationConstants.CHAR_Y);
        this.userDataSvc.saveUser(generateForgotPassword);

        User changePassword = new User("Change", "Password",
                "changepasswordbasedontokentesting@e.com", USER_PASSWORD);
        changePassword.addRole(new Role(USER));
        changePassword.setActiveSw(ApplicationConstants.CHAR_Y);
        changePassword.setPasswordExpireDate(LocalDateTime.now().plusDays(2));
        try {
            this.userRegisterationSvc.registerNewUser(changePassword);
        } catch (UsernameExistException e) {
            // Nothing should happen
        }
        this.passwordManagerSvc.generateForgotPasswordToken(changePassword.getEmail());

        this.initialized = true;
    }

    @Test
    public void testResetPassword() {
        String url = "/v1/password/reset";
        PasswordResetDTO passwordResetDTO = getPasswordResetDTO("Qwaszx@123", "updatepasswordtesting3@e.com");

        ResponseEntity<String> responseEntity =
                this.testRestUtil.patch(passwordResetDTO, String.class, url, "updatepasswordtesting3@e.com",
                        USER_PASSWORD);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testResetPasswordThruAdmin() {
        String url = "/v1/password/reset";
        PasswordResetDTO passwordResetDTO = getPasswordResetDTO("Qwaszx@1234", "updatepasswordtesting2@e.com");

        ResponseEntity<String> responseEntity =
                this.testRestUtil.patch(passwordResetDTO, String.class, url, INTEGRATION_TEST_USER,
                        USER_PASSWORD);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testResetPasswordThruNonAdminUser() {
        String url = "/v1/password/reset";
        PasswordResetDTO passwordResetDTO = getPasswordResetDTO("Qwaszx@1235", "updatepasswordtesting@e.com");

        ResponseEntity<String> responseEntity =
                this.testRestUtil.patch(passwordResetDTO, String.class, url, "nonadminpasswordtesting@e.com",
                        USER_PASSWORD);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    public void testForgotPassword() {
        String url = "/v1/password/token/" + "generateforgotpassowrdtesting@e.com";
        ResponseEntity<String> responseEntity =
                this.testRestUtil.post(String.class, url, "generateforgotpassowrdtesting@e.com",
                        USER_PASSWORD);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testForgotPasswordWithOtherUser() {
        String url = "/v1/password/token/" + "generateforgotpassowrdtesting@e.com";
        ResponseEntity<String> responseEntity =
                this.testRestUtil.post(String.class, url, "nonadminpasswordtesting@e.com",
                        USER_PASSWORD);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testChangePasswordBasedOnToken() {
        String token = this.forgotPasswordTokenSvc.fetchByEmail("changepasswordbasedontokentesting@e.com")
                .orElseThrow(IllegalStateException::new)
                .getToken();
        String url = "/v1/password/forgot/" + token;

        PasswordResetDTO passwordResetDTO = getPasswordResetDTO("Qwaszx@1235",
                "changepasswordbasedontokentesting@e.com");

        ResponseEntity<String> responseEntity =
                this.testRestUtil.patch(passwordResetDTO, String.class, url,
                        "changepasswordbasedontokentesting@e.com", USER_PASSWORD);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testChangePasswordBasedOnInValidToken() {
        String token = "Invalid-Token";
        String url = "/v1/password/forgot/" + token;

        PasswordResetDTO passwordResetDTO = getPasswordResetDTO("Qwaszx@1235",
                "changepasswordbasedontokentesting@e.com");

        ResponseEntity<String> responseEntity =
                this.testRestUtil.patch(passwordResetDTO, String.class, url,
                        "changepasswordbasedontokentesting@e.com", USER_PASSWORD);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    private PasswordResetDTO getPasswordResetDTO(String password, String email) {
        PasswordResetDTO passwordResetDTO = new PasswordResetDTO();
        passwordResetDTO.setPassword(password);
        passwordResetDTO.setMatchingPassword(password);
        passwordResetDTO.setEmail(email);
        return passwordResetDTO;
    }

}
