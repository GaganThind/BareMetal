package in.gagan.base.framework.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

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
		return new UserSecurity(user.getPassword(), 
				LocalDateTime.now().plusDays(ApplicationConstants.PASSWORD_EXPIRE_DAYS), 
				ApplicationConstants.DEFAULT_FAILED_LOGIN_ATTEMPTS, 
				ApplicationConstants.CHAR_N, 
				convertDTOToRole(user.getUserRole()), 
				convertToUser(user));
	}
	
	public static Set<UserRoleDTO> convertRoleToDTO(Set<Role> roles) {
		return roles.stream()
					.map(role -> role.getRoleName())
					.map(UserRoleDTO::new)
					.collect(Collectors.toSet());
	}
	
	public static Set<Role> convertDTOToRole(Set<UserRoleDTO> roles) {
		return roles.stream()
					.map(role -> role.getRoleName())
					.map(Role::new)
					.collect(Collectors.toSet());
	}
	
	public static int calculateAge(LocalDate inputDate) {
		if (null == inputDate) {
			return 0;
		}
		return Period.between(inputDate, LocalDate.now()).getYears();
	}
	
	public static UserDTO convertToUserDTO(UserSecurity userSecurity) {
		UserDTO userDTO = new UserDTO();
		User user = userSecurity.getUser();
		BeanUtils.copyProperties(user, userDTO);
		BeanUtils.copyProperties(userSecurity, userDTO,"password", "userRole");
		userDTO.setUsername(new StringBuilder(user.getFirstName()).append(ApplicationConstants.BLANK).append(user.getLastName()).toString());
		userDTO.setUserRole(convertRoleToDTO(userSecurity.getUserRole()));
		return userDTO;
	}
	
	public static UserRoleDTO converToUserRoleDTO(Role role) {
		return new UserRoleDTO(role.getRoleName()); 
	}
	
}
