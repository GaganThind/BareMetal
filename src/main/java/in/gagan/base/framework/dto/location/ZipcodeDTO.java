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

package in.gagan.base.framework.dto.location;

import java.util.Objects;

/**
 * This DTO captures the zipcode specific data
 * 
 * @author gaganthind
 *
 */
public class ZipcodeDTO {
	
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

	public String getCityId() {
		return cityId;
	}

	public String getStateId() {
		return stateId;
	}

	public String getCountryId() {
		return countryId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ZipcodeDTO that = (ZipcodeDTO) o;
		return zipcode == that.zipcode && Objects.equals(cityId, that.cityId) && Objects.equals(stateId, that.stateId)
				&& Objects.equals(countryId, that.countryId);
	}
	@Override
	public int hashCode() {
		return Objects.hash(zipcode, cityId, stateId, countryId);
	}
	
}
