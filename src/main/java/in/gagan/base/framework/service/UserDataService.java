package in.gagan.base.framework.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.dao.UserDAO;
import in.gagan.base.framework.dao.UserSecurityDAO;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.entity.UserSecurity;
import in.gagan.base.framework.util.ExceptionHelperUtil;
import in.gagan.base.framework.util.UserHelperUtil;

@Transactional
@Service
public class UserDataService {
	
	private final UserSecurityDAO userSecurityDAO;
	
	private final UserDAO userDAO;
	
	@Autowired
	public UserDataService(UserSecurityDAO userSecurityDAO, UserDAO userDAO) {
		this.userSecurityDAO = userSecurityDAO;
		this.userDAO = userDAO;
	}
	
	public UserDTO fetchUserDetailsByEmail(String email) throws UsernameNotFoundException {
		UserSecurity userSecurity = fetchUserSecurityDetailsByEmail(email);
		return UserHelperUtil.convertToUserDTO(userSecurity);
	}
	
	public List<UserDTO> fetchAllUsers() {
		List<UserSecurity> userSecurity = (List<UserSecurity>) this.userSecurityDAO.findAll().orElse(Collections.emptyList());
		return userSecurity.stream()
							.map(UserHelperUtil::convertToUserDTO)
							.collect(Collectors.toList());
	}
	
	public UserSecurity fetchUserSecurityDetailsByEmail(String email) throws UsernameNotFoundException {
		User user = fetchUserByEmail(email);
		return this.userSecurityDAO.findById(user.getUserId())
									.orElseThrow(() -> ExceptionHelperUtil.throwUserNotFound(email));
	}
	
	public User fetchUserByEmail(String email) throws UsernameNotFoundException {
		return this.userDAO.findUserByEmail(email)
							.orElseThrow(() -> ExceptionHelperUtil.throwUserNotFound(email));
	}
	
}
