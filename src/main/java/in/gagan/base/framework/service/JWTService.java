package in.gagan.base.framework.service;

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
	
	public static final String IP = "IP";
	public static final String USER_AGENT = "USER_AGENT";
	
	/**
	 * Method used to get the username/email from the JWT token.
	 * 
	 * @param token - JWT token
	 * @return String - Username/Email
	 */
	public String getUsername(String token);
	
	/**
	 * Method used to get the expiration date of the JWT token.
	 * 
	 * @param token - JWT token
	 * @return Date - Expiration date
	 */
	public Date getExpirationDate(String token);
	
	/**
	 * Method used to get the roles from the JWT token.
	 * 
	 * @param token - JWT token
	 * @return Set<SimpleGrantedAuthority> - roles as SimpleGrantedAuthorities for Spring
	 */
	public Set<SimpleGrantedAuthority> getRoles(String token);
	
	/**
	 * Method used to check if JWT token has expired.
	 * 
	 * @param token - JWT token
	 * @return boolean - True if token has expired otherwise false
	 */
	public boolean isExpired(String token);
	
	/**
	 * Method used to check if JWT token is a valid token.
	 * 
	 * @param token - JWT token
	 * @return boolean - True if user is logged-in and the token has not expired
	 */
	public boolean isValidToken(String token);
	
	/**
	 * Method used to build a JWT token using authentication information.
	 * 
	 * @param authResult - Authentication object containing user details
	 * @return String - JWT token
	 * 
	 */
	public String buildToken(Authentication authResult);
	
	/**
	 * Method used to build a JWT token using authentication information, IP address and User agent.
	 * 
	 * @param authResult - Authentication object containing user details
	 * @return String - JWT token
	 * 
	 */
	public String buildTokenWithIpAndUserAgent(Authentication authResult, String ipAddress, String userAgent);
	
	/**
	 * Method used to get the IP address from the JWT token.
	 * 
	 * @param token - JWT token
	 * @return String - IP address of the user
	 */
	public String getIpAddress(String token);
	
	/**
	 * Method used to get the user agent from the JWT token.
	 * 
	 * @param token - JWT token
	 * @return String - User Agent of the user
	 */
	public String getUserAgent(String token);

}
