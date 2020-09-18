package in.gagan.base.framework.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.dto.UserDetailsDTO;
import in.gagan.base.framework.dto.UserRoleDTO;

@Transactional
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserSecurityService userSecuritySvc;

	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {

		UserDetailsDTO userDetails = this.userSecuritySvc.fetchUserDetailsForAuthByEmail(email);
		Set<UserRoleDTO> roles = userDetails.getUserRole();

		Set<GrantedAuthority> authorities = roles.stream()
												.map(role -> new SimpleGrantedAuthority(role.getRoleName()))
												.collect(Collectors.toSet());
		
		return new org.springframework.security.core.userdetails.User(userDetails.getEmail(),
				userDetails.getPassword(), userDetails.isActive(), true, !userDetails.isPasswordExpired(),
				!userDetails.isAccountLocked(), authorities);
	}

}
