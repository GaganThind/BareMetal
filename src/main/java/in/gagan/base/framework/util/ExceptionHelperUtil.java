package in.gagan.base.framework.util;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public final class ExceptionHelperUtil {
	
	private ExceptionHelperUtil() { }
	
	public static UsernameNotFoundException throwUserNotFound(String email) {
		StringBuilder message = new StringBuilder();
		message.append("Username/Email: ")
		  .append(email)
		  .append(" not found");
		return new UsernameNotFoundException(message.toString());
	}

}
