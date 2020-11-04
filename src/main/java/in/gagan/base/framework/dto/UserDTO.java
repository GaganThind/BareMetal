package in.gagan.base.framework.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.enums.Gender;
import in.gagan.base.framework.util.UserHelperUtil;
import in.gagan.base.framework.validator.EmailValidator;
import in.gagan.base.framework.validator.Password;
import in.gagan.base.framework.validator.PasswordMatches;

/**
 * This DTO captures the user details from user entity and is used for internal data transfer.
 * 
 * @author gaganthind
 *
 */
@PasswordMatches
public class UserDTO implements Serializable {
	
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -5722242821192312914L;

	@NotNull(message = "{message.registration.first.name}")
	@Size(min = 1, max = 25, message = "{message.registration.first.name.length}")
	private String firstName;

	@NotNull(message = "{message.registration.last.name}")
	@Size(min = 1, max = 25, message = "{message.registration.last.name.length}")
	private String lastName;
	
	@NotNull(message = "{message.registration.email}")
	@Size(min = 4, message = "{message.registration.email}")
	@EmailValidator
	private String email;
	
	@Password
	@NotNull(message = "{message.registration.password}")
	@Size(min = 8, message = "{message.registration.password}")
	private String password;
	
	@NotNull(message = "{message.registration.password}")
	@Size(min = 1, message = "{message.registration.password}")
	private String matchingPassword;

	@Past(message = "{message.registration.dob.invalid}")
	private LocalDate dob;
	
	private int age;

	private Gender gender;
	
	private boolean activeSw = true;
	
	@NotNull(message = "{message.registration.roles}")
	@Size(min = 1, message = "{message.registration.roles}")
	private Set<UserRoleDTO> userRole = new HashSet<>();
	
	public UserDTO() { super(); }
	
	public UserDTO(String firstName, String lastName, String email, String password, LocalDate dob, Gender gender,
			Set<UserRoleDTO> userRole) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.gender = gender;
		this.userRole = userRole;
		this.age = UserHelperUtil.calculateAge(dob);
	}

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

	public String getUsername() {
		return new StringBuilder(firstName).append(ApplicationConstants.BLANK).append(lastName).toString();
	}

	public String getEmail() {
		return email.toLowerCase();
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
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
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
