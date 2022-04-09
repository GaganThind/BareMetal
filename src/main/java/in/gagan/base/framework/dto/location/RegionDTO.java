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
 * This DTO captures the region specific data
 * 
 * @author gaganthind
 *
 */
public class RegionDTO {
	
	private final String id;
	
	private final String name;
	
	private Set<CityDTO> cities;
	
	public RegionDTO(String name) {
		this.id = this.name = name;
		this.cities = new HashSet<>();
	}
	
	public RegionDTO(String name, Set<CityDTO> cities) {
		this.id = this.name = name;
		this.cities = cities;
	}

	public void addCity(CityDTO city) {
		if (null == this.cities) {
			this.cities = new HashSet<>();
		}
		
		this.cities.add(city);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set<CityDTO> getCities() {
		return cities;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RegionDTO regionDTO = (RegionDTO) o;
		return id.equals(regionDTO.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
