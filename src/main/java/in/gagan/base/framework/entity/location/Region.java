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
			if (other.regionName != null)
				return false;
		} else if (!regionName.equals(other.regionName))
			return false;
		return true;
	}

}
