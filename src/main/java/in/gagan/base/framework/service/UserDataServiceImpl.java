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
	
	@Override
	public User fetchUserByEmail(String email) throws UsernameNotFoundException {
		User user = this.userDAO.findUserByEmail(email)
								.orElseThrow(() -> ExceptionHelperUtil.throwUserNotFound(email));
		return user;
	}
	
	@Override
	public List<User> fetchAllUsers() {
		return (List<User>) this.userDAO.findAll().orElse(Collections.emptyList());
	}
	
	@Override
	public void saveUser(User user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		if (null == user.getPasswordExpireDate()) {
			user.setPasswordExpireDate(LocalDateTime.now().plusDays(this.passwordProps.getPasswordExpireDays()));
		}
		this.userDAO.save(user);
	}
	
	@Override
	public void updateUser(User user) {
		this.userDAO.save(user);
	}
	
	@Override
	public void deleteUser(String email) {
		User user = fetchUserByEmail(email);
		this.userDAO.delete(user);
	}
	
	@Override
	public void hardDeleteUser(String email) {
		User user = fetchUserByEmail(email);
		this.userDAO.hardDelete(user);
	}
	
	@Override
	public boolean isUserPresent(String email) {
		return this.userDAO.findUserByEmail(email).isPresent();
	}
	
}
