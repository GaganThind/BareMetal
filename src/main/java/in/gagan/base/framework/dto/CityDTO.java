package in.gagan.base.framework.dto;

import java.io.Serializable;

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
	
	private final Long zipcode;
	
	public CityDTO(String id, String name, Long zipcode) {
		this.id = id;
		this.name = name;
		this.zipcode = zipcode;
	}
	
	public Long getZipcode() {
		return zipcode;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
