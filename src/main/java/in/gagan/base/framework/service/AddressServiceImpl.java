package in.gagan.base.framework.service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.dao.CountryDAO;
import in.gagan.base.framework.dto.CityDTO;
import in.gagan.base.framework.dto.CountryDTO;
import in.gagan.base.framework.dto.RegionDTO;
import in.gagan.base.framework.dto.ZipcodeDTO;
import in.gagan.base.framework.entity.location.City;
import in.gagan.base.framework.entity.location.Country;
import in.gagan.base.framework.entity.location.Region;

/**
 * This class provides the implementation of AddressService interface and
 * provides operations for Address functionality.
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

	// TODO Refactor
	/**
	 * Method used to return all the countries data containing states and cities.
	 * 
	 * @return countries - data consisting of all the countries
	 */
	@Override
	public Optional<Set<CountryDTO>> getCountries() {
		Iterable<Country> countries = this.countryDAO.findAll().orElseThrow(noSuchElementExceptionSupplier(
				message.getMessage("message.address.countries.list.empty", null, Locale.ENGLISH)));

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
		Country country = this.countryDAO.findbyCountryId(countryId).orElseThrow(noSuchElementExceptionSupplier(
				message.getMessage("message.address.country.not.found", new Object[] { countryId }, Locale.ENGLISH)));

		CountryDTO countryDTO = convertToDTO(country);
		return Optional.ofNullable(countryDTO);
	}

	/**
	 * Method used to return all the region data based on passed country.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @return regions - data consisting of all the region/states of the country
	 */
	@Override
	public Optional<Set<RegionDTO>> getRegions(String countryId) {
		Iterable<Region> regions = this.countryDAO.findRegionsByCountryId(countryId)
				.orElseThrow(noSuchElementExceptionSupplier(message.getMessage("message.address.states.not.found",
						new Object[] { countryId }, Locale.ENGLISH)));
		Set<RegionDTO> regionDTOs = new HashSet<>();
		for (Region region : regions) {
			regionDTOs.add(new RegionDTO(region.getRegionName()));
		}
		
		// Sort region
		Set<RegionDTO> sortedSet = new TreeSet<>(Comparator.comparing(RegionDTO::getName));
		sortedSet.addAll(regionDTOs);

		return Optional.of(sortedSet);
	}

	/**
	 * Method used to return the region data along with city details based on passed
	 * countryid and regionId.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param regionId  - region/state id for which data is to be searched
	 * @return region - data consisting of the region/state
	 */
	@Override
	public Optional<RegionDTO> getRegion(String countryId, String regionId) {
		Region region = this.countryDAO.findRegionbyCountryIdAndRegionId(countryId, regionId)
				.orElseThrow(noSuchElementExceptionSupplier(message.getMessage("message.address.state.not.found",
						new Object[] { regionId, countryId }, Locale.ENGLISH)));
		RegionDTO regionDTO = new RegionDTO(region.getRegionName());

		Set<CityDTO> cityDTOs = new HashSet<>();
		for (City city : region.getCities()) {
			cityDTOs.add(new CityDTO(city.getCityName()));
		}
		
		// Sort cities
		Set<CityDTO> sortedSet = new TreeSet<>(Comparator.comparing(CityDTO::getName));
		sortedSet.addAll(cityDTOs);
		
		regionDTO.setCities(sortedSet);

		return Optional.of(regionDTO);
	}

	/**
	 * Method used to return the cities based on country and state.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param regionId  - region/state id for which data is to be searched
	 * @return cities - data consisting of all the cities of the state
	 */
	@Override
	public Optional<Set<CityDTO>> getCities(String countryId, String regionId) {
		Set<CityDTO> cities = 
				getRegion(countryId, regionId)
					.orElseThrow(noSuchElementExceptionSupplier(message
						.getMessage("message.address.cities.not.found", new Object[] { regionId, countryId }, Locale.ENGLISH)))
					.getCities();
		
		// Sort cities
		Set<CityDTO> sortedSet = new TreeSet<>(Comparator.comparing(CityDTO::getName));
		sortedSet.addAll(cities);
		
		return Optional.of(sortedSet);
	}

	/**
	 * Method used to return the city based on country, state and city.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param stateId   - state id for which data is to be searched
	 * @return city - data consisting of city details
	 */
	@Override
	public Optional<CityDTO> getCity(String countryId, String regionId, String cityId) {
		Iterable<City> city = 
				this.countryDAO.findCitybyCountryIdStateIdAndCityId(countryId, regionId, cityId)
								.orElseThrow(noSuchElementExceptionSupplier(message.getMessage("message.address.city.not.found",
											new Object[] { cityId, regionId, countryId }, Locale.ENGLISH)));

		CityDTO cityDTO = null;

		for (City ct : city) {
			if (null == cityDTO) {
				cityDTO = new CityDTO(ct.getCityName());
			}
			cityDTO.addZipcode(ct.getZipcode());
		}
		
		// Sort zipcodes
		Set<Long> sortedSet = new TreeSet<>(cityDTO.getZipcodes());
		cityDTO.setZipcodes(sortedSet);

		return Optional.of(cityDTO);
	}

	/**
	 * Method used to return data based on passed zipcode.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param zipcode   - zipcode for which data is to be searched
	 * @return zipcodeDTO - data containing details based on zipcode
	 */
	@Override
	public Optional<ZipcodeDTO> getDataBasedOnZipcode(String countryId, long zipcode) {
		return this.countryDAO.findZipcodeDatabyCountryIdAndZipcode(countryId, zipcode);
	}

	private Supplier<? extends NoSuchElementException> noSuchElementExceptionSupplier(String message) {
		return () -> new NoSuchElementException(message);
	}

	private Set<CountryDTO> convertToDTO(Iterable<Country> countries) {
		Set<CountryDTO> countryDTOs = new HashSet<>();

		for (Country country : countries) {
			countryDTOs.add(convertToDTO(country));
		}

		return countryDTOs;
	}

	private CountryDTO convertToDTO(Country country) {
		CountryDTO countryDTO = new CountryDTO(country.getCountryName());

		for (Region region : country.getRegions()) {
			RegionDTO regionDTO = new RegionDTO(region.getRegionName());

			for (City city : region.getCities()) {
				Set<CityDTO> cityDTOs = regionDTO.getCities();
				String cityName = city.getCityName();

				Optional<CityDTO> cityOpt = cityDTOs.stream().filter(c -> c.getName().equals(cityName)).findFirst();

				CityDTO cityDTO = cityOpt.orElse(new CityDTO(cityName));
				cityDTO.addZipcode(city.getZipcode());

				regionDTO.addCity(cityDTO);
			}

			countryDTO.addRegion(regionDTO);
		}

		return countryDTO;
	}

}
