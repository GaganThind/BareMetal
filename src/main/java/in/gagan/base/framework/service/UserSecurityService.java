package in.gagan.base.framework.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.component.AdminAccount;
import in.gagan.base.framework.dao.UserDAO;
import in.gagan.base.framework.dao.UserSecurityDAO;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.dto.UserDetailsDTO;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.entity.UserSecurity;
import in.gagan.base.framework.exception.UsernameExistException;
import in.gagan.base.framework.util.ExceptionHelperUtil;
import in.gagan.base.framework.util.UserHelperUtil;

@Transactional
@Service
public class UserSecurityService {
	
	@Autowired
	private UserSecurityDAO userSecurityDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserValidationService userValidationSvc;
	
	@Autowired
	private AdminAccount adminAccount;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public String registerNewUser(UserDTO user) throws UsernameExistException {
		if (this.userValidationSvc.validateIfUserExists(user.getEmail())) {
			throw new UsernameExistException(user.getEmail());
		}
		
		UserSecurity userSecurity = UserHelperUtil.convertToUserSecurity(user);
		userSecurity.setPassword(this.passwordEncoder.encode(userSecurity.getPassword()));
		
		this.userSecurityDAO.save(userSecurity);
		return user.getUsername();
	}
	
	public UserDetailsDTO fetchUserDetailsForAuthByEmail(String email) throws UsernameNotFoundException {
		User user = fetchUserByEmail(email);
		UserSecurity userSecurity = fetchUserSecurityDetailsById(user.getUserId(), email);
		return new UserDetailsDTO(user, userSecurity);
	}
	
	public UserDTO fetchUserDetailsByEmail(String email) throws UsernameNotFoundException {
		User user = fetchUserByEmail(email);
		UserSecurity userSecurity = fetchUserSecurityDetailsById(user.getUserId(), email);
		return new UserDTO(user, userSecurity);
	}
	
	public void createAdminUser() {
		UserSecurity userSecurity = this.adminAccount.getAdminDetails();
		if (!this.userValidationSvc.validateIfUserExists(userSecurity.getUser().getEmail())) {
			userSecurity.setPassword(this.passwordEncoder.encode(userSecurity.getPassword()));
			this.userSecurityDAO.save(userSecurity);
		}
	}
	
	private User fetchUserByEmail(String email) throws UsernameNotFoundException {
		return this.userDAO.findUserByEmail(email)
				.orElseThrow(() -> ExceptionHelperUtil.throwUserNotFound(email));
	}
	
	private UserSecurity fetchUserSecurityDetailsById(long id, String email) throws UsernameNotFoundException {
		return this.userSecurityDAO.findById(id)
				.orElseThrow(() -> ExceptionHelperUtil.throwUserNotFound(email));
	}
	
}
