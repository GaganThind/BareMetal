package in.gagan.base.framework.service;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.dao.ExceptionMonitorDAO;
import in.gagan.base.framework.dto.ExceptionMonitorDTO;
import in.gagan.base.framework.entity.ExceptionMonitor;

/**
 * This class provides the implementation of ExceptionMonitoringService interface and provides operations for Exception Monitoring functionality.
 * 
 * @author gaganthind
 *
 */
@Transactional
@Service
public class ExceptionMonitoringServiceImpl implements ExceptionMonitoringService {
	
	private final ExceptionMonitorDAO exceptionMonitorDAO;

	@Autowired
	public ExceptionMonitoringServiceImpl(ExceptionMonitorDAO exceptionMonitorDAO) {
		this.exceptionMonitorDAO = exceptionMonitorDAO;
	}
	
	/**
	 * This method creates a new record in EXCEPTION_MONITORING table and captures exception details.
	 * 
	 * @param exceptionMonitorDTO - Exception DTO
	 */
	@Override
	public void insertException(ExceptionMonitorDTO exceptionMonitorDTO) {
		ExceptionMonitor exceptionMonitor = new ExceptionMonitor();
		BeanUtils.copyProperties(exceptionMonitorDTO, exceptionMonitor, "createDt");
		this.exceptionMonitorDAO.save(exceptionMonitor);
	}
	
}
