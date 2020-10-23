package in.gagan.base.framework.util;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Utility class for providing exception related helper methods.
 * 
 * @author gaganthind
 *
 */
public final class ExceptionHelperUtil {
	
	private ExceptionHelperUtil() { }
	
	/**
	 * This method returns new User name not found exception.
	 * 
	 * @param email - Email id to be included in exception
	 * @return UsernameNotFoundException - exception
	 */
	public static UsernameNotFoundException throwUserNotFound(String email) {
		StringBuilder message = new StringBuilder();
		message.append("Username/Email: ")
		  .append(email)
		  .append(" not found");
		return new UsernameNotFoundException(message.toString());
	}

}
