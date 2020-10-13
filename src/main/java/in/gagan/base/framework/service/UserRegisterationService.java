package in.gagan.base.framework.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.component.AdminAccount;
import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.exception.UsernameExistException;
import in.gagan.base.framework.util.UserHelperUtil;

@Transactional
@Service
public class UserRegisterationService {
	
	private final AdminAccount adminAccount;
	
	private final UserDataService userDataService;
	
	@Autowired
	public UserRegisterationService(AdminAccount adminAccount, UserDataService userDataService) {
		this.adminAccount = adminAccount;
		this.userDataService = userDataService;
	}
	
	public void createAdminUser() {
		User user = this.adminAccount.getAdminDetails();
		
		if (!this.userDataService.isUserPresent(user.getEmail())) {
			this.userDataService.saveUser(user);
		}
	}
	
	public String registerNewUser(UserDTO user) throws UsernameExistException {
		UserDataValidator.validateUserDTO(user);
		
		if (this.userDataService.isUserPresent(user.getEmail())) {
			throw new UsernameExistException(user.getEmail());
		}
		
		insertUser(user);
		
		return user.getUsername();
	}
	
	public UserDTO fetchUser(String email) {
		UserDataValidator.validateEmail(email);
		UserDTO userDTO = new UserDTO();
		User user = this.userDataService.fetchUserByEmail(email);
		UserHelperUtil.convertUserToUserDTO(user, userDTO);
		return userDTO;
	}
	
	public UserDTO updateOrCreateUser(UserDTO user) {
		if (this.userDataService.isUserPresent(user.getEmail())) {
			UserDataValidator.validateUserDTOforUpdate(user);
			updateUser(user);
		} else {
			UserDataValidator.validateUserDTO(user);
			insertUser(user);
		}
		
		return user;
	}
	
	public void deleteUser(String email) {
		UserDataValidator.validateEmail(email);
		this.userDataService.deleteUser(email);
	}
	
	public void hardDeleteUser(String email) {
		UserDataValidator.validateEmail(email);
		this.userDataService.hardDeleteUser(email);
	}
	
	private void insertUser(UserDTO userDTO) {
		User user = new User();
		UserHelperUtil.convertUserDTOToUser(userDTO, user);
		this.userDataService.saveUser(user);
	}
	
	private void updateUser(UserDTO userDTO) {
		User user = new User();
		UserHelperUtil.convertUserDTOToUser(userDTO, user);
		this.userDataService.updateUser(user);
	}
	
}
