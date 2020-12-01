package in.gagan.base.framework.dto;

import java.io.Serializable;

import in.gagan.base.framework.enums.UserRoles;

/**
 * This DTO captures the user role details from role entity and is used for internal data transfer.
 * 
 * @author gaganthind
 *
 */
public class UserRoleDTO implements Serializable {
	
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -807666354682392231L;
	private UserRoles roleName;
	
	public UserRoleDTO() { }
	
	public UserRoleDTO(UserRoles roleName) {
		this.roleName = roleName;
	}

	public UserRoles getRoleName() {
		return roleName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
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
		UserRoleDTO other = (UserRoleDTO) obj;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		return true;
	}
	
}
