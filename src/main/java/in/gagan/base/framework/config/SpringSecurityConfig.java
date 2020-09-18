package in.gagan.base.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import in.gagan.base.framework.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomUserDetailsService userDetailsSvc;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsSvc).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
            .antMatchers("/v1/register", "/h2-console/**").permitAll()
			.antMatchers("/v1/**").authenticated()
		.and()
			.httpBasic()
		.and()
			.logout().permitAll()
		.and()
			.csrf().disable();
		
		http.headers().frameOptions().disable();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
