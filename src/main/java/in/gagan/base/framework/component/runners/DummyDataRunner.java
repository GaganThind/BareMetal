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

package in.gagan.base.framework.component.runners;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.entity.user.Role;
import in.gagan.base.framework.entity.user.User;
import in.gagan.base.framework.service.user.UserDataService;
import in.gagan.base.framework.service.user.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static in.gagan.base.framework.enums.UserRoles.*;
import static in.gagan.base.framework.enums.UserRoles.USER_BASIC;

@Order(3)
@Component
public class DummyDataRunner implements CommandLineRunner {

    private final UserDataService userDataSvc;

    private final VerificationTokenService verificationTokenSvc;

    @Autowired
    public DummyDataRunner(UserDataService userDataSvc, VerificationTokenService verificationTokenSvc) {
        this.userDataSvc = userDataSvc;
        this.verificationTokenSvc = verificationTokenSvc;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        User testingUser = new User("Integration", "Testing", "integrationtesting@e.com", "TestUser@123");
        testingUser.addRole(new Role(USER));
        testingUser.setActiveSw(ApplicationConstants.CHAR_Y);

        User dummyFetchUser = new User("Dummy", "Testing", "dummytesting@e.com", "TestUser@123");
        dummyFetchUser.addRole(new Role(USER_BASIC));
        dummyFetchUser.setActiveSw(ApplicationConstants.CHAR_Y);

        User adminUser = new User("Admin", "Testing", "admintesting@e.com", "TestUser@123");
        adminUser.addRole(new Role(ADMIN));
        adminUser.setActiveSw(ApplicationConstants.CHAR_Y);

        User deleteUser = new User("Delete", "Testing", "deletetesting@e.com", "TestUser@123");
        deleteUser.addRole(new Role(USER));
        deleteUser.setActiveSw(ApplicationConstants.CHAR_Y);

        User deleteUser1 = new User("Delete", "Testing", "deletetesting1@e.com", "TestUser@123");
        deleteUser1.addRole(new Role(USER_BASIC));
        deleteUser1.setActiveSw(ApplicationConstants.CHAR_Y);

        User deleteUser2 = new User("Delete", "Testing", "deletetesting2@e.com", "TestUser@123");
        deleteUser2.addRole(new Role(USER_BASIC));
        deleteUser2.setActiveSw(ApplicationConstants.CHAR_Y);

        User user = new User("A", "B", "test1@e.com", "T");
        user.addRole(new Role(ADMIN));
        user.setActiveSw(ApplicationConstants.CHAR_Y);

        User user1 = new User("A1", "B1", "test2@e.com", "T");
        user1.addRole(new Role(ADMIN_BASIC));
        user1.setActiveSw(ApplicationConstants.CHAR_Y);

        User user2 = new User("A2", "B2", "test3@e.com", "T");
        user2.addRole(new Role(USER));
        user2.setActiveSw(ApplicationConstants.CHAR_Y);

        User user3 = new User("A3", "B3", "test4@e.com", "T");
        user3.addRole(new Role(USER_BASIC));
        user3.setActiveSw(ApplicationConstants.CHAR_Y);

        this.userDataSvc.saveUser(testingUser);

        this.userDataSvc.saveUser(dummyFetchUser);

        this.userDataSvc.saveUser(adminUser);

        this.userDataSvc.saveUser(deleteUser);

        this.userDataSvc.saveUser(deleteUser1);

        this.userDataSvc.saveUser(deleteUser2);

        this.userDataSvc.saveUser(user);
        this.verificationTokenSvc.generateTokenForUser(user);

        this.userDataSvc.saveUser(user1);
        this.verificationTokenSvc.generateTokenForUser(user1);

        this.userDataSvc.saveUser(user2);
        this.verificationTokenSvc.generateTokenForUser(user2);

        this.userDataSvc.saveUser(user3);
        this.verificationTokenSvc.generateTokenForUser(user3);
    }
}
