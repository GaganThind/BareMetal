package in.gagan.base.framework.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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

@Transactional
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserDAO userDAO;
	
	public CustomUserDetailsService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

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
