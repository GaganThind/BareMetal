package in.gagan.base.framework.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.entity.UserSecurity;
import in.gagan.base.framework.util.UserHelperUtil;

public class UserDTO implements Serializable {
	
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -5722242821192312914L;

	private String firstName;

	private String lastName;
	
	private String username;
	
	private String email;
	
	private String password;

	private LocalDate dob;

	private char gender;
	
	private boolean activeSw = true;
	
	private Set<UserRoleDTO> userRole = new HashSet<>();
	
	public UserDTO() { super(); }
	
	public UserDTO(String firstName, String lastName, String username, String email, String password, LocalDate dob, char gender,
			Set<UserRoleDTO> userRole) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.gender = gender;
		this.userRole = userRole;
	}
	
	public UserDTO(User user, UserSecurity userSecurity) {
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.username = new StringBuilder(user.getFirstName()).append(ApplicationConstants.BLANK).append(user.getLastName()).toString();
		this.email = user.getEmail();
		this.password = "";
		this.dob = user.getDob();
		this.gender = user.getGender();
		this.userRole = UserHelperUtil.convertRoleToDTO(userSecurity.getUserRole());
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
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
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

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
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
