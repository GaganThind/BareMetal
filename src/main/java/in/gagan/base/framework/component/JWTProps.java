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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

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
	
	@Value("${public.key}")
	private RSAPublicKey publicKey;

	@Value("${private.key}")
	private RSAPrivateKey privateKey;

	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}
}
