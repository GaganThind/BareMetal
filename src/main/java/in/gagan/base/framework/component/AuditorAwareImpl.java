package in.gagan.base.framework.component;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * This class is used to provide the principal/logged-in user name for database operation.
 * 
 * @author gaganthind
 *
 */
public class AuditorAwareImpl implements AuditorAware<String> {

	/**
	 * This method is  used to get the name of the current logged in user.
	 * 
	 * @return Optional<String> - Logged-in username
	 */
	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = null != auth ? ((User) auth.getPrincipal()).getUsername() : "System";
		return Optional.of(username);
	}

}
