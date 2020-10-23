package in.gagan.base.framework.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.util.UserHelperUtil;

/**
 * This DTO captures the user details from the user entity object to be used to authentication by the spring security.
 * 
 * @author gaganthind
 *
 */
public class UserDetailsDTO implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 2898127193811921189L;

	private String password;
	
	private boolean passwordExpired;

	private String email;

	private boolean accountLocked;
	
	private boolean activeSw;
	
	private Set<UserRoleDTO> userRole = new HashSet<>();
	
	public UserDetailsDTO() { }
	
	public UserDetailsDTO(User user) {
		this.password = user.getPassword();
		this.passwordExpired = checkIfPasswordExpired(user);
		this.email = user.getEmail();
		this.accountLocked = user.isAccountLocked() == ApplicationConstants.CHAR_Y;
		this.activeSw = user.isActive() == ApplicationConstants.CHAR_Y;
		this.userRole = UserHelperUtil.convertRoleToDTO(user.getUserRole());
	}

	public String getPassword() {
		return password;
	}

	public boolean isPasswordExpired() {
		return passwordExpired;
	}

	public String getEmail() {
		return email;
	}

	public boolean isAccountLocked() {
		return accountLocked;
	}

	public boolean isActive() {
		return activeSw;
	}

	public Set<UserRoleDTO> getUserRole() {
		return userRole;
	}
	
	private boolean checkIfPasswordExpired(User user) {
		return LocalDateTime.now().isAfter(user.getPasswordExpireDate());
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
		
		UserDetailsDTO other = (UserDetailsDTO) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	
}
