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

package in.gagan.base.framework.service.user;

import java.util.Date;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * This Service interface provides the basic operations for JWT implementation.
 * 
 * @author gaganthind
 *
 */
public interface JWTService {
	
	String IP = "IP";
	String USER_AGENT = "USER_AGENT";
	
	/**
	 * Method used to get the username/email from the JWT token.
	 * 
	 * @param token - JWT token
	 * @return String - Username/Email
	 */
	String getUsername(String token);
	
	/**
	 * Method used to get the expiration date of the JWT token.
	 * 
	 * @param token - JWT token
	 * @return Date - Expiration date
	 */
	Date getExpirationDate(String token);
	
	/**
	 * Method used to get the roles from the JWT token.
	 * 
	 * @param token - JWT token
	 * @return Set<SimpleGrantedAuthority> - roles as SimpleGrantedAuthorities for Spring
	 */
	Set<SimpleGrantedAuthority> getRoles(String token);
	
	/**
	 * Method used to check if JWT token has expired.
	 * 
	 * @param token - JWT token
	 * @return boolean - True if token has expired otherwise false
	 */
	boolean isExpired(String token);
	
	/**
	 * Method used to check if JWT token is a valid token.
	 * 
	 * @param token - JWT token
	 * @return boolean - True if user is logged-in and the token has not expired
	 */
	boolean isValidToken(String token);
	
	/**
	 * Method used to build a JWT token using authentication information.
	 * 
	 * @param authResult - Authentication object containing user details
	 * @return String - JWT token
	 * 
	 */
	String buildToken(Authentication authResult);
	
	/**
	 * Method used to build a JWT token using authentication information, IP address and User agent.
	 * 
	 * @param authResult - Authentication object containing user details
	 * @return String - JWT token
	 * 
	 */
	String buildToken(Authentication authResult, String ipAddress, String userAgent);
	
	/**
	 * Method used to get the IP address from the JWT token.
	 * 
	 * @param token - JWT token
	 * @return String - IP address of the user
	 */
	String getIpAddress(String token);
	
	/**
	 * Method used to get the user agent from the JWT token.
	 * 
	 * @param token - JWT token
	 * @return String - User Agent of the user
	 */
	String getUserAgent(String token);

}
