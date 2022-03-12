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

import java.io.Serializable;

/**
 * This DTO captures the zipcode specific data
 * 
 * @author gaganthind
 *
 */
public class ZipcodeDTO implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -1354685243128420186L;
	
	private final long zipcode;
	
	private final String cityId;
	
	private final String stateId;
	
	private final String countryId;

	public ZipcodeDTO(long zipcode, String cityId, String stateId, String countryId) {
		this.zipcode = zipcode;
		this.cityId = cityId;
		this.stateId = stateId;
		this.countryId = countryId;
	}

	public long getZipcode() {
		return zipcode;
	}

	public String getCity() {
		return cityId;
	}

	public String getState() {
		return stateId;
	}

	public String getCountry() {
		return countryId;
	}
	
}
