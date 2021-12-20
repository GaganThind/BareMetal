package in.gagan.base.framework.dto;

import java.io.Serializable;

/**
 * This DTO captures the zipcode specific data
 * 
 * @author gaganthind
 *
 */
public class ZipcodeDTO implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -1354685243128420186L;
	
	private Long zipcode;
	
	private String city;
	
	private String state;
	
	private String country;

	public ZipcodeDTO(Long zipcode, String city, String state, String country) {
		this.zipcode = zipcode;
		this.city = city;
		this.state = state;
		this.country = country;
	}

	public Long getZipcode() {
		return zipcode;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}
	
}
