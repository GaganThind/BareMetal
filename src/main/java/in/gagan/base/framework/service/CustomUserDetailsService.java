package in.gagan.base.framework.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.dao.UserDAO;
import in.gagan.base.framework.dto.UserDetailsDTO;
import in.gagan.base.framework.dto.UserRoleDTO;
import in.gagan.base.framework.util.ExceptionHelperUtil;

/**
 * This class provides the implementation of UserDetailsService interface and provides the functionality of user Authentication.
 * 
 * @author gaganthind
 *
 */
@Transactional
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserDAO userDAO;
	
	@Autowired
	public CustomUserDetailsService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	/**
	 * This method is used to authenticate user. This is done by fetching details from User table and authenticating.
	 * 
	 * @param email - user email for authentication
	 * @return UserDetails - User details object of spring
	 * @throws UsernameNotFoundException - Provided email isn't registered in the system
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		in.gagan.base.framework.entity.User user = this.userDAO.findUserByEmail(email)
																.orElseThrow(() -> ExceptionHelperUtil.throwUserNotFound(email));
		
		UserDetailsDTO userDetails = new UserDetailsDTO(user);
		Set<UserRoleDTO> roles = userDetails.getUserRole();

		Set<GrantedAuthority> authorities = roles.stream()
												.map(this::convertRoleName)
												.map(SimpleGrantedAuthority::new)
												.collect(Collectors.toSet());
		
		return new User(userDetails.getEmail(), userDetails.getPassword(), userDetails.isActive(), true, 
				!userDetails.isPasswordExpired(), !userDetails.isAccountLocked(), authorities);
	}
	
	private String convertRoleName(UserRoleDTO role) {
		return new StringBuilder("ROLE_").append(role.getRoleName()).toString();
	}

}
