package in.gagan.base.framework.service;

import in.gagan.base.framework.dto.CountryDTO;
import in.gagan.base.framework.dto.StateDTO;
import in.gagan.base.framework.dto.ZipcodeDTO;

/**
 * This Service interface provides the operations for address functionality.
 * 
 * @author gaganthind
 *
 */
public interface AddressService {
	
	/**
	 * Method used to return the country data containing states and cities.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @return country - data consisting of all the states and respective cities
	 */
	public CountryDTO getCountryData(String countryId);
	
	
	/**
	 * Method used to return the state data based on passed state.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param stateId - state id for which data is to be searched
	 * @return state - data consisting of all the cities of the state
	 */
	public StateDTO getStateData(String countryId, String stateId);
	
	/**
	 * Method used to return data based on passed zipcode.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param zipcode - state id for which data is to be searched
	 * @return zipcode - data containing details based on zipcode
	 */
	public ZipcodeDTO getDataBasedOnZipcode(String countryId, Long zipcode);

}
