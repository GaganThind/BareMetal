package in.gagan.base.framework.util;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public final class ExceptionHelperUtil {
	
	private ExceptionHelperUtil() { }
	
	public static UsernameNotFoundException throwUserNotFound(String email) {
		return new UsernameNotFoundException("Username/Email: " + email + " not found");
	}

}
