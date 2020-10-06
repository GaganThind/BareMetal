package in.gagan.base.framework.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.component.AdminAccount;
import in.gagan.base.framework.dao.UserSecurityDAO;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.entity.UserSecurity;
import in.gagan.base.framework.exception.UsernameExistException;
import in.gagan.base.framework.util.UserHelperUtil;

@Transactional
@Service
public class UserRegisterationService {
	
	private final AdminAccount adminAccount;
	
	private final UserValidationService userValidationSvc;
	
	private final PasswordEncoder passwordEncoder;
	
	private final UserSecurityDAO userSecurityDAO;
	
	@Autowired
	public UserRegisterationService(UserValidationService userValidationSvc, AdminAccount adminAccount
			, PasswordEncoder passwordEncoder, UserSecurityDAO userSecurityDAO) {
		this.userValidationSvc = userValidationSvc;
		this.adminAccount = adminAccount;
		this.passwordEncoder = passwordEncoder;
		this.userSecurityDAO = userSecurityDAO;
	}
	
	public void createAdminUser() {
		UserSecurity userSecurity = this.adminAccount.getAdminDetails();
		if (!this.userValidationSvc.validateIfUserExists(userSecurity.getUser().getEmail())) {
			userSecurity.setPassword(this.passwordEncoder.encode(userSecurity.getPassword()));
			this.userSecurityDAO.save(userSecurity);
		}
	}
	
	public String registerNewUser(UserDTO user) throws UsernameExistException {
		this.userValidationSvc.validateIfProvidedUserIsCorrectlyFormed(user);
		
		UserSecurity userSecurity = UserHelperUtil.convertToUserSecurity(user);
		userSecurity.setPassword(this.passwordEncoder.encode(userSecurity.getPassword()));
		
		this.userSecurityDAO.save(userSecurity);
		return user.getUsername();
	}

}
