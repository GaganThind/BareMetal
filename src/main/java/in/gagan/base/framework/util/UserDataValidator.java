package in.gagan.base.framework.util;

import static in.gagan.base.framework.enums.UserRoles.EMPTY;

import java.util.Objects;
import java.util.Set;

import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.dto.UserRoleDTO;

public class UserDataValidator {
	
	public static void validateUserDTO(UserDTO user) {
		Objects.requireNonNull(user, "User Details not provided");
		validateEmail(user.getEmail());
		Objects.requireNonNull(user.getFirstName(), "First Name is mandatory");
		Objects.requireNonNull(user.getLastName(), "Last Name is mandatory");
		Objects.requireNonNull(user.getPassword(), "Password is mandatory");
		Objects.requireNonNull(user.getUserRole(), "Use Role(s) are mandatory");

		if (!validateIfCorrectRolesAreAssigned(user.getUserRole())) {
			throw new IllegalArgumentException("Provided User Roles are not correct");
		}
	}
	
	private static boolean validateIfCorrectRolesAreAssigned(Set<UserRoleDTO> userRoles) {
		return userRoles.stream()
				.map(role -> role.getRoleName())
				.noneMatch(role -> EMPTY == role);
	}
	
	public static void validateUserDTOforUpdate(UserDTO user) {
		Objects.requireNonNull(user, "User Details not provided");

		if (null != user.getUserRole() && !validateIfCorrectRolesAreAssigned(user.getUserRole())) {
			throw new IllegalArgumentException("Provided User Roles are not correct");
		}
	} 
	
	public static void validateEmail(String email) {
		Objects.requireNonNull(email, "Email id cannot be null");
	}

}
