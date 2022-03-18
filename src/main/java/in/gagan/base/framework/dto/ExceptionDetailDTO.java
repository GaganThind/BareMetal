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

import java.time.LocalDateTime;

/**
 * This DTO captures the exception details and re-throws them in default format. 
 * 
 * @author gaganthind
 *
 */
public final class ExceptionDetailDTO {

	// Create date
	private final LocalDateTime createDt;

	// Exception details
	private final String exceptionDetails;

	// Exception message
	private final String exceptionMessage;
	
	/**
	 * Param constructor
	 * 
	 * @param ex exception object
	 */
	public ExceptionDetailDTO(Exception ex) {
		this.createDt = LocalDateTime.now();
		this.exceptionDetails = ex.toString();
		this.exceptionMessage = null != ex.getMessage() ? ex.getMessage() : "No Message";
	}

	public LocalDateTime getCreateDt() {
		return this.createDt;
	}

	public String getExceptionDetails() {
		return this.exceptionDetails;
	}

	public String getExceptionMessage() {
		return this.exceptionMessage;
	}
	
	@Override
	public String toString() {
		return "ExceptionDetail [createDt=" + createDt + ", exceptionDetails=" + exceptionDetails
				+ ", exceptionMessage=" + exceptionMessage + "]";
	}
	
}
