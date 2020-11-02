package in.gagan.base.framework.config;

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

import static in.gagan.base.framework.enums.UserRoles.*;

import in.gagan.base.framework.component.JWTProps;
import in.gagan.base.framework.filter.JWTTokenAuthenticationFilter;
import in.gagan.base.framework.filter.JWTUsernamePasswordAuthFilter;
import in.gagan.base.framework.service.CustomUserDetailsService;

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
	
	@Autowired
	public SpringSecurityConfig(CustomUserDetailsService userDetailsSvc, JWTProps jwtProps) {
		this.userDetailsSvc = userDetailsSvc;
		this.jwtProps = jwtProps;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsSvc).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.addFilter(new JWTUsernamePasswordAuthFilter(authenticationManager(), jwtProps))
			.addFilter(new JWTTokenAuthenticationFilter(authenticationManager(), jwtProps))
			.authorizeRequests()
	            .antMatchers("/v1/users/register/**", "/h2/**").permitAll()
	            .antMatchers(HttpMethod.GET, "/v1/users/**").hasAnyRole(ADMIN_BASIC.name(), USER_BASIC.name(), ADMIN.name(), USER.name())
	            .antMatchers("/v1/users/**").hasAnyRole(ADMIN.name(), USER.name())
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

}
