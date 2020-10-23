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

	@Override
	public void createAdminUser() {
		User user = this.adminAccount.getAdminDetails();
		
		if (!this.userDataService.isUserPresent(user.getEmail())) {
			this.userDataService.updateUser(user);
		}
	}
	
	@Override
	public List<UserDTO> fetchAllUsers() {
		List<User> users = this.userDataService.fetchAllUsers();
		return users.stream()
					.map(UserHelperUtil::convertUserToUserDTO)
					.collect(Collectors.toList());
	}
	
}
