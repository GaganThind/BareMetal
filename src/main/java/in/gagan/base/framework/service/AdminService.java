package in.gagan.base.framework.service;

import java.util.List;

import in.gagan.base.framework.dto.UserDTO;

public interface AdminService {

	public void createAdminUser();
	
	public List<UserDTO> fetchAllUsers();

}
