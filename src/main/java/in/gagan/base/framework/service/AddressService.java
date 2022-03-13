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

package in.gagan.base.framework.service;

import in.gagan.base.framework.dto.location.ZipcodeDTO;
import in.gagan.base.framework.entity.location.City;
import in.gagan.base.framework.entity.location.Country;
import in.gagan.base.framework.entity.location.Region;

import java.util.Optional;

/**
 * This Service interface provides the operations for address functionality.
 *
 * @author gaganthind
 *
 */
public interface AddressService {

	/**
	 * Method used to return all the countries' data containing states and cities for admin purpose.
	 *
	 * @return countries - data consisting of all the countries
	 */
	Optional<Iterable<Country>> getCountriesForAdmin();

	/**
	 * Method used to return all the countries names.
	 *
	 * @return countries - data consisting of all the countries
	 */
	Optional<Iterable<Country>> getCountries();

	/**
	 * Method used to return the country data containing states and cities.
	 *
	 * @param countryId - country id for which data is to be searched
	 * @return country - data consisting of all the states and respective cities
	 */
	Optional<Country> getCountry(String countryId);

	/**
	 * Method used to return all the region data based on passed country.
	 *
	 * @param countryId - country id for which data is to be searched
	 * @return regions - data consisting of all the region/states of the country
	 */
	Optional<Iterable<Region>> getRegions(String countryId);

	/**
	 * Method used to return the region data along with city details based on passed countryId and regionId.
	 *
	 * @param countryId - country id for which data is to be searched
	 * @param regionId - region/state id for which data is to be searched
	 * @return region - data consisting of the region/state
	 */
	Optional<Region> getRegion(String countryId, String regionId);

	/**
	 * Method used to return the cities based on country and state.
	 *
	 * @param countryId - country id for which data is to be searched
	 * @param regionId - region/state id for which data is to be searched
	 * @return cities - data consisting of all the cities of the state
	 */
	Optional<Iterable<City>> getCities(String countryId, String regionId);

	/**
	 * Method used to return the city based on country, state and city.
	 *
	 * @param countryId - country id for which data is to be searched
	 * @param regionId - region/state id for which data is to be searched
	 * @return city - data consisting of city
	 */
	Optional<Iterable<City>> getCity(String countryId, String regionId, String cityId);

	/**
	 * Method used to return data based on passed zipcode.
	 *
	 * @param countryId - country id for which data is to be searched
	 * @param zipcode   - zipcode for which data is to be searched
	 * @return zipcodeDTO - data containing details based on zipcode
	 */
	Optional<ZipcodeDTO> getDataBasedOnZipcode(String countryId, long zipcode);

}
