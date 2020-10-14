package in.gagan.base.framework.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
	
    private final LoginAttemptService loginAttemptSvc;
    
    @Autowired
    public AuthenticationFailureListener(LoginAttemptService loginAttemptSvc) {
		this.loginAttemptSvc = loginAttemptSvc;
	}

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		Authentication auth = event.getAuthentication();
		this.loginAttemptSvc.loginFailed(auth);
	}

}
