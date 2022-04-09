/*
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

package in.gagan.base.framework.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.entity.base.AuditableEntity;
import in.gagan.base.framework.entity.base.BaseEntity;

/**
 * Entity representing the EXCEPTION_MONITORING table 
 * 
 * @author gaganthind
 *
 */
@Entity
@Table(name = "EXCEPTION_MONITOR")
public class ExceptionMonitor extends AuditableEntity implements BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="EXCEPTION_ID", nullable = false, unique = true, length = 10)
	private long exceptionId;
	
	@Column(name = "EXCEPTION_DETAILS", length = 500)
	private String exceptionDetails;
	
	@Column(name = "EXCEPTION_MESSAGE", length = 500)
	private String exceptionMessage;
	
	@Column(name = "STACK_TRACE", length = 500)
	private String stackTrace;
	
	public ExceptionMonitor() {
		super(ApplicationConstants.CHAR_Y);
	}

	public void setExceptionDetails(String exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (exceptionId ^ (exceptionId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExceptionMonitor other = (ExceptionMonitor) obj;
		return exceptionId == other.exceptionId;
	}
	
}
