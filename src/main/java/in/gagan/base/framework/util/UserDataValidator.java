package in.gagan.base.framework.util;

import static in.gagan.base.framework.enums.UserRoles.EMPTY;

import java.util.Objects;
import java.util.Set;

import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.dto.UserRoleDTO;

/**
 * Utility class to provide basic validation of data.
 * 
 * @author gaganthind
 *
 */
public class UserDataValidator {
	
	/**
	 * Validate UserDTO coming from the user and check if mandatory fields are present.
	 * 
	 * @param userDTO - UserDTO object
	 */
	public static void validateUserDTO(UserDTO userDTO) {
		Objects.requireNonNull(userDTO, "User Details not provided");
		validateEmail(userDTO.getEmail());
		Objects.requireNonNull(userDTO.getFirstName(), "First Name is mandatory");
		Objects.requireNonNull(userDTO.getLastName(), "Last Name is mandatory");
		Objects.requireNonNull(userDTO.getPassword(), "Password is mandatory");
		Objects.requireNonNull(userDTO.getUserRole(), "Use Role(s) are mandatory");

		if (!validateIfCorrectRolesAreAssigned(userDTO.getUserRole())) {
			throw new IllegalArgumentException("Provided User Roles are not correct");
		}
	}
	
	/**
	 * Validate if all the UserRole are correct and are present in user system.
	 * 
	 * @param userRoles - UserRoles from input userDTO
	 * @return boolean - true, if all correct roles are present otherwise false
	 */
	private static boolean validateIfCorrectRolesAreAssigned(Set<UserRoleDTO> userRoles) {
		return userRoles.stream()
				.map(role -> role.getRoleName())
				.noneMatch(role -> EMPTY == role);
	}
	
	/**
	 * Validate if user details are correct for update operation.
	 * 
	 * @param userDTO - user object for update
	 */
	public static void validateUserDTOforUpdate(UserDTO userDTO) {
		Objects.requireNonNull(userDTO, "User Details not provided");

		if (null != userDTO.getUserRole() && !validateIfCorrectRolesAreAssigned(userDTO.getUserRole())) {
			throw new IllegalArgumentException("Provided User Roles are not correct");
		}
	} 
	
	/**
	 * Validate if the email is present.
	 * 
	 * @param email - Input email
	 */
	public static void validateEmail(String email) {
		Objects.requireNonNull(email, "Email id cannot be null");
	}

}
