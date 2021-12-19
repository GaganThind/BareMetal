package in.gagan.base.framework.component;

import static in.gagan.base.framework.enums.UserRoles.ADMIN;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.entity.Role;
import in.gagan.base.framework.entity.User;

/**
 * This class provides the admin account details 
 * and data is loaded from properties file (admin.properties).
 * 
 * The admin account is created during application startup
 * 
 * @author gaganthind
 *
 */
@Component
@PropertySource(value = { "classpath:admin.properties" })
public class AdminAccount {
	
	@Value("${email}")
	private String email;
	
	@Value("${first.name}")
	private String firstName;
	
	@Value("${last.name}")
	private String lastName;
	
	@Value("${dob}")
	private String dob;
	
	@Value("${gender}")
	private String gender;
	
	@Value("${password}")
	private String password;
	
	@Value("${password.expiry}")
	private String passwordExp;
	
	/**
	 * @return User - The user entity object for admin to be persisted
	 */
	public User getAdminDetails() {
		User user = new User(firstName, lastName, email, password);
		user.setPasswordExpireDate(LocalDateTime.now().plusDays(Integer.valueOf(passwordExp)));
		user.setActiveSw(ApplicationConstants.CHAR_Y);
		user.addRole(new Role(ADMIN));
		return user;
	}
}
