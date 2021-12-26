package in.gagan.base.framework.dao;

import java.util.Optional;

import in.gagan.base.framework.entity.Country;

/**
 * This DAO interface provides the CRUD operations for COUNTRIES table.
 * 
 * @author gaganthind
 *
 */
public interface CountryDAO extends BaseDAO<Country, Long> {
	
	/**
	 * Method used to find country data based on countryId
	 * 
	 * @param countryId: country to search
	 * @return countries: countries data consisting of state, cities and zipcodes
	 */
	public Optional<Iterable<Country>> findbyCountryId(String countryId);
	
	/**
	 * Method used to find country data based on countryId and stateId
	 * 
	 * @param countryId: country to search
	 * @param stateId: state to search
	 * @return countries: countries data consisting of state, cities and zipcodes
	 */
	public Optional<Iterable<Country>> findbyCountryIdAndStateId(String countryId, String stateId);
	
	/**
	 * Method used to find country data based on countryId, stateId and cityId
	 * 
	 * @param countryId: country to search
	 * @param stateId: state to search
	 * @param cityId: city to search
	 * @return countries: countries data consisting of state, cities and zipcodes
	 */
	public Optional<Iterable<Country>> findbyCountryIdStateIdAndCityId(String countryId, String stateId, String cityId);
	
	/**
	 * Method used to find country data based on countryId and zipcode
	 * 
	 * @param countryId: country to search
	 * @param zipcode: zipcode to search
	 * @return country: country data consisting of state, citie and zipcode
	 */
	public Optional<Country> findbyCountryIdAndZipcode(String countryId, long zipcode);

}
