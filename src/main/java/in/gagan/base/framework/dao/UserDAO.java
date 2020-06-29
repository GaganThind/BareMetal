package in.gagan.base.framework.dao;

import org.springframework.stereotype.Repository;

import in.gagan.base.framework.model.User;

/**
 * This class is used to CRUD operations on the User table using DAO pattern
 * 
 * @author gaganthind
 *
 */
@Repository
public class UserDAO extends AbstractBaseDAO<User, Long> {
	
}
