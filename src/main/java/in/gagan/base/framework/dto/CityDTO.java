package in.gagan.base.framework.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * This DTO captures the city specific data
 * 
 * @author gaganthind
 *
 */
public final class CityDTO implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -6915127657521583329L;
	
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
	
	public void addZipcode(long zipcode) {
		if (null == this.zipcodes) {
			this.zipcodes = new HashSet<>();
		}
		
		this.zipcodes.add(zipcode);
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
		CityDTO other = (CityDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}