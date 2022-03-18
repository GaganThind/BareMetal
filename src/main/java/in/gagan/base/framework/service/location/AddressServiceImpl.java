/*
 * SpringBoot_Hibernate_Framework
 * 
 * Copyright (C) 2020-2022  Gagan Thind

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package in.gagan.base.framework.service.location;

import in.gagan.base.framework.dao.CountryDAO;
import in.gagan.base.framework.dto.location.ZipcodeDTO;
import in.gagan.base.framework.entity.location.City;
import in.gagan.base.framework.entity.location.Country;
import in.gagan.base.framework.entity.location.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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
	
	/**
	 * Method used to return all the countries' data containing states and cities for admin purpose.
	 * 
	 * @return countries - data consisting of all the countries
	 */
	@Override
	public Optional<Iterable<Country>> getCountriesForAdmin() {
		return this.countryDAO.findAll();
	}

	/**
	 * Method used to return all the countries names.
	 * 
	 * @return countries - data consisting of all the countries
	 */
	@Override
	public Optional<Iterable<Country>> getCountries() {
		return this.countryDAO.findAll();
	}

	/**
	 * Method used to return the country data containing states and cities.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @return country - data consisting of all the states and respective cities
	 */
	@Override
	public Optional<Country> getCountry(String countryId) {
		return this.countryDAO.findByCountryId(countryId);
	}

	/**
	 * Method used to return all the region data based on passed country.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @return regions - data consisting of all the region/states of the country
	 */
	@Override
	public Optional<Iterable<Region>> getRegions(String countryId) {
		return this.countryDAO.findRegionsByCountryId(countryId);
	}

	/**
	 * Method used to return the region data along with city details based on passed
	 * countryId and regionId.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param regionId  - region/state id for which data is to be searched
	 * @return region - data consisting of the region/state
	 */
	@Override
	public Optional<Region> getRegion(String countryId, String regionId) {
		return this.countryDAO.findRegionByCountryIdAndRegionId(countryId, regionId);
	}

	/**
	 * Method used to return the cities based on country and state.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param regionId  - region/state id for which data is to be searched
	 * @return cities - data consisting of all the cities of the state
	 */
	@Override
	public Optional<Iterable<City>> getCities(String countryId, String regionId) {
		return this.countryDAO.findCitiesByCountryIdAndRegionId(countryId, regionId);
	}

	/**
	 * Method used to return the city based on country, state and city.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param regionId   - region id for which data is to be searched
	 * @return city - data consisting of city details
	 */
	@Override
	public Optional<Iterable<City>> getCity(String countryId, String regionId, String cityId) {
		return this.countryDAO.findCityByCountryIdStateIdAndCityId(countryId, regionId, cityId);
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
		return this.countryDAO.findZipcodeDataByCountryIdAndZipcode(countryId, zipcode);
	}

}
