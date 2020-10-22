package in.gagan.base.framework.dao;

import org.springframework.stereotype.Repository;

import in.gagan.base.framework.entity.Role;

/**
 * This class provides CRUD operations on the Role table using DAO pattern
 * 
 * @author gaganthind
 *
 */
@Repository
public class RoleDAOImpl extends AbstractBaseDAO<Role, Long> implements RoleDAO {

}
