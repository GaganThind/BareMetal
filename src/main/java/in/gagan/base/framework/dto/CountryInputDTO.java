package in.gagan.base.framework.dto;

import java.io.Serializable;

/**
 * This DTO captures the country specific data from csv file.
 * 
 * @author gaganthind
 *
 */
public class CountryInputDTO implements Serializable {
	
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -2260269970570563126L;

	private String city;
	
	private String state;
	
	private String country;
	
	private long zipcode;
	
	public CountryInputDTO() { }

	public CountryInputDTO(String city, String state, String country, long zipcode) {
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode = zipcode;
	}

	public long getZipcode() {
		return zipcode;
	}

	public void setZipcode(long zipcode) {
		this.zipcode = zipcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		CountryInputDTO other = (CountryInputDTO) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (zipcode != other.zipcode)
			return false;
		return true;
	}
	
}
