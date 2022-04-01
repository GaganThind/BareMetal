/*
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

package in.gagan.base.framework.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static in.gagan.base.framework.enums.UserPermissions.*;

/**
 * This enum is used to represent the user Roles.
 * 
 * @author gaganthind
 *
 */
public enum UserRoles {
	
	// Admin
	ADMIN(new HashSet<>(Arrays.asList(ADMIN_WRITE, ADMIN_READ, USER_WRITE, USER_READ))),
	ADMIN_BASIC(new HashSet<>(Arrays.asList(ADMIN_READ, USER_READ))),
	
	// User
	USER(new HashSet<>(Arrays.asList(USER_WRITE, USER_READ))),
	USER_BASIC(new HashSet<>(List.of(USER_READ))),
	
	// Empty
	EMPTY(new HashSet<>());
	
	private final Set<UserPermissions> permissions;
	
	UserRoles(Set<UserPermissions> permissions) {
		this.permissions = permissions; 
	}
	
	public Set<UserPermissions> getPermissions() {
		return this.permissions;
	}
}
