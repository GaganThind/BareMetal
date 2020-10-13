package in.gagan.base.framework.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.dto.UserRoleDTO;
import in.gagan.base.framework.entity.Role;
import in.gagan.base.framework.entity.User;

public final class UserHelperUtil {
	
	private UserHelperUtil() { }
	
	public static User convertUserDTOToUser(UserDTO userDTO, User user) {
		BeanUtils.copyProperties(userDTO, user);
		user.setUserRole(convertDTOToRole(userDTO.getUserRole()));
		return user;
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
	
	public static void convertUserToUserDTO(User user, UserDTO userDTO) {
		BeanUtils.copyProperties(user, userDTO,"password", "userRole");
		userDTO.setUserRole(convertRoleToDTO(user.getUserRole()));
	}
	
}
