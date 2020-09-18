package in.gagan.base.framework.util;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.dto.UserRoleDTO;
import in.gagan.base.framework.entity.Role;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.entity.UserSecurity;

public final class UserHelperUtil {
	
	private UserHelperUtil() { }
	
	public static User convertToUser(UserDTO user) {
		return new User(user.getFirstName(), 
				user.getLastName(), 
				user.getEmail(), 
				user.getDob(), 
				user.getGender());
	}
	
	public static UserSecurity convertToUserSecurity(UserDTO user) {
		Set<Role> roles = convertDTOToRole(user.getUserRole());
		return new UserSecurity(user.getPassword(), 
				LocalDateTime.now().plusDays(ApplicationConstants.PASSWORD_EXPIRE_DAYS), 
				ApplicationConstants.DEFAULT_FAILED_LOGIN_ATTEMPTS, 
				ApplicationConstants.CHAR_N, 
				roles, 
				convertToUser(user));
		
	}
	
	public static Set<UserRoleDTO> convertRoleToDTO(Set<Role> roles) {
		return roles.stream()
					.map(UserRoleDTO::new)
					.collect(Collectors.toSet());
	}
	
	public static Set<Role> convertDTOToRole(Set<UserRoleDTO> roles) {
		return roles.stream()
					.map(role -> new Role(role.getRoleName()))
					.collect(Collectors.toSet());
	}

}
