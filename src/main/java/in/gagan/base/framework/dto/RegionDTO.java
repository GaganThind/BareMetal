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
		RegionDTO other = (RegionDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
