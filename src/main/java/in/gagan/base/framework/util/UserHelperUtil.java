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
	
	public static UserSecurity convertUserDTOToUserSecurity(UserDTO userDTO, UserSecurity userSecurity) {
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		BeanUtils.copyProperties(userDTO, userSecurity);
		
		userSecurity.setPasswordExpireDate(LocalDateTime.now().plusDays(ApplicationConstants.PASSWORD_EXPIRE_DAYS));
		userSecurity.setFailedLoginAttempts(ApplicationConstants.DEFAULT_FAILED_LOGIN_ATTEMPTS);
		userSecurity.setAccountLocked(ApplicationConstants.CHAR_N);
		userSecurity.setUserRole(convertDTOToRole(userDTO.getUserRole()));
		userSecurity.setUser(user);
		
		return userSecurity;
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
	
	public static void convertUserSecurityToUserDTO(UserSecurity userSecurity, UserDTO userDTO) {
		User user = userSecurity.getUser();
		BeanUtils.copyProperties(user, userDTO);
		BeanUtils.copyProperties(userSecurity, userDTO,"password", "userRole");
		userDTO.setUserRole(convertRoleToDTO(userSecurity.getUserRole()));
	}
	
}
