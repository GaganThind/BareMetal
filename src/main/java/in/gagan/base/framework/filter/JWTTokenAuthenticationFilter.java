package in.gagan.base.framework.filter;

import static in.gagan.base.framework.constant.JWTSecurityConstants.BLANK;
import static in.gagan.base.framework.constant.JWTSecurityConstants.HEADER_STRING;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import in.gagan.base.framework.component.JWTProps;
import in.gagan.base.framework.constant.JWTSecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

/**
 * This filter class is used to validate JWT token on all requests.
 * 
 * @author gaganthind
 *
 */
public class JWTTokenAuthenticationFilter extends BasicAuthenticationFilter {
	
	private final JWTProps jwtProps;
	
	@Autowired
	public JWTTokenAuthenticationFilter(AuthenticationManager authenticationManager, JWTProps jwtProps) {
		super(authenticationManager);
		this.jwtProps = jwtProps;
	}

	/**
	 * This method is used to do validation of the JWT token passed in request object.
	 * 
	 * @param request - contains request parameters
	 * @param response - contains response parameters
	 * @param chain - filter chain to execute other filters
	 * @exception ServletException - Servlet exception
	 * @exception IOException - IO exception
	 * 
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader(HEADER_STRING);
		
		if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith(this.jwtProps.getTokenPrefix())) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			Authentication authentication = getAuthentication(authHeader);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception ex) {
			throw new IllegalStateException("Token cannot be trusted");
		}

		filterChain.doFilter(request, response);

	}

	/**
	 * This method is used to get the authentication object by verifying user details.
	 * 
	 * @param authHeader - authentication token with type
	 * @return Authentication - Authentication object
	 */
	@SuppressWarnings("unchecked")
	private Authentication getAuthentication(String authHeader) {
		
		Jws<Claims> claimsJws = getClaims(authHeader);
		Claims body = claimsJws.getBody();
		String username = body.getSubject();

		List<Map<String, String>> authorities = (List<Map<String, String>>) body.get(JWTSecurityConstants.AUTHORITIES);

		Set<SimpleGrantedAuthority> roles = authorities.stream()
														.map(role -> role.get(JWTSecurityConstants.AUTHORITIY))
														.map(SimpleGrantedAuthority::new)
														.collect(Collectors.toUnmodifiableSet());

		Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, roles);
		return authentication;
	}

	/**
	 * This method based on the JWT token gets the user details.
	 * 
	 * @param authHeader - JWT token with prefix
	 * @return Jws<Claims> - Claims details
	 */
	private Jws<Claims> getClaims(String authHeader) {
		
		String token = authHeader.replace(this.jwtProps.getTokenPrefix(), BLANK);
		
		Jws<Claims> claimsJws = Jwts.parserBuilder()
									.setSigningKey(this.jwtProps.getSecretKey())
									.build()
									.parseClaimsJws(token);
		return claimsJws;
	}

}
