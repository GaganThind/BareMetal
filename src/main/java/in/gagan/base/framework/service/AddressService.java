package in.gagan.base.framework.service;

import java.util.Optional;
import java.util.Set;

import in.gagan.base.framework.dto.CityDTO;
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
	 * Method used to return all the countries data containing states and cities.
	 * 
	 * @return countries - data consisting of all the countries
	 */
	public Optional<Set<CountryDTO>> getCountries();
	
	/**
	 * Method used to return the country data containing states and cities.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @return country - data consisting of all the states and respective cities
	 */
	public Optional<CountryDTO> getCountry(String countryId);
	
	/**
	 * Method used to return all the states data based on passed country.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @return states - data consisting of all the states of the country and respective cities
	 */
	public Optional<Set<StateDTO>> getStates(String countryId);
	
	/**
	 * Method used to return the state data based on passed state.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param stateId - state id for which data is to be searched
	 * @return state - data consisting of all the cities of the state and other details
	 */
	public Optional<StateDTO> getState(String countryId, String stateId);
	
	/**
	 * Method used to return the cities based on country and state.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param stateId - state id for which data is to be searched
	 * @return cities - data consisting of all the cities of the state
	 */
	public Optional<Set<CityDTO>> getCities(String countryId, String stateId);
	
	/**
	 * Method used to return the city based on country, state and city.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param stateId - state id for which data is to be searched
	 * @return cities - data consisting of all the cities of the state
	 */
	public Optional<CityDTO> getCity(String countryId, String stateId, String cityId);
	
	/**
	 * Method used to return data based on passed zipcode.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param zipcode - state id for which data is to be searched
	 * @return zipcodeDTO - data containing details based on zipcode
	 */
	public Optional<ZipcodeDTO> getDataBasedOnZipcode(String countryId, long zipcode);

}