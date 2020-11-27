package in.gagan.base.framework.component;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.AuditorAware;

import in.gagan.base.framework.util.UserHelperUtil;

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
		String principal = UserHelperUtil.loggedInUser();
		String username = StringUtils.defaultIfBlank(principal, "System");
		return Optional.of(username);
	}

}
