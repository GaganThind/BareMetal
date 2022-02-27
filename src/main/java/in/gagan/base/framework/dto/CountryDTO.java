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
import java.util.HashSet;
import java.util.Set;

/**
 * This DTO captures the country specific data.
 * 
 * @author gaganthind
 *
 */
public final class CountryDTO implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -6852622873535636645L;
	
	private final String id;
	
	private final String name;
	
	private Set<RegionDTO> regions;
	
	public CountryDTO(String name) {
		this.id = name;
		this.name = name;
		this.regions = new HashSet<>();
	}
	
	public CountryDTO(String name, Set<RegionDTO> regions) {
		this.id = name;
		this.name = name;
		this.regions = regions;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public Set<RegionDTO> getRegions() {
		return regions;
	}

	public void setRegions(Set<RegionDTO> regions) {
		this.regions = regions;
	}

	public void addRegion(RegionDTO region) {
		if (null == this.regions) {
			this.regions = new HashSet<>();
		}
		
		this.regions.add(region);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		CountryDTO other = (CountryDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
