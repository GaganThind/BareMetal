package in.gagan.base.framework.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import in.gagan.base.framework.service.LoginAttemptService;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
	
	private final LoginAttemptService loginAttemptSvc;
    
    @Autowired
    public AuthenticationSuccessListener(LoginAttemptService loginAttemptSvc) {
		this.loginAttemptSvc = loginAttemptSvc;
	}

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		Authentication auth = event.getAuthentication();
		this.loginAttemptSvc.loginSucceeded(auth);
	}

}
