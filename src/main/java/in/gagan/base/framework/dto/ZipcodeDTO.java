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
	
	private long zipcode;
	
	private String cityId;
	
	private String stateId;
	
	private String countryId;

	public ZipcodeDTO(long zipcode, String cityId, String stateId, String countryId) {
		this.zipcode = zipcode;
		this.cityId = cityId;
		this.stateId = stateId;
		this.countryId = countryId;
	}

	public long getZipcode() {
		return zipcode;
	}

	public String getCity() {
		return cityId;
	}

	public String getState() {
		return stateId;
	}

	public String getCountry() {
		return countryId;
	}
	
}
