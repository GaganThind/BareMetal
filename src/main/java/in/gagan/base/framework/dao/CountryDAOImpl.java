/*
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import in.gagan.base.framework.dao.base.AbstractBaseDAO;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import in.gagan.base.framework.dto.location.ZipcodeDTO;
import in.gagan.base.framework.entity.location.City;
import in.gagan.base.framework.entity.location.Country;
import in.gagan.base.framework.entity.location.Region;

/**
 * This class is used to CRUD operations on the COUNTRIES table using DAO pattern.
 * 
 * @author gaganthind
 *
 */
@Repository
public class CountryDAOImpl extends AbstractBaseDAO<Country, Long> implements CountryDAO {
	
	private static final String COUNTRY_CLAUSE = " country_name = :countryName ";
	private static final String COUNTRY = "countryName";
	private static final String REGION = "regionName";
	private static final String CITY = "cityName";
	private static final String ZIPCODE = "zipcode";
	private static final String TABLE_COUNTRIES = "COUNTRIES";
	private static final String TABLE_REGIONS = "REGIONS";
	private static final String TABLE_CITIES = "CITIES";
	
	/**
	 * Method used to find country data based on countryId
	 * 
	 * @param countryId: country to search
	 * @return countries: countries data consisting of state, cities and zipcodes
	 */
	@Override
	public Optional<Country> findByCountryId(String countryId) {
		String selectQuery = LITERAL_FROM +
				getTableName() +
				LITERAL_WHERE +
				COUNTRY_CLAUSE;
		
		TypedQuery<Country> query = entityManager.createQuery(selectQuery, Country.class);
		query.setParameter(COUNTRY, countryId);
		
		List<Country> countries = query.getResultList();
		return !CollectionUtils.isEmpty(countries) ? Optional.of(countries.get(0)) : Optional.empty();
	}
	
	/**
	 * Method used to find region data (only) based on countryId.
	 * 
	 * @param countryId: country to search
	 * @return regions: state/region data
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Optional<Iterable<Region>> findRegionsByCountryId(String countryId) {
		String selectQuery = LITERAL_SELECT +
				"regions.* " +
				LITERAL_FROM +
				TABLE_COUNTRIES + " countries " + "INNER JOIN " + TABLE_REGIONS + " regions " +
				LITERAL_WHERE +
				" countries.country_id = regions.country_id " +
				LITERAL_AND +
				" countries.country_name = :countryName ";
		
		Query query = entityManager.createNativeQuery(selectQuery, Region.class);
		query.setParameter(COUNTRY, countryId);
		
		List<Region> regions = query.getResultList();
		
		return !CollectionUtils.isEmpty(regions) ? Optional.of(regions) : Optional.empty();
	}
	
	/**
	 * Method used to find region data along with city data based on country id and region id
	 * 
	 * @param countryId: country to search
	 * @param regionId: region to search
	 * @return region: data consisting of the region/state
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Optional<Region> findRegionByCountryIdAndRegionId(String countryId, String regionId) {
		String selectQuery = LITERAL_SELECT +
				"regions.* " +
				LITERAL_FROM +
				TABLE_COUNTRIES + " countries " + "INNER JOIN " + TABLE_REGIONS + " regions " +
				LITERAL_WHERE +
				" countries.country_id = regions.country_id " +
				LITERAL_AND +
				" countries.country_name = :countryName " +
				LITERAL_AND +
				" regions.region_name = :regionName ";
		
		Query query = entityManager.createNativeQuery(selectQuery, Region.class);
		query.setParameter(COUNTRY, countryId);
		query.setParameter(REGION, regionId);
		
		List<Region> regions = query.getResultList();
		
		return !CollectionUtils.isEmpty(regions) ? Optional.of(regions.get(0)) : Optional.empty();
	}

	/**
	 * Method used to find cities data based on country id and region id
	 *
	 * @param countryId: country to search
	 * @param regionId: region to search
	 * @return cities: data consisting of the cities of region/state
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Optional<Iterable<City>> findCitiesByCountryIdAndRegionId(String countryId, String regionId) {
		String selectQuery = LITERAL_SELECT +
				"cities.* " +
				LITERAL_FROM +
				TABLE_COUNTRIES + " countries " + "INNER JOIN " + TABLE_REGIONS + " regions " +
				" On countries.country_id = regions.country_id " +
				"INNER JOIN " + TABLE_CITIES + " cities " +
				" On regions.region_id = cities.region_id " +
				LITERAL_WHERE +
				" countries.country_name = :countryName " +
				LITERAL_AND +
				" regions.region_name = :regionName ";

		Query query = entityManager.createNativeQuery(selectQuery, City.class);
		query.setParameter(COUNTRY, countryId);
		query.setParameter(REGION, regionId);

		List<City> cities = query.getResultList();

		return !CollectionUtils.isEmpty(cities) ? Optional.of(cities) : Optional.empty();
	}
	
	/**
	 * Method used to find city data based on countryId, regionId and cityId
	 * 
	 * @param countryId: country to search
	 * @param regionId: region/state to search
	 * @param cityId: city to search
	 * @return city: data consisting of city details
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Optional<Iterable<City>> findCityByCountryIdStateIdAndCityId(String countryId, String regionId, String cityId) {
		String selectQuery = LITERAL_SELECT +
				"cities.* " +
				LITERAL_FROM +
				TABLE_COUNTRIES + " countries " + "INNER JOIN " + TABLE_REGIONS + " regions " +
				" On countries.country_id = regions.country_id " +
				"INNER JOIN " + TABLE_CITIES + " cities " +
				" On regions.region_id = cities.region_id " +
				LITERAL_WHERE +
				" countries.country_name = :countryName " +
				LITERAL_AND +
				" regions.region_name = :regionName " +
				LITERAL_AND +
				" cities.city_name = :cityName ";
		
		Query query = entityManager.createNativeQuery(selectQuery, City.class);
		query.setParameter(COUNTRY, countryId);
		query.setParameter(REGION, regionId);
		query.setParameter(CITY, cityId);
		
		List<City> cities = query.getResultList();
		
		return !CollectionUtils.isEmpty(cities) ? Optional.of(cities) : Optional.empty();
	}
	
	/**
	 * Method used to find country data based on countryId and zipcode
	 * 
	 * @param countryId: country to search
	 * @param zipcode: zipcode to search
	 * @return zipcodeDTO: zipcode data consisting of region/state, city and country names
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Optional<ZipcodeDTO> findZipcodeDataByCountryIdAndZipcode(String countryId, long zipcode) {
		String selectQuery = LITERAL_SELECT +
				" countries.country_name, regions.region_name, cities.city_name, cities.zipcode " +
				LITERAL_FROM +
				TABLE_COUNTRIES + " countries " + "INNER JOIN " + TABLE_REGIONS + " regions " +
				" On countries.country_id = regions.country_id " +
				"INNER JOIN " + TABLE_CITIES + " cities " +
				" On regions.region_id = cities.region_id " +
				LITERAL_WHERE +
				" countries.country_name = :countryName " +
				LITERAL_AND +
				" cities.zipcode LIKE CONCAT('%',:zipcode,'%')";
		
		Query query = entityManager.createNativeQuery(selectQuery);
		query.setParameter(COUNTRY, countryId);
		query.setParameter(ZIPCODE, zipcode);
		
		List zipData = query.getResultList();
		if (!CollectionUtils.isEmpty(zipData)) {
			Object[] data = ((ArrayList<Object[]>) zipData).get(0);
			return Optional.of(new ZipcodeDTO(zipcode, (String) data[2], (String) data[1], (String) data[0]));
		}
		
		return Optional.empty();
	}

}
