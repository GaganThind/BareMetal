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

package in.gagan.base.framework.filter;

import static in.gagan.base.framework.constant.JWTSecurityConstants.BLANK;
import static in.gagan.base.framework.constant.JWTSecurityConstants.HEADER_STRING;

import java.io.IOException;
import java.util.Set;

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
import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.service.user.JWTService;

/**
 * This filter class is used to validate JWT token on all requests.
 * 
 * @author gaganthind
 *
 */
public class JWTTokenAuthenticationFilter extends BasicAuthenticationFilter {
	
	private final JWTProps jwtProps;
	private final JWTService jwtSvc;
	
	@Autowired
	public JWTTokenAuthenticationFilter(AuthenticationManager authenticationManager, JWTProps jwtProps, JWTService jwtSvc) {
		super(authenticationManager);
		this.jwtProps = jwtProps;
		this.jwtSvc = jwtSvc;
	}

	/**
	 * This method is used to do validation of the JWT token passed in request object.
	 * 
	 * @param request - contains request parameters
	 * @param response - contains response parameters
	 * @param filterChain - filter chain to execute other filters
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
			Authentication authentication = getAuthentication(authHeader, request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception ex) {
			throw new IllegalStateException("Token cannot be trusted. Hence invalidating token.");
			// TODO Add token to black list
		}

		filterChain.doFilter(request, response);

	}

	/**
	 * This method is used to get the authentication object by verifying user details.
	 * 
	 * @param authHeader - authentication token with type
	 * @param request - HttpRequest object
	 * @return Authentication - Authentication object
	 * @throws IllegalAccessException - If token is not valid, ip address has changed or user agent is different
	 */
	private Authentication getAuthentication(String authHeader, HttpServletRequest request) throws IllegalAccessException {
		
		String token = authHeader.replace(this.jwtProps.getTokenPrefix(), BLANK);
		
		String username = this.jwtSvc.getUsername(token);
		boolean isValidToken = this.jwtSvc.isValidToken(token);
		
		String ipAddressFromToken = this.jwtSvc.getIpAddress(token);
		String ipAddressFromRequest = StringUtils.defaultIfBlank(request.getHeader("X-FORWARDED-FOR"), request.getRemoteAddr());
		boolean isValidIpAddress = StringUtils.isNotBlank(ipAddressFromRequest) && StringUtils.equals(ipAddressFromToken, ipAddressFromRequest);
		
		String userAgentFromToken = this.jwtSvc.getUserAgent(token);
		String userAgentFromRequest = StringUtils.defaultIfBlank(request.getHeader("User-Agent"), ApplicationConstants.BLANK);
		boolean isValidUserAgent = StringUtils.isNotBlank(userAgentFromRequest) && StringUtils.equals(userAgentFromToken, userAgentFromRequest);
		
		if (!isValidToken || !isValidIpAddress || !isValidUserAgent) {
			throw new IllegalAccessException();
		}

		Set<SimpleGrantedAuthority> roles = this.jwtSvc.getRoles(token);

		return new UsernamePasswordAuthenticationToken(username, null, roles);
	}

}
