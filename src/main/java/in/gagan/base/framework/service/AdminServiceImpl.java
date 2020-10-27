package in.gagan.base.framework.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.component.AdminAccount;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.util.UserHelperUtil;

/**
 * This class provides the implementation of AdminService interface and provides operations for Admin functionality.
 * 
 * @author gaganthind
 *
 */
@Transactional
@Service
public class AdminServiceImpl implements AdminService {
	
	private final AdminAccount adminAccount;
	
	private final UserDataService userDataService;
	
	@Autowired
	public AdminServiceImpl(AdminAccount adminAccount, UserDataService userDataService) {
		this.adminAccount = adminAccount;
		this.userDataService = userDataService;
	}

	/**
	 * Method used to create Admin account on application startup.
	 */
	@Override
	public void createAdminUser() {
		User user = this.adminAccount.getAdminDetails();
		
		if (!this.userDataService.isUserPresent(user.getEmail())) {
			this.userDataService.updateUser(user);
		}
	}
	
	/**
	 * Method used to fetch all the users of the application.
	 * 
	 * @return List<UserDTO> - list of all users
	 */
	@Override
	public List<UserDTO> fetchAllUsers() {
		List<User> users = this.userDataService.fetchAllUsers();
		return users.stream()
					.map(UserHelperUtil::convertUserToUserDTO)
					.collect(Collectors.toList());
	}
	
}
