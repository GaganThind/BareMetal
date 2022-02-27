/*
 * SpringBoot_Hibernate_Framework
 * 
 * Copyright (C) 2020-2022  Gagan Thind

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package in.gagan.base.framework.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import in.gagan.base.framework.service.LoginAttemptService;

/**
 * This listener class is used to provide functionality for authentication success.
 * 
 * @author gaganthind
 *
 */
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
