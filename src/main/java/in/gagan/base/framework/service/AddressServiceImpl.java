package in.gagan.base.framework.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import in.gagan.base.framework.dto.CityDTO;
import in.gagan.base.framework.dto.CountryDTO;
import in.gagan.base.framework.dto.StateDTO;
import in.gagan.base.framework.dto.ZipcodeDTO;

/**
 * This class provides the implementation of AddressService interface and provides operations for Address functionality.
 * 
 * @author gaganthind
 *
 */
@Transactional
@Service
public class AddressServiceImpl implements AddressService {
	
	/**
	 * Method used to return the country data containing states and cities.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @return country - data consisting of all the states and respective cities.
	 */
	public CountryDTO getCountryData(String countryId) {
		StateDTO karnataka = new StateDTO("KA", "Karnataka");
		karnataka.addCity(new CityDTO("BGLR", "Bengaluru", 560098L));
		karnataka.addCity(new CityDTO("MYS", "Mysore", 560099L));
		
		StateDTO maharashtra = new StateDTO("MH", "Maharashtra");
		maharashtra.addCity(new CityDTO("MU", "Mumbai", 400711L));
		maharashtra.addCity(new CityDTO("NVMU", "Navi Mumbai", 400700L));
		
		CountryDTO india = new CountryDTO("IN", "India");
		india.addState(karnataka);
		india.addState(maharashtra);
		return india;
	}
	
	/**
	 * Method used to return the state data based on passed state.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param stateId - state id for which data is to be searched
	 * @return state - data consisting of all the cities of the state.
	 */
	public StateDTO getStateData(String countryId, String stateId) {
		StateDTO karnataka = new StateDTO("KA", "Karnataka");
		karnataka.addCity(new CityDTO("BGLR", "Bengaluru", 560098L));
		karnataka.addCity(new CityDTO("MYS", "Mysore", 560099L));
		
		StateDTO maharashtra = new StateDTO("MH", "Maharashtra");
		maharashtra.addCity(new CityDTO("MU", "Mumbai", 400711L));
		maharashtra.addCity(new CityDTO("NVMU", "Navi Mumbai", 400700L));
		
		return "KA".equals(stateId) ? karnataka : maharashtra;
	}
	
	/**
	 * Method used to return data based on passed zipcode.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param zipcode - state id for which data is to be searched
	 * @return zipcode - data containing details based on zipcode
	 */
	public ZipcodeDTO getDataBasedOnZipcode(String countryId, Long zipcode) {
		return new ZipcodeDTO(400700L, "Mumbai", "Maharashtra", "India");
	}

}
