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

package in.gagan.base.framework.entity.location;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.entity.base.BaseEntity;
import in.gagan.base.framework.entity.base.NonAuditableEntity;

/**
 * Entity representing the REGIONS table 
 * 
 * @author gaganthind
 *
 */
@Entity
@Table(name="REGIONS")
public class Region extends NonAuditableEntity implements BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REGION_ID", nullable = false, unique = true, length = 10)
	private long regionId;
	
	@Column(name = "REGION_NAME", nullable = false)
	private String regionName;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name="REGION_ID", nullable = false)
	@Fetch(FetchMode.SELECT)
	private Set<City> cities = new HashSet<>();
	
	public Region() {
		super(ApplicationConstants.CHAR_Y);
	}

	public Region(String regionName) {
		super(ApplicationConstants.CHAR_Y);
		this.regionName = regionName;
	}

	public long getRegionId() {
		return regionId;
	}

	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Set<City> getCities() {
		return cities;
	}

	public void setCities(Set<City> cities) {
		this.cities = cities;
	}
	
	public void addCity(City city) {
		if (null == this.cities) {
			this.cities = new HashSet<>();
		}
		
		this.cities.add(city);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((regionName == null) ? 0 : regionName.hashCode());
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
		Region other = (Region) obj;
		if (regionName == null) {
			return other.regionName == null;
		} else return regionName.equals(other.regionName);
	}

}
