package in.gagan.base.framework.enums;

public enum Permissions {
	ADMIN_WRITE("admin:write"),
	ADMIN_READ("admin:read"),
	USER_WRITE("user:write"),
	USER_READ("user:read");
	
	private final String permission;
	
	Permissions(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return this.permission;
	}
}
