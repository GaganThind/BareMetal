package in.gagan.base.framework.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import in.gagan.base.framework.dto.ZipcodeDTO;
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
	public Optional<Country> findbyCountryId(String countryId) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append(LITERAL_FROM);
		selectQuery.append(getTableName());
		selectQuery.append(LITREAL_WHERE);
		selectQuery.append(COUNTRY_CLAUSE);
		
		TypedQuery<Country> query = entityManager.createQuery(selectQuery.toString(), Country.class);
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
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append(LITERAL_SELECT);
		selectQuery.append("regions.* ");
		selectQuery.append(LITERAL_FROM);
		selectQuery.append(TABLE_COUNTRIES).append(" countries ").append("INNER JOIN ").append(TABLE_REGIONS).append(" regions ");
		selectQuery.append(LITREAL_WHERE);
		selectQuery.append(" countries.country_id = regions.country_id ");
		selectQuery.append(LITREAL_AND);
		selectQuery.append(" countries.country_name = :countryName ");
		
		Query query = entityManager.createNativeQuery(selectQuery.toString(), Region.class);
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
	public Optional<Region> findRegionbyCountryIdAndRegionId(String countryId, String regionId) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append(LITERAL_SELECT);
		selectQuery.append("regions.* ");
		selectQuery.append(LITERAL_FROM);
		selectQuery.append(TABLE_COUNTRIES).append(" countries ").append("INNER JOIN ").append(TABLE_REGIONS).append(" regions ");
		selectQuery.append(LITREAL_WHERE);
		selectQuery.append(" countries.country_id = regions.country_id ");
		selectQuery.append(LITREAL_AND);
		selectQuery.append(" countries.country_name = :countryName ");
		selectQuery.append(LITREAL_AND);
		selectQuery.append(" regions.region_name = :regionName ");
		
		Query query = entityManager.createNativeQuery(selectQuery.toString(), Region.class);
		query.setParameter(COUNTRY, countryId);
		query.setParameter(REGION, regionId);
		
		List<Region> regions = query.getResultList();
		
		return !CollectionUtils.isEmpty(regions) ? Optional.of(regions.get(0)) : Optional.empty();
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
	public Optional<Iterable<City>> findCitybyCountryIdStateIdAndCityId(String countryId, String regionId, String cityId) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append(LITERAL_SELECT);
		selectQuery.append("cities.* ");
		selectQuery.append(LITERAL_FROM);
		selectQuery.append(TABLE_COUNTRIES).append(" countries ").append("INNER JOIN ").append(TABLE_REGIONS).append(" regions ");
		selectQuery.append(" On countries.country_id = regions.country_id ");
		selectQuery.append("INNER JOIN ").append(TABLE_CITIES).append(" cities ");
		selectQuery.append(" On regions.region_id = cities.region_id ");
		selectQuery.append(LITREAL_WHERE);
		selectQuery.append(" countries.country_name = :countryName ");
		selectQuery.append(LITREAL_AND);
		selectQuery.append(" regions.region_name = :regionName ");
		selectQuery.append(LITREAL_AND);
		selectQuery.append(" cities.city_name = :cityName ");
		
		Query query = entityManager.createNativeQuery(selectQuery.toString(), City.class);
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
	public Optional<ZipcodeDTO> findZipcodeDatabyCountryIdAndZipcode(String countryId, long zipcode) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append(LITERAL_SELECT);
		selectQuery.append(" countries.country_name, regions.region_name, cities.city_name, cities.zipcode ");
		selectQuery.append(LITERAL_FROM);
		selectQuery.append(TABLE_COUNTRIES).append(" countries ").append("INNER JOIN ").append(TABLE_REGIONS).append(" regions ");
		selectQuery.append(" On countries.country_id = regions.country_id ");
		selectQuery.append("INNER JOIN ").append(TABLE_CITIES).append(" cities ");
		selectQuery.append(" On regions.region_id = cities.region_id ");
		selectQuery.append(LITREAL_WHERE);
		selectQuery.append(" countries.country_name = :countryName ");
		selectQuery.append(LITREAL_AND);
		selectQuery.append(" cities.zipcode LIKE CONCAT('%',:zipcode,'%')");
		
		Query query = entityManager.createNativeQuery(selectQuery.toString());
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
