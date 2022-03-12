/*
 * SpringBoot_Hibernate_Framework
 * 
 * Copyright (C) 2020-2022  Gagan Thind

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
			return other.roleName == null;
		} else return roleName.equals(other.roleName);
	}
	
}
