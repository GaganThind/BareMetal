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

import static in.gagan.base.framework.constant.JWTSecurityConstants.SINGLE_SPACE;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

/**
 * This class provides the jwt token details
 * and data is loaded from properties file (jwtToken.properties).
 * 
 * @author gaganthind
 *
 */
@Component
@PropertySource(value = { "classpath:jwtToken.properties" })
public class JWTProps {
	
	@Value("${secretKey}")
	private String secretKey;
	
	@Value("${tokenExpirationInWeeks}")
	private int tokenExpirationInWeeks;

	@Value("${tokenPrefix}")
	private String tokenPrefix;

	public SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}

	public int getTokenExpirationInWeeks() {
		return tokenExpirationInWeeks;
	}

	public String getTokenPrefix() {
		return new StringBuilder(tokenPrefix).append(SINGLE_SPACE).toString();
	}
	
}
