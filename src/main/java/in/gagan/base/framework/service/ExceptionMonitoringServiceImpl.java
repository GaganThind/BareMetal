/*
 * SpringBoot_Hibernate_Framework
 * 
 * Copyright (C) 2020-2022  Gagan Thind

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
