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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import in.gagan.base.framework.component.JWTProps;
import in.gagan.base.framework.constant.JWTSecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JWTTokenAuthenticationFilter extends OncePerRequestFilter {
	
	private final JWTProps jwtProps;
	
	@Autowired
	public JWTTokenAuthenticationFilter(JWTProps jwtProps) {
		this.jwtProps = jwtProps;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader(HEADER_STRING);
		
		if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith(this.jwtProps.getTokenPrefix())) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.replace(this.jwtProps.getTokenPrefix(), BLANK);

		try {
			
			Jws<Claims> claimsJws = Jwts.parserBuilder()
										.setSigningKey(this.jwtProps.getSecretKey())
										.build()
										.parseClaimsJws(token);

			Claims body = claimsJws.getBody();
			String username = body.getSubject();

			List<Map<String, String>> authorities = (List<Map<String, String>>) body.get(JWTSecurityConstants.AUTHORITIES);

			Set<SimpleGrantedAuthority> roles = authorities.stream()
															.map(role -> role.get(JWTSecurityConstants.AUTHORITIY))
															.map(SimpleGrantedAuthority::new)
															.collect(Collectors.toUnmodifiableSet());

			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, roles);

			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception ex) {
			throw new IllegalStateException("Token cannot be trusted");
		}

		filterChain.doFilter(request, response);

	}

}
