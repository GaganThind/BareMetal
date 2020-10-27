package in.gagan.base.framework.service;

import in.gagan.base.framework.dto.ExceptionMonitorDTO;

/**
 * This Service interface provides the operations for Exception Monitoring functionality.
 * 
 * @author gaganthind
 *
 */
public interface ExceptionMonitoringService {
	
	/**
	 * This method creates a new record in EXCEPTION_MONITORING table and captures exception details.
	 * 
	 * @param exceptionMonitorDTO - Exception DTO
	 */
	public void insertException(ExceptionMonitorDTO exceptionMonitorDTO);

}
