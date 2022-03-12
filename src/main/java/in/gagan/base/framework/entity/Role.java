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

package in.gagan.base.framework.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.entity.base.AuditableEntity;
import in.gagan.base.framework.entity.base.BaseEntity;
import in.gagan.base.framework.enums.UserRoles;

/**
 * Entity representing the ROLES table 
 * 
 * @author gaganthind
 *
 */
@Entity
@Table(name="ROLES")
public class Role extends AuditableEntity implements BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ROLE_ID", nullable = false, unique = true, length = 10)
	private long roleId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ROLE_NAME", nullable = false)
	private UserRoles roleName;
	
	public Role() {
		super(ApplicationConstants.CHAR_Y);
	}
	
	public Role(UserRoles roleName) {
		super(ApplicationConstants.CHAR_Y);
		this.roleName = roleName;
	}
	
	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public UserRoles getRoleName() {
		return roleName;
	}

	public void setRoleName(UserRoles roleName) {
		this.roleName = roleName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (roleId ^ (roleId >>> 32));
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
		Role other = (Role) obj;
		if (roleId != other.roleId)
			return false;
		if (roleName == null) {
			return other.roleName == null;
		} else return roleName.equals(other.roleName);
	}
	
}
