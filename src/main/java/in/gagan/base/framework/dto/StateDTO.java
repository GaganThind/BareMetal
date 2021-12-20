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
	
	public StateDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public StateDTO(String id, String name, Set<CityDTO> cities) {
		this.id = id;
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

}
