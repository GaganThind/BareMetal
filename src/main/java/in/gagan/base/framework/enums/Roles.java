package in.gagan.base.framework.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static in.gagan.base.framework.enums.Permissions.*;

public enum Roles {
	
	// Admin
	ADMIN(new HashSet<>(Arrays.asList(ADMIN_WRITE, ADMIN_READ, USER_WRITE, USER_READ))),
	ADMIN_BASIC(new HashSet<>(Arrays.asList(ADMIN_READ, USER_READ))),
	
	// User
	USER(new HashSet<>(Arrays.asList(USER_WRITE, USER_READ))),
	USER_READ_ONLY(new HashSet<>(Arrays.asList(USER_READ))),
	
	// Empty
	EMPTY(null);
	
	private final Set<Permissions> permissions;
	
	Roles(Set<Permissions> permissions) {
		this.permissions = permissions; 
	}
	
	public Set<Permissions> getPermissions() {
		return this.permissions;
	}
}
