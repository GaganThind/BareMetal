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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.entity.base.BaseEntity;
import in.gagan.base.framework.entity.base.NonAuditableEntity;

/**
 * Entity representing the CITIES table 
 * 
 * @author gaganthind
 *
 */
@Entity
@Table(name="CITIES")
public class City extends NonAuditableEntity implements BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CITY_ID", nullable = false, unique = true, length = 10)
	private long cityId;

	@Column(name = "CITY_NAME", nullable = false)
	private String cityName;
	
	@Column(name = "ZIPCODE", nullable = false)
	private long zipcode;
	
	public City() {
		super(ApplicationConstants.CHAR_Y);
	}
	
	public City(String cityName, long zipcode) {
		super(ApplicationConstants.CHAR_Y);
		this.cityName = cityName;
		this.zipcode = zipcode;
	}

	public long getCityId() {
		return cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public long getZipcode() {
		return zipcode;
	}

	public void setZipcode(long zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
		result = prime * result + (int) (zipcode ^ (zipcode >>> 32));
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
		City other = (City) obj;
		if (cityName == null) {
			if (other.cityName != null)
				return false;
		} else if (!cityName.equals(other.cityName))
			return false;
		return zipcode == other.zipcode;
	}
	
}
