package in.gagan.base.framework.service;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.dao.ExceptionMonitorDAO;
import in.gagan.base.framework.dto.ExceptionMonitorDTO;
import in.gagan.base.framework.entity.ExceptionMonitor;

@Transactional
@Service
public class ExceptionMonitoringService {
	
	private final ExceptionMonitorDAO exceptionMonitorDAO;

	@Autowired
	public ExceptionMonitoringService(ExceptionMonitorDAO exceptionMonitorDAO) {
		this.exceptionMonitorDAO = exceptionMonitorDAO;
	}
	
	public void insertException(ExceptionMonitorDTO exceptionMonitorDTO) {
		ExceptionMonitor exceptionMonitor = new ExceptionMonitor();
		BeanUtils.copyProperties(exceptionMonitorDTO, exceptionMonitor, "createDt");
		this.exceptionMonitorDAO.save(exceptionMonitor);
	}
	
}
