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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This DTO captures the city specific data
 * 
 * @author gaganthind
 *
 */
public final class CityDTO {
	
	private final String id;
	
	private final String name;
	
	private Set<Long> zipcodes;
	
	public CityDTO(String name) {
		this.id = name;
		this.name = name;
		this.zipcodes = new HashSet<>();
	}
	
	public CityDTO(String name, Set<Long> zipcodes) {
		this.id = name;
		this.name = name;
		this.zipcodes = zipcodes;
	}
	
	public Set<Long> getZipcodes() {
		return zipcodes;
	}
	
	public void setZipcodes(Set<Long> zipcodes) {
		this.zipcodes = zipcodes;
	}

	public void addZipcode(long zipcode) {
		if (null == this.zipcodes) {
			this.zipcodes = new HashSet<>();
		}
		
		this.zipcodes.add(zipcode);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CityDTO cityDTO = (CityDTO) o;
		return id.equals(cityDTO.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
