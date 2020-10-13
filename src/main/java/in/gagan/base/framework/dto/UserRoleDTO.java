package in.gagan.base.framework.dto;

import in.gagan.base.framework.enums.UserRoles;

public class UserRoleDTO {
	
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
