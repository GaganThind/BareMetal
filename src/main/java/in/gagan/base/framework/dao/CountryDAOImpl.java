package in.gagan.base.framework.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import in.gagan.base.framework.entity.Country;

/**
 * This class is used to CRUD operations on the COUNTRIES table using DAO pattern.
 * 
 * @author gaganthind
 *
 */
@Repository
public class CountryDAOImpl extends AbstractBaseDAO<Country, Long> implements CountryDAO {
	
	private static final String COUNTRY_CLAUSE = " country = :country ";
	private static final String STATE_CLAUSE = " state = :state ";
	private static final String CITY_CLAUSE = " city = :city ";
	private static final String ZIPCODE_CLAUSE = " zipcode = :zipcode ";
	private static final String COUNTRY = "country";
	private static final String STATE = "state";
	private static final String CITY = "city";
	private static final String ZIPCODE = "zipcode";
	
	/**
	 * Method used to find country data based on countryId
	 * 
	 * @param countryId: country to search
	 * @return countries: countries data consisting of state, cities and zipcodes
	 */
	@Override
	public Optional<Iterable<Country>> findbyCountryId(String countryId) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append(LITERAL_FROM);
		selectQuery.append(getTableName());
		selectQuery.append(LITREAL_WHERE);
		selectQuery.append(COUNTRY_CLAUSE);
		
		TypedQuery<Country> query = entityManager.createQuery(selectQuery.toString(), Country.class);
		query.setParameter(COUNTRY, countryId);
		
		List<Country> countries = query.getResultList();
		return !CollectionUtils.isEmpty(countries) ? Optional.of(countries) : Optional.empty();
	}
	
	/**
	 * Method used to find country data based on countryId and stateId
	 * 
	 * @param countryId: country to search
	 * @param stateId: state to search
	 * @return countries: countries data consisting of state, cities and zipcodes
	 */
	@Override
	public Optional<Iterable<Country>> findbyCountryIdAndStateId(String countryId, String stateId) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append(LITERAL_FROM);
		selectQuery.append(getTableName());
		selectQuery.append(LITREAL_WHERE);
		selectQuery.append(COUNTRY_CLAUSE);
		selectQuery.append(LITREAL_AND);
		selectQuery.append(STATE_CLAUSE);
		
		TypedQuery<Country> query = entityManager.createQuery(selectQuery.toString(), Country.class);
		query.setParameter(COUNTRY, countryId);
		query.setParameter(STATE, stateId);
		
		List<Country> countries = query.getResultList();
		return !CollectionUtils.isEmpty(countries) ? Optional.of(countries) : Optional.empty();
	}
	
	/**
	 * Method used to find country data based on countryId, stateId and cityId
	 * 
	 * @param countryId: country to search
	 * @param stateId: state to search
	 * @param cityId: city to search
	 * @return countries: countries data consisting of state, cities and zipcodes
	 */
	@Override
	public Optional<Iterable<Country>> findbyCountryIdStateIdAndCityId(String countryId, String stateId, String cityId) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append(LITERAL_FROM);
		selectQuery.append(getTableName());
		selectQuery.append(LITREAL_WHERE);
		selectQuery.append(COUNTRY_CLAUSE);
		selectQuery.append(LITREAL_AND);
		selectQuery.append(STATE_CLAUSE);
		selectQuery.append(LITREAL_AND);
		selectQuery.append(CITY_CLAUSE);
		
		TypedQuery<Country> query = entityManager.createQuery(selectQuery.toString(), Country.class);
		query.setParameter(COUNTRY, countryId);
		query.setParameter(STATE, stateId);
		query.setParameter(CITY, cityId);
		
		List<Country> countries = query.getResultList();
		return !CollectionUtils.isEmpty(countries) ? Optional.of(countries) : Optional.empty();
	}
	
	/**
	 * Method used to find country data based on countryId and zipcode
	 * 
	 * @param countryId: country to search
	 * @param zipcode: zipcode to search
	 * @return country: country data consisting of state, citie and zipcode
	 */
	@Override
	public Optional<Country> findbyCountryIdAndZipcode(String countryId, long zipcode) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append(LITERAL_FROM);
		selectQuery.append(getTableName());
		selectQuery.append(LITREAL_WHERE);
		selectQuery.append(COUNTRY_CLAUSE);
		selectQuery.append(LITREAL_AND);
		selectQuery.append(ZIPCODE_CLAUSE);
		
		TypedQuery<Country> query = entityManager.createQuery(selectQuery.toString(), Country.class);
		query.setParameter(COUNTRY, countryId);
		query.setParameter(ZIPCODE, zipcode);
		
		List<Country> countries = query.getResultList();
		return !CollectionUtils.isEmpty(countries) ? Optional.of(countries.get(0)) : Optional.empty();
	}

}
