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

package in.gagan.base.framework.component;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

/**
 * This class is used to provide meaningful exception messages incase of login failure.
 * 
 * @author gaganthind
 *
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private final MessageSource messages;

	private final LocaleResolver localeResolver;
	
	@Autowired
	public CustomAuthenticationFailureHandler(MessageSource messages, LocaleResolver localeResolver) {
		this.messages = messages;
		this.localeResolver = localeResolver;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		super.onAuthenticationFailure(request, response, exception);

		Locale locale = localeResolver.resolveLocale(request);

		String errorMessage = messages.getMessage("message.login.credentials.bad", null, locale);

		if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
			errorMessage = messages.getMessage("message.login.user.inactive", null, locale);
		} else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
			errorMessage = messages.getMessage("message.login.user.expired", null, locale);
		} else if (exception instanceof UsernameNotFoundException) {
			errorMessage = exception.getMessage();
		}

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        
        try (PrintWriter writer = response.getWriter()) {	
            writer.write(errorMessage);
			writer.flush();
        } catch (IOException ie) {
            // Nothing
        }
        
	}

}
