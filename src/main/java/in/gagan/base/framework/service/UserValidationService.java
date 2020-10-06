package in.gagan.base.framework.service;

import java.util.Objects;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.dao.UserDAO;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.dto.UserRoleDTO;
import in.gagan.base.framework.enums.Roles;
import in.gagan.base.framework.exception.UsernameExistException;

@Transactional
@Service
public class UserValidationService {
	
	private final UserDAO userDAO;
	
	@Autowired
	public UserValidationService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public boolean validateIfProvidedUserIsCorrectlyFormed(UserDTO user) throws UsernameExistException {
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
				.noneMatch(role -> Roles.EMPTY == role);
	}
	
	private Roles convertToRoles(UserRoleDTO userRole) {
		String role = userRole.getRoleName();
		Roles value = Roles.EMPTY;
		try {
			value = Roles.valueOf(role);
		} catch (IllegalArgumentException | NullPointerException ex) {
			value = Roles.EMPTY;
		}
		return value;
	}

}
