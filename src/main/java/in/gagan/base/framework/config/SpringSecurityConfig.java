package in.gagan.base.framework.config;

import static in.gagan.base.framework.enums.UserRoles.ADMIN;
import static in.gagan.base.framework.enums.UserRoles.ADMIN_BASIC;
import static in.gagan.base.framework.enums.UserRoles.USER;
import static in.gagan.base.framework.enums.UserRoles.USER_BASIC;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import in.gagan.base.framework.component.CustomAuthenticationFailureHandler;
import in.gagan.base.framework.component.JWTProps;
import in.gagan.base.framework.filter.JWTTokenAuthenticationFilter;
import in.gagan.base.framework.filter.JWTUsernamePasswordAuthFilter;
import in.gagan.base.framework.service.CustomUserDetailsService;
import in.gagan.base.framework.service.JWTService;

/**
 * This class provides the spring security configurations.
 * 
 * @author gaganthind
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final CustomUserDetailsService userDetailsSvc;
	private final JWTProps jwtProps;
	private final JWTService jwtSvc;
	private final CustomAuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	public SpringSecurityConfig(CustomUserDetailsService userDetailsSvc, JWTProps jwtProps, JWTService jwtSvc, CustomAuthenticationFailureHandler authenticationFailureHandler) {
		this.userDetailsSvc = userDetailsSvc;
		this.jwtProps = jwtProps;
		this.jwtSvc = jwtSvc;
		this.authenticationFailureHandler = authenticationFailureHandler;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsSvc).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors()
		.and()
			.csrf().disable()
		.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.addFilter(jwtUsernamePasswordAuthFilter())
			.addFilter(new JWTTokenAuthenticationFilter(authenticationManager(), this.jwtProps, this.jwtSvc))
			.authorizeRequests()
	            .antMatchers("/login", "/v1/users/register/**", "/h2/**", "/v1/password/forgot/**").permitAll()
	            .antMatchers(HttpMethod.GET, "/v1/users/**").hasAnyRole(ADMIN_BASIC.name(), USER_BASIC.name(), ADMIN.name(), USER.name())
	            .antMatchers("/v1/users/**").hasAnyRole(ADMIN.name(), USER.name())
	            .antMatchers("/v1/password/**").hasAnyRole(ADMIN_BASIC.name(), USER_BASIC.name(), ADMIN.name(), USER.name())
	            .antMatchers(HttpMethod.GET,"/v1/admin/**").hasAnyRole(ADMIN.name(), ADMIN_BASIC.name())
	            .antMatchers("/v1/admin/**").hasRole(ADMIN.name())
				.antMatchers("/**").authenticated()
				.anyRequest().authenticated();
		
		http.headers().frameOptions().disable(); // Makes h2-console to work 
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CorsFilter corsFilter() {
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
	    config.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
	    config.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
	    config.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), 
	    		HttpMethod.OPTIONS.name(), HttpMethod.DELETE.name(), HttpMethod.PATCH.name()));

	    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
	    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", config);
	   
	    return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	public JWTUsernamePasswordAuthFilter jwtUsernamePasswordAuthFilter() throws Exception {
		JWTUsernamePasswordAuthFilter jwtUsernamePasswordAuthFilter = new JWTUsernamePasswordAuthFilter(authenticationManager(), this.jwtProps, this.jwtSvc);
		jwtUsernamePasswordAuthFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
		return jwtUsernamePasswordAuthFilter;
	}

}
