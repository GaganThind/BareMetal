package in.gagan.base.framework.service;

import org.springframework.security.core.Authentication;

public interface LoginAttemptService {

	public void loginFailed(Authentication auth);

	public void loginSucceeded(Authentication auth);

}
