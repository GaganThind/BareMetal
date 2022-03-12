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

package in.gagan.base.framework.dto;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * This DTO captures the exception details and log them in database. 
 * 
 * @author gaganthind
 *
 */
public final class ExceptionMonitorDTO {
	
	// Create date
	private final LocalDateTime createDt;

	// Exception details
	private final String exceptionDetails;

	// Exception message
	private final String exceptionMessage;
	
	// StackTrace
	private final String stackTrace;

	public ExceptionMonitorDTO(Exception ex) {
		this.createDt = LocalDateTime.now();
		
		String exDetails = StringUtils.defaultIfEmpty(ex.toString(), "No Message");
		this.exceptionDetails = StringUtils.left(exDetails, 500);
		
		String exMessage = StringUtils.defaultIfEmpty(ex.getMessage(), "No Message");
		this.exceptionMessage = StringUtils.left(exMessage, 500);
		
		String stacktrace = Arrays.toString(ex.getStackTrace());
		this.stackTrace = StringUtils.left(stacktrace, 500);
	}

	public LocalDateTime getCreateDt() {
		return createDt;
	}

	public String getExceptionDetails() {
		return exceptionDetails;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	@Override
	public String toString() {
		return "ExceptionMonitorDTO [createDt=" + createDt + ", exceptionDetails=" + exceptionDetails
				+ ", exceptionMessage=" + exceptionMessage + ", stackTrace=" + stackTrace + "]";
	}
	
}
