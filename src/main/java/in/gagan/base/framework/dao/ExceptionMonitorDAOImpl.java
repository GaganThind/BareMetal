package in.gagan.base.framework.dao;

import org.springframework.stereotype.Repository;

import in.gagan.base.framework.entity.ExceptionMonitor;

/**
 * This class is used to CRUD operations on the EXCEPTION_MONITOR table using DAO pattern.
 * 
 * @author gaganthind
 *
 */
@Repository
public class ExceptionMonitorDAOImpl extends AbstractBaseDAO<ExceptionMonitor, Long> implements ExceptionMonitorDAO {
	
}
