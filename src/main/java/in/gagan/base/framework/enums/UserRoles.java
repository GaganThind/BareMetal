package in.gagan.base.framework.enums;

import java.util.Arrays;
import java.util.HashSet;
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
	USER_BASIC(new HashSet<>(Arrays.asList(USER_READ))),
	
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
