package in.gagan.base.framework.component;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.entity.Role;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.entity.UserSecurity;
import in.gagan.base.framework.enums.Roles;

@Component
public class AdminAccount {
	
	@Value("${application.admin.email}")
	private String email;
	
	@Value("${application.admin.first.name}")
	private String firstName;
	
	@Value("${application.admin.last.name}")
	private String lastName;
	
	@Value("${application.admin.dob}")
	private String dob;
	
	@Value("${application.admin.gender}")
	private String gender;
	
	@Value("${application.admin.password}")
	private String password;
	
	@Value("${application.admin.password.expiry}")
	private String passwordExp;
	
	public UserSecurity getAdminDetails() {
		User user = new User(firstName, lastName, email, LocalDate.parse(dob), gender.charAt(0));
		
		UserSecurity userSecurity = new UserSecurity(password, LocalDateTime.now().plusDays(Integer.valueOf(passwordExp)), 
				(short) 0, ApplicationConstants.CHAR_N, null, user);
		userSecurity.addRole(new Role(Roles.ADMIN.name()));
		
		return userSecurity;
	}
}
