package in.gagan.base.framework.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.component.PasswordProps;
import in.gagan.base.framework.dao.UserDAO;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.util.ExceptionHelperUtil;

/**
 * This class provides the implementation of UserDataService interface and provides operations for User management functionality.
 * 
 * @author gaganthind
 *
 */
@Transactional
@Service
public class UserDataServiceImpl implements UserDataService {
	
	private final UserDAO userDAO;
	
	private final PasswordEncoder passwordEncoder;
	
	private final PasswordProps passwordProps;
	
	@Autowired
	public UserDataServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder, PasswordProps passwordProps) {
		this.userDAO = userDAO;
		this.passwordEncoder = passwordEncoder;
		this.passwordProps = passwordProps;
	}
	
	/**
	 * This method is used to fetch user data based on provided email.
	 * 
	 * @param email - Email address of the user
	 * @return User - User object from the database
	 * @throws UsernameNotFoundException - If user with the provided email doesn't exist 
	 */
	@Override
	public User fetchUserByEmail(String email) throws UsernameNotFoundException {
		User user = this.userDAO.findUserByEmail(email)
								.orElseThrow(() -> ExceptionHelperUtil.throwUserNotFound(email));
		return user;
	}
	
	/**
	 * This method is used to fetch the details of all the user existing in the system.
	 * 
	 * @return List<User> - list of users
	 */
	@Override
	public List<User> fetchAllUsers() {
		return (List<User>) this.userDAO.findAll().orElse(Collections.emptyList());
	}
	
	/**
	 * This method is used to save the User record.
	 * 
	 * @param user - user record
	 */
	@Override
	public void saveUser(User user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		if (null == user.getPasswordExpireDate()) {
			user.setPasswordExpireDate(LocalDateTime.now().plusDays(this.passwordProps.getPasswordExpireDays()));
		}
		this.userDAO.save(user);
	}
	
	/**
	 * This method is used to update the User record.
	 * 
	 * @param user - user record
	 */
	@Override
	public void updateUser(User user) {
		this.userDAO.save(user);
	}
	
	/**
	 * This method is used to soft delete the User record.
	 * 
	 * @param email - email address of user
	 */
	@Override
	public void deleteUser(String email) {
		User user = fetchUserByEmail(email);
		this.userDAO.delete(user);
	}
	
	/**
	 * This method is used to hard delete the User record.
	 * 
	 * @param email - email address of user
	 */
	@Override
	public void hardDeleteUser(String email) {
		User user = fetchUserByEmail(email);
		this.userDAO.hardDelete(user);
	}
	
	/**
	 * This method is used to check if a User record exists with the provided email.
	 * 
	 * @param email - email address of user
	 * @return boolean - if user is present in the system
	 */
	@Override
	public boolean isUserPresent(String email) {
		return this.userDAO.findUserByEmail(email).isPresent();
	}
	
}
