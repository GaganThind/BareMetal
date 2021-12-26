package in.gagan.base.framework.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * This DTO captures the state specific data
 * 
 * @author gaganthind
 *
 */
public final class StateDTO implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -8413161241508101087L;
	
	private final String id;
	
	private final String name;
	
	private Set<CityDTO> cities;
	
	public StateDTO(String name) {
		this.id = name;
		this.name = name;
		this.cities = new HashSet<>();
	}
	
	public StateDTO(String name, Set<CityDTO> cities) {
		this.id = name;
		this.name = name;
		this.cities = cities;
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

	public void addCity(CityDTO city) {
		if (null == this.cities) {
			this.cities = new HashSet<>();
		}
		
		this.cities.add(city);
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
		StateDTO other = (StateDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
