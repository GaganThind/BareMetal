/*
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

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import in.gagan.base.framework.constant.ApplicationConstants;

/**
 * This class provides the verification token properties 
 * and the data is loaded from the properties file (verificationToken.properties)
 * 
 * @author gaganthind
 *
 */
@Component
@PropertySource(value = "classpath:verificationToken.properties")
public class VerificationTokenProps {
	
	@Value("${token.expiry}")
	private int VERIFICATION_TOKEN_EXPIRY_DAYS;
	
	public int getVerificationTokenExpiryDays() {
		return this.VERIFICATION_TOKEN_EXPIRY_DAYS;
	}
	
	@Value("${server.configured.send.email}")
	private String TOKEN_SERVER_CONFIGURED_TO_SEND_EMAIL;
	
	public boolean isServerConfiguredToSendEmail() {
		return ApplicationConstants.STRING_TRUE.equals(this.TOKEN_SERVER_CONFIGURED_TO_SEND_EMAIL);
	}
	
	/**
	 * Method to get the expiry date for newly created token from current time.
	 * 
	 * @return localDateTime verification token expiry time
	 */
	public LocalDateTime getExpiryTimeFromNow() {
		return LocalDateTime.now().plusDays(getVerificationTokenExpiryDays());
	}
	
}
