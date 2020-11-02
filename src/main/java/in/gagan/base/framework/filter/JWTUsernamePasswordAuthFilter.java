package in.gagan.base.framework.filter;

import static in.gagan.base.framework.constant.JWTSecurityConstants.AUTHORITIES;
import static in.gagan.base.framework.constant.JWTSecurityConstants.HEADER_STRING;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.gagan.base.framework.component.JWTProps;
import in.gagan.base.framework.dto.UsernamePasswordAuthDTO;
import io.jsonwebtoken.Jwts;

/**
 * This filter class is used to provide authentication of credentials and creation of JWT token.
 * 
 * @author gaganthind
 *
 */
public class JWTUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationmanager;
	private final JWTProps jwtProps;
	
	@Autowired
	public JWTUsernamePasswordAuthFilter(AuthenticationManager authenticationmanager, JWTProps jwtProps) {
		this.authenticationmanager = authenticationmanager;
		this.jwtProps = jwtProps;
	}
	
	/**
	 * This method captures the username and password details and perform the authentication of the user.
	 * 
	 * @param request - contains request parameters
	 * @param response - contains response parameters
	 * @throws AuthenticationException - If the user is not authenticated
	 * 
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		UsernamePasswordAuthDTO usernamePasswordAuth = getUsernamePasswordAuthDTO(request);
		
		String username = usernamePasswordAuth.getUsername();
		String password = usernamePasswordAuth.getPassword();
		
		Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authenticateUser = this.authenticationmanager.authenticate(auth);
		
		return authenticateUser;
		
	}

	/**
	 * This method is called on the successful authentication of the user credentials and then generates a unique JWT token.
	 * 
	 * @param request - contains request parameters
	 * @param response - contains response parameters
	 * @param chain - filter chain to execute other filters
	 * @param authResult - Authentication object containing user details
	 * @exception IOException - IO exception
	 * @exception ServletException - Servlet exception
	 *  
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
			FilterChain chain, Authentication authResult) throws IOException, ServletException {
		
		String token = buildToken(authResult);
		String tokenPrefix = this.jwtProps.getTokenPrefix();
		String headerString = new StringBuilder(tokenPrefix).append(token).toString();
		
		response.addHeader(HEADER_STRING, headerString);
		
	}

	/**
	 * This method gets the username and password form request object and sets in UsernamePasswordAuthDTO. 
	 * 
	 * @param request - contains request parameters
	 * @return UsernamePasswordAuthDTO - Username and Password in DTO object
	 */
	private UsernamePasswordAuthDTO getUsernamePasswordAuthDTO(HttpServletRequest request) {
		UsernamePasswordAuthDTO usernamePasswordAuth = null;
		try {
			usernamePasswordAuth = new ObjectMapper().readValue(request.getInputStream(), UsernamePasswordAuthDTO.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return usernamePasswordAuth;
	}
	
	/**
	 * This method is used to build a JWT token using authentication information.
	 * 
	 * @param authResult - Authentication object containing user details
	 * @return String - JWT token
	 * 
	 */
	private String buildToken(Authentication authResult) {
		String token = Jwts.builder()
							.setSubject(authResult.getName())
							.claim(AUTHORITIES, authResult.getAuthorities())
							.setIssuedAt(new Date())
							.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(this.jwtProps.getTokenExpirationInWeeks())))
							.signWith(this.jwtProps.getSecretKey())
							.compact();
		return token;
	}
	
}
