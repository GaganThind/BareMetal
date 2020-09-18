package in.gagan.base.framework.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.dao.UserDAO;

@Transactional
@Service
public class UserValidationService {
	
	@Autowired
	private UserDAO userDAO;
	
	public boolean validateIfUserExists(String email) {
		return this.userDAO.findUserByEmail(email).isPresent();
	}

}
