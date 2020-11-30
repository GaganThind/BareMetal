package in.gagan.base.framework.service;

import static in.gagan.base.framework.constant.JWTSecurityConstants.AUTHORITIES;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.component.JWTProps;
import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.constant.JWTSecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * This class provides the implementation of UserDataService interface and provides operations for User management functionality.
 * 
 * @author gaganthind
 *
 */
@Service
public class JWTServiceImpl implements JWTService {
	
	private final JWTProps jwtProps;
	
	@Autowired
	public JWTServiceImpl(JWTProps jwtProps) {
		this.jwtProps = jwtProps;
	}

	/**
	 * Method used to get the username/email from the JWT token.
	 * 
	 * @param token - JWT token
	 * @return String - Username/Email
	 */
	@Override
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	/**
	 * Method used to get the expiration date of the JWT token.
	 * 
	 * @param token - JWT token
	 * @return Date - Expiration date
	 */
	@Override
	public Date getExpirationDate(String token) {
		return getClaims(token).getExpiration();
	}

	/**
	 * Method used to get the roles from the JWT token.
	 * 
	 * @param token - JWT token
	 * @return Set<SimpleGrantedAuthority> - roles as SimpleGrantedAuthorities for Spring
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<SimpleGrantedAuthority> getRoles(String token) {
		List<Map<String, String>> authorities = (List<Map<String, String>>) getClaims(token).get(JWTSecurityConstants.AUTHORITIES);
		
		Set<SimpleGrantedAuthority> roles = authorities.stream()
														.map(role -> role.get(JWTSecurityConstants.AUTHORITIY))
														.map(SimpleGrantedAuthority::new)
														.collect(Collectors.toUnmodifiableSet());
		return roles;
	}

	/**
	 * Method used to check if JWT token has expired.
	 * 
	 * @param token - JWT token
	 * @return boolean - True if token has expired otherwise false
	 */
	@Override
	public boolean isExpired(String token) {
		Date expirationDate = getExpirationDate(token);
		return expirationDate.before(new Date());
	}

	/**
	 * Method used to check if JWT token is a valid token.
	 * 
	 * @param token - JWT token
	 * @return boolean - True if user is logged-in and the token has not expired
	 */
	@Override
	public boolean isValidToken(String token) {
		return null != getUsername(token) && !isExpired(token);
	}

	/**
	 * Method used to build a JWT token using authentication information.
	 * 
	 * @param authResult - Authentication object containing user details
	 * @return String - JWT token
	 * 
	 */
	@Override
	public String buildToken(Authentication authResult) {
		String token = Jwts.builder()
				.setSubject(authResult.getName())
				.claim(AUTHORITIES, authResult.getAuthorities())
				.setIssuedAt(new Date())
				.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(this.jwtProps.getTokenExpirationInWeeks())))
				.signWith(this.jwtProps.getSecretKey())
				.compact();
		return token;
	}
	
	/**
	 * Method used to build a JWT token using authentication information, IP address and User agent.
	 * 
	 * @param authResult - Authentication object containing user details
	 * @return String - JWT token
	 * 
	 */
	@Override
	public String buildTokenWithIpAndUserAgent(Authentication authResult, String ipAddress, String userAgent) {
		String token = Jwts.builder()
				.setSubject(authResult.getName())
				.claim(AUTHORITIES, authResult.getAuthorities())
				.claim(IP, ipAddress)
				.claim(USER_AGENT, userAgent)
				.setIssuedAt(new Date())
				.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(this.jwtProps.getTokenExpirationInWeeks())))
				.signWith(this.jwtProps.getSecretKey())
				.compact();
		return token;
	}
	
	/**
	 * Method used to get the IP address from the JWT token.
	 * 
	 * @param token - JWT token
	 * @return String - IP address of the user
	 */
	public String getIpAddress(String token) {
		return (String) getClaims(token).getOrDefault(IP, ApplicationConstants.BLANK);
	}
	
	/**
	 * Method used to get the user agent from the JWT token.
	 * 
	 * @param token - JWT token
	 * @return String - User Agent of the user
	 */
	public String getUserAgent(String token) {
		return (String) getClaims(token).getOrDefault(USER_AGENT, ApplicationConstants.BLANK);
	}
	
	/**
	 * This method based on the JWT token gets the user details.
	 * 
	 * @param authHeader - JWT token with prefix
	 * @return Jws<Claims> - Claims details
	 */
	private Claims getClaims(String token) {
		Claims claims = Jwts.parserBuilder()
									.setSigningKey(this.jwtProps.getSecretKey())
									.build()
									.parseClaimsJws(token)
									.getBody();
		return claims;
	}

}
