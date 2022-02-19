package in.gagan.base.framework.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * This DTO captures the region specific data
 * 
 * @author gaganthind
 *
 */
public class RegionDTO implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -851783917214658684L;
	
	private final String regionId;
	
	private final String regionName;
	
	private Set<CityDTO> cities;
	
	public RegionDTO(String regionName) {
		this.regionId = this.regionName = regionName;
		this.cities = new HashSet<>();
	}
	
	public RegionDTO(String regionName, Set<CityDTO> cities) {
		this.regionId = this.regionName = regionName;
		this.cities = cities;
	}
	
	public void addCity(CityDTO city) {
		if (null == this.cities) {
			this.cities = new HashSet<>();
		}
		
		this.cities.add(city);
	}

	public String getRegionId() {
		return regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public Set<CityDTO> getCities() {
		return cities;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((regionId == null) ? 0 : regionId.hashCode());
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
		RegionDTO other = (RegionDTO) obj;
		if (regionId == null) {
			if (other.regionId != null)
				return false;
		} else if (!regionId.equals(other.regionId))
			return false;
		return true;
	}

}
