package in.gagan.base.framework.component;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
		String principal = null != auth ? (String) auth.getPrincipal() : null;
		String username = StringUtils.defaultIfEmpty(principal, "System");
		return Optional.of(username);
	}

}
