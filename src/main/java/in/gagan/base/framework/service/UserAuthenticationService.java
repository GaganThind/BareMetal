package in.gagan.base.framework.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.dto.UserDetailsDTO;
import in.gagan.base.framework.entity.UserSecurity;

@Transactional
@Service
public class UserAuthenticationService {
	
	private final UserDataService userDataSvc;
	
	@Autowired
	public UserAuthenticationService(UserDataService userDataSvc) {
		this.userDataSvc = userDataSvc;
	}
	
	public UserDetailsDTO fetchUserDetailsForAuthByEmail(String email) throws UsernameNotFoundException {
		UserSecurity userSecurity = this.userDataSvc.fetchUserSecurityDetailsByEmail(email);
		return new UserDetailsDTO(userSecurity.getUser(), userSecurity);
	}
	
}
