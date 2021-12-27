package in.gagan.base.framework.service;

import java.util.HashSet;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.dao.CountryDAO;
import in.gagan.base.framework.dto.CityDTO;
import in.gagan.base.framework.dto.CountryDTO;
import in.gagan.base.framework.dto.StateDTO;
import in.gagan.base.framework.dto.ZipcodeDTO;
import in.gagan.base.framework.entity.Country;

/**
 * This class provides the implementation of AddressService interface and provides operations for Address functionality.
 * 
 * @author gaganthind
 *
 */
@Transactional
@Service
public class AddressServiceImpl implements AddressService {
	
	private final CountryDAO countryDAO;
	
	private final MessageSource message;
	
	@Autowired
	public AddressServiceImpl(CountryDAO countryDAO, MessageSource message) {
		this.countryDAO = countryDAO;
		this.message = message;
	}
	
	/**
	 * Method used to return all the countries data containing states and cities.
	 * 
	 * @return countries - data consisting of all the countries
	 */
	@Override
	public Optional<Set<CountryDTO>> getCountries() {
		Iterable<Country> countries = 
				this.countryDAO.findAll()
								.orElseThrow(
										noSuchElementExceptionSupplier(message.getMessage("message.address.countries.list.empty", null, Locale.ENGLISH)));
		
		Set<CountryDTO> countryDTOs = convertToDTO(countries);
		return Optional.ofNullable(countryDTOs);
	}

	/**
	 * Method used to return the country data containing states and cities.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @return country - data consisting of all the states and respective cities
	 */
	@Override
	public Optional<CountryDTO> getCountry(String countryId) {
		Iterable<Country> countries = 
				this.countryDAO.findCountryAndStateNameByCountryId(countryId)
								.orElseThrow(noSuchElementExceptionSupplier(message.getMessage("message.address.country.not.found", new Object[] { countryId }, Locale.ENGLISH)));

		Set<CountryDTO> countryDTOs = convertToDTO(countries);
		return countryDTOs.stream().findFirst();
	}
	
	/**
	 * Method used to return all the states data based on passed country.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @return states - data consisting of all the states of the country and respective cities
	 */
	@Override
	public Optional<Set<StateDTO>> getStates(String countryId) {
		Set<StateDTO> states = getCountry(countryId)
								.orElseThrow(noSuchElementExceptionSupplier(message.getMessage("message.address.states.not.found", new Object[] { countryId }, Locale.ENGLISH)))
								.getStates();
		return Optional.ofNullable(states);
	}
	
	/**
	 * Method used to return the state data based on passed state.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param stateId - state id for which data is to be searched
	 * @return state - data consisting of all the cities of the state
	 */
	@Override
	public Optional<StateDTO> getState(String countryId, String stateId) {
		Iterable<Country> countries = this.countryDAO.findbyCountryIdAndStateId(countryId, stateId)
														.orElseThrow(
																noSuchElementExceptionSupplier(message.getMessage("message.address.state.not.found", new Object[] { stateId, countryId }, Locale.ENGLISH)));
		
		Set<CountryDTO> countryDTOs = convertToDTO(countries);
		Optional<CountryDTO> country = countryDTOs.stream().findFirst();
		Set<StateDTO> states = country.get().getStates();
		return states.stream().findFirst();
	}
	
	/**
	 * Method used to return the cities based on country and state.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param stateId - state id for which data is to be searched
	 * @return cities - data consisting of all the cities of the state
	 */
	@Override
	public Optional<Set<CityDTO>> getCities(String countryId, String stateId) {
		Set<CityDTO> cities = getState(countryId, stateId)
								.orElseThrow(noSuchElementExceptionSupplier(message.getMessage("message.address.cities.not.found", new Object[] { stateId, countryId }, Locale.ENGLISH)))
								.getCities();
		return Optional.ofNullable(cities);
	}
	
	/**
	 * Method used to return the city based on country, state and city.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param stateId - state id for which data is to be searched
	 * @return city - data consisting of city details
	 */
	@Override
	public Optional<CityDTO> getCity(String countryId, String stateId, String cityId) {
		Iterable<Country> countries = this.countryDAO.findbyCountryIdStateIdAndCityId(countryId, stateId, cityId)
														.orElseThrow(
																noSuchElementExceptionSupplier(
																		message.getMessage("message.address.city.not.found", new Object[] { cityId, stateId, countryId }, Locale.ENGLISH)));
		
		Set<CountryDTO> countryDTOs = convertToDTO(countries);
		Optional<CountryDTO> country = countryDTOs.stream().findFirst();
		Optional<StateDTO> state = country.get().getStates().stream().findFirst();
		Set<CityDTO> cities = state.get().getCities();
		return cities.stream().findFirst();
	}
	
	/**
	 * Method used to return data based on passed zipcode.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param zipcode - state id for which data is to be searched
	 * @return zipcodeDTO - data containing details based on zipcode
	 */
	@Override
	public Optional<ZipcodeDTO> getDataBasedOnZipcode(String countryId, long zipcode) {
		Country country = this.countryDAO.findbyCountryIdAndZipcode(countryId, zipcode)
														.orElseThrow(
																noSuchElementExceptionSupplier(
																		message.getMessage("message.address.zipcode.not.found", new Object[] { zipcode, countryId }, Locale.ENGLISH)));
		return Optional.ofNullable(new ZipcodeDTO(country.getZipcode(), country.getCity(), country.getState(), country.getCountry()));
	}
	
	private Supplier<? extends NoSuchElementException> noSuchElementExceptionSupplier(String message) {
		return () -> new NoSuchElementException(message);
	}
	
	private Set<CountryDTO> convertToDTO(Iterable<Country> countries) {
		Set<CountryDTO> countryDTOs = new HashSet<>();
		Set<StateDTO> stateDTOs = null;
		Set<CityDTO> cityDTOs = null;
		
		CountryDTO countryDTO = null;
		StateDTO stateDTO = null;
		CityDTO cityDTO = null;
		
		for (Country country: countries) {
			String countryName = country.getCountry();
			String stateName = country.getState();
			String cityName = country.getCity();
			long zipcode = country.getZipcode();
			
			Optional<CountryDTO> countryOpt = countryDTOs.stream()
															.filter(c -> c.getName().equals(countryName))
															.findFirst();
			countryDTO = countryOpt.orElse(new CountryDTO(countryName));
			
			stateDTOs = countryDTO.getStates();
			Optional<StateDTO> stateOpt = stateDTOs.stream()
													.filter(s -> s.getName().equals(stateName))
													.findFirst();
			stateDTO = stateOpt.orElse(new StateDTO(stateName));
			
			cityDTOs = stateDTO.getCities();
			Optional<CityDTO> cityOpt = cityDTOs.stream()
													.filter(c -> c.getName().equals(cityName))
													.findFirst();
			
			cityDTO = cityOpt.orElse(new CityDTO(cityName));
			
			cityDTO.addZipcode(zipcode);
			stateDTO.addCity(cityDTO);
			countryDTO.addState(stateDTO);
			countryDTOs.add(countryDTO);
		}
		return countryDTOs;
	}

}
