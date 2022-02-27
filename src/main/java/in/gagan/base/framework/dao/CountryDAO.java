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

package in.gagan.base.framework.dao;

import java.util.Optional;

import in.gagan.base.framework.dto.ZipcodeDTO;
import in.gagan.base.framework.entity.location.City;
import in.gagan.base.framework.entity.location.Country;
import in.gagan.base.framework.entity.location.Region;

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
	 * @return country: country data consisting of state, cities and zipcodes
	 */
	public Optional<Country> findbyCountryId(String countryId);
	
	/**
	 * Method used to find region data (only) based on countryId.
	 * 
	 * @param countryId: country to search
	 * @return regions: state/region data
	 */
	public Optional<Iterable<Region>> findRegionsByCountryId(String countryId);
	
	/**
	 * Method used to find region data along with city data based on country id and region id
	 * 
	 * @param countryId: country to search
	 * @param regionId: region to search
	 * @return region: data consisting of the region/state
	 */
	public Optional<Region> findRegionbyCountryIdAndRegionId(String countryId, String regionId);
	
	/**
	 * Method used to find city data based on countryId, regionId and cityId
	 * 
	 * @param countryId: country to search
	 * @param regionId: region/state to search
	 * @param cityId: city to search
	 * @return city: data consisting of city details
	 */
	public Optional<Iterable<City>> findCitybyCountryIdStateIdAndCityId(String countryId, String regionId, String cityId);
	
	/**
	 * Method used to find country data based on countryId and zipcode
	 * 
	 * @param countryId: country to search
	 * @param zipcode: zipcode to search
	 * @return zipcodeDTO: zipcode data consisting of region/state, city and country names
	 */
	public Optional<ZipcodeDTO> findZipcodeDatabyCountryIdAndZipcode(String countryId, long zipcode);

}
