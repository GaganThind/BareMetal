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

package in.gagan.base.framework.dto.user;

import in.gagan.base.framework.enums.UserRoles;

import java.util.Objects;

/**
 * This DTO captures the user role details from role entity and is used for internal data transfer.
 * 
 * @author gaganthind
 *
 */
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserRoleDTO that = (UserRoleDTO) o;
		return roleName == that.roleName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(roleName);
	}
}
