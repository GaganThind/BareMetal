package in.gagan.base.framework.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.dto.UpdateUserDTO;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.dto.UserRoleDTO;
import in.gagan.base.framework.entity.Role;
import in.gagan.base.framework.entity.User;

/**
 * Utility class for user entity. This class contains methods for user to DTO conversions.
 * 
 * @author gaganthind
 *
 */
public final class UserHelperUtil {
	
	private UserHelperUtil() { }
	
	/**
	 * Convert UserDTO to User entity object.
	 * 
	 * @param userDTO - Input DTO from client
	 * @param user - Output User entity object
	 */
	public static void convertUserDTOToUser(UserDTO userDTO, User user) {
		BeanUtils.copyProperties(userDTO, user);
		user.setUserRole(convertDTOToRole(userDTO.getUserRole()));
	}
	
	/**
	 * Convert Role entity to RoleDTO.
	 * 
	 * @param roles - set of roles 
	 * @return Set<UserRolesDTO> - set of userRoleDTO
	 */
	public static Set<UserRoleDTO> convertRoleToDTO(Set<Role> roles) {
		return roles.stream()
					.map(role -> role.getRoleName())
					.map(UserRoleDTO::new)
					.collect(Collectors.toSet());
	}
	
	/**
	 * Convert RoleDTO to Role entity.
	 * 
	 * @param roles - set of userRoleDTO
	 * @return Set<Role> - set of roles
	 */
	public static Set<Role> convertDTOToRole(Set<UserRoleDTO> roles) {
		return roles.stream()
					.map(role -> role.getRoleName())
					.map(Role::new)
					.collect(Collectors.toSet());
	}
	
	/**
	 * Calculate age based on input date.
	 * 
	 * @param inputDate - date to check
	 * @return int - Age
	 */
	public static int calculateAge(LocalDate inputDate) {
		if (null == inputDate) {
			return 0;
		}
		return Period.between(inputDate, LocalDate.now()).getYears();
	}
	
	/**
	 * Convert User entity object toUserDTO object.
	 * 
	 * @param user - User entity object
	 * @param userDTO - Output UserDTO object
	 */
	public static void convertUserToUserDTO(User user, UserDTO userDTO) {
		BeanUtils.copyProperties(user, userDTO,"password", "userRole");
		userDTO.setUserRole(convertRoleToDTO(user.getUserRole()));
	}
	
	/**
	 * Convert User entity object toUserDTO object.
	 * 
	 * @param user - User entity object
	 * @return UserDTO - Output UserDTO object
	 */
	public static UserDTO convertUserToUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO,"password", "userRole");
		userDTO.setUserRole(convertRoleToDTO(user.getUserRole()));
		return userDTO;
	}

	/**
	 * Update User entity object with UpdateUserDTO fields. 
	 * 
	 * @param updateUserDTO - User DTO for updating user details
	 * @param user - User entity object
	 */
	public static void updateUserWithUpdateUserDTO(UpdateUserDTO updateUserDTO, User user) {
		if (null != updateUserDTO.getFirstName()) {
			user.setFirstName(updateUserDTO.getFirstName());
		}
		
		if (null != updateUserDTO.getLastName()) {
			user.setLastName(updateUserDTO.getLastName());
		}
		
		if (null != updateUserDTO.getDob()) {
			user.setDob(updateUserDTO.getDob());
		}
		
		if (null != updateUserDTO.getGender()) {
			user.setGender(updateUserDTO.getGender());
		}
		
		if (null != updateUserDTO.getUserRole() && !updateUserDTO.getUserRole().isEmpty()) {
			user.setUserRole(convertDTOToRole(updateUserDTO.getUserRole()));
		}
		
	}
	
	/**
	 * This method returns the logged-in username.
	 * 
	 * @return String - username
	 */
	public static String loggedInUser() {
		String username = ApplicationConstants.BLANK;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (null == auth) {
			return username;
		}
		
		return (String) auth.getPrincipal();
	}
	
}
