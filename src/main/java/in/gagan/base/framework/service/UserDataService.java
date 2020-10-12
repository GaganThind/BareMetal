package in.gagan.base.framework.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.dao.UserDAO;
import in.gagan.base.framework.dao.UserSecurityDAO;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.entity.UserSecurity;
import in.gagan.base.framework.util.ExceptionHelperUtil;

@Transactional
@Service
public class UserDataService {
	
	private final UserSecurityDAO userSecurityDAO;
	
	private final UserDAO userDAO;
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserDataService(UserSecurityDAO userSecurityDAO, UserDAO userDAO, PasswordEncoder passwordEncoder) {
		this.userSecurityDAO = userSecurityDAO;
		this.userDAO = userDAO;
		this.passwordEncoder = passwordEncoder;
	}
	
	public UserSecurity fetchUserSecurityByEmail(String email) throws UsernameNotFoundException {
		User user = this.userDAO.findUserByEmail(email)
								.orElseThrow(() -> ExceptionHelperUtil.throwUserNotFound(email));
		UserSecurity userSecurity = this.userSecurityDAO.findById(user.getUserId())
														.orElseThrow(() -> ExceptionHelperUtil.throwUserNotFound(email));
		return userSecurity;
	}
	
	public void saveUser(UserSecurity userSecurity) {
		userSecurity.setPassword(this.passwordEncoder.encode(userSecurity.getPassword()));
		this.userSecurityDAO.save(userSecurity);
	}
	
	public void updateUser(UserSecurity userSecurity) {
		this.userSecurityDAO.save(userSecurity);
	}
	
	public void deleteUser(String email) {
		UserSecurity userSecurity = fetchUserSecurityByEmail(email);
		this.userSecurityDAO.delete(userSecurity);
	}
	
	public void hardDeleteUser(String email) {
		UserSecurity userSecurity = fetchUserSecurityByEmail(email);
		this.userSecurityDAO.hardDelete(userSecurity);
	}
	
	public boolean isUserPresent(String email) {
		return this.userDAO.findUserByEmail(email).isPresent();
	}
	
}
