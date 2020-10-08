package in.gagan.base.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static in.gagan.base.framework.enums.UserRoles.*;
import in.gagan.base.framework.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final CustomUserDetailsService userDetailsSvc;
	
	@Autowired
	public SpringSecurityConfig(CustomUserDetailsService userDetailsSvc) {
		this.userDetailsSvc = userDetailsSvc;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsSvc).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
            .antMatchers("/v1/register", "/h2-console/**").permitAll()
            .antMatchers(HttpMethod.GET, "/v1/user/**").hasAnyRole(ADMIN_BASIC.name(), USER_BASIC.name(), ADMIN.name(), USER.name())
            .antMatchers("/v1/user/**").hasAnyRole(ADMIN.name(), USER.name())
            .antMatchers(HttpMethod.GET,"/v1/admin/**").hasAnyRole(ADMIN.name(), ADMIN_BASIC.name())
            .antMatchers("/v1/admin/**").hasRole(ADMIN.name())
			.antMatchers("/**").authenticated()
			.anyRequest().authenticated()
		.and()
			.httpBasic()
		.and()
			.logout().permitAll()
		.and()
			.csrf().disable();
		
		http.headers().frameOptions().disable(); // Makes h2-console to work 
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
