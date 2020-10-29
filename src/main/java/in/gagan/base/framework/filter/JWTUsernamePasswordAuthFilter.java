package in.gagan.base.framework.filter;

import static in.gagan.base.framework.constant.JWTSecurityConstants.HEADER_STRING;
import static in.gagan.base.framework.constant.JWTSecurityConstants.AUTHORITIES;

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
import in.gagan.base.framework.dto.UsernameAndPasswordAuthDTO;
import io.jsonwebtoken.Jwts;

public class JWTUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationmanager;
	private final JWTProps jwtProps;
	
	@Autowired
	public JWTUsernamePasswordAuthFilter(AuthenticationManager authenticationmanager, JWTProps jwtProps) {
		this.authenticationmanager = authenticationmanager;
		this.jwtProps = jwtProps;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
			throws AuthenticationException {
		
		UsernameAndPasswordAuthDTO usernamePasswordAuth = null;

		try {
			usernamePasswordAuth = new ObjectMapper().readValue(request.getInputStream(), UsernameAndPasswordAuthDTO.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		final String username = usernamePasswordAuth.getUsername();
		final String password = usernamePasswordAuth.getPassword();
		
		Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authenticateUser = this.authenticationmanager.authenticate(auth);
		
		return authenticateUser;
		
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
			FilterChain chain, Authentication authResult) throws IOException, ServletException {
		
		String token = Jwts.builder()
							.setSubject(authResult.getName())
							.claim(AUTHORITIES, authResult.getAuthorities())
							.setIssuedAt(new Date())
							.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(this.jwtProps.getTokenExpirationInWeeks())))
							.signWith(this.jwtProps.getSecretKey())
							.compact();
		
		String headerString = new StringBuilder(this.jwtProps.getTokenPrefix()).append(token).toString();
		
		response.addHeader(HEADER_STRING, headerString);
		
	}
	
}
