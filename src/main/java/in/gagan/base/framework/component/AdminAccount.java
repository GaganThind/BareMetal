package in.gagan.base.framework.component;

import static in.gagan.base.framework.enums.UserRoles.ADMIN;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import in.gagan.base.framework.entity.Role;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.enums.Gender;

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
	
	public User getAdminDetails() {
		User user = new User(firstName, lastName, email, LocalDate.parse(dob), Gender.valueOf(gender), password);
		user.setPasswordExpireDate(LocalDateTime.now().plusDays(Integer.valueOf(passwordExp)));
		user.addRole(new Role(ADMIN));
		return user;
	}
}
