package in.gagan.base.framework.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import in.gagan.base.framework.enums.Gender;
import in.gagan.base.framework.validator.EmailValidator;

/**
 * This DTO captures the user details from user entity for updating user details and is used for internal data transfer.
 * 
 * @author gaganthind
 *
 */
public class UpdateUserDTO implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -4616881598623678171L;
	
	private String firstName;

	private String lastName;
	
	@NotNull(message = "{message.registration.email}")
	@Size(min = 4, message = "{message.registration.email}")
	@EmailValidator
	private String email;
	
	private LocalDate dob;
	
	private Gender gender;
	
	private boolean activeSw;
	
	private Set<UserRoleDTO> userRole = new HashSet<>();

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public boolean isActiveSw() {
		return activeSw;
	}

	public void setActiveSw(boolean activeSw) {
		this.activeSw = activeSw;
	}

	public Set<UserRoleDTO> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRoleDTO> userRole) {
		this.userRole = userRole;
	}

}
