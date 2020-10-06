package in.gagan.base.framework.service;

import java.util.Objects;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.dao.UserDAO;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.dto.UserRoleDTO;
import in.gagan.base.framework.enums.UserRoles;
import in.gagan.base.framework.exception.UsernameExistException;

@Transactional
@Service
public class UserValidationService {
	
	private final UserDAO userDAO;
	
	@Autowired
	public UserValidationService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public boolean validateUserDTO(UserDTO user) throws UsernameExistException {
		Objects.requireNonNull(user, "User Details not provided");
		Objects.requireNonNull(user.getEmail(), "Email id is mandatory");
		Objects.requireNonNull(user.getFirstName(), "First Name is mandatory");
		Objects.requireNonNull(user.getLastName(), "Last Name is mandatory");
		Objects.requireNonNull(user.getPassword(), "Password is mandatory");
		Objects.requireNonNull(user.getUserRole(), "Use Role(s) are mandatory");

		if (!validateIfCorrectRolesAreAssigned(user.getUserRole())) {
			throw new IllegalArgumentException("Provided User Roles are not correct");
		}
		
		if (validateIfUserExists(user.getEmail())) {
			throw new UsernameExistException(user.getEmail());
		}
		
		return true;
	}
	
	public boolean validateIfUserExists(String email) {
		return this.userDAO.findUserByEmail(email).isPresent();
	}
	
	private boolean validateIfCorrectRolesAreAssigned(Set<UserRoleDTO> userRoles) {
		return userRoles.stream()
				.map(this::convertToRoles)
				.noneMatch(role -> UserRoles.EMPTY == role);
	}
	
	private UserRoles convertToRoles(UserRoleDTO userRole) {
		String role = userRole.getRoleName();
		UserRoles value = UserRoles.EMPTY;
		try {
			value = UserRoles.valueOf(role);
		} catch (IllegalArgumentException | NullPointerException ex) {
			// No value with the specified string exists in Roles enum
		}
		return value;
	}

}
