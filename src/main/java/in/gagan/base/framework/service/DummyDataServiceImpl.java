package in.gagan.base.framework.service;

import static in.gagan.base.framework.enums.UserRoles.ADMIN;
import static in.gagan.base.framework.enums.UserRoles.ADMIN_BASIC;
import static in.gagan.base.framework.enums.UserRoles.USER;
import static in.gagan.base.framework.enums.UserRoles.USER_BASIC;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.entity.Role;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.enums.Gender;

/**
 * This class provides the implementation of DummyDataServide interface and provides operations for creating dummy data.
 * 
 * @author gaganthind
 *
 */
@Transactional
@Service
public class DummyDataServiceImpl implements DummyDataService {
	
	private final UserDataService userDataSvc;
	
	private final VerificationTokenService verificationTokenSvc;
	
	@Autowired
	public DummyDataServiceImpl(UserDataService userDataSvc, VerificationTokenService verificationTokenSvc) {
		this.userDataSvc = userDataSvc;
		this.verificationTokenSvc = verificationTokenSvc;
	}
	
	/**
	 * This method creates the dummy users on application startup.
	 */
	@Override
	public void createDummyData() {
		User user = new User("A", "B", "test1@e.com", null, Gender.M, "T");
		user.addRole(new Role(ADMIN));
		user.setActiveSw(ApplicationConstants.CHAR_Y);
		
		User user1 = new User("A1", "B1", "test2@e.com", null, Gender.F, "T");
		user1.addRole(new Role(ADMIN_BASIC));
		user1.setActiveSw(ApplicationConstants.CHAR_Y);
		
		User user2 = new User("A2", "B2", "test3@e.com", null, Gender.F, "T");
		user2.addRole(new Role(USER));
		user2.setActiveSw(ApplicationConstants.CHAR_Y);
		
		User user3 = new User("A3", "B3", "test4@e.com", null, Gender.M, "T");
		user3.addRole(new Role(USER_BASIC));
		user3.setActiveSw(ApplicationConstants.CHAR_Y);
		
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
