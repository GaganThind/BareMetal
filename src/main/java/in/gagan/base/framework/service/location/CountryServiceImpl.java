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

package in.gagan.base.framework.service.location;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.dao.CountryDAO;
import in.gagan.base.framework.dto.location.CountryInputDTO;
import in.gagan.base.framework.entity.location.City;
import in.gagan.base.framework.entity.location.Country;
import in.gagan.base.framework.entity.location.Region;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class CountryServiceImpl implements CountryService {
	
	private final CountryDAO countryDAO;
	
	@Autowired
	public CountryServiceImpl(CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}
	
	@Value("${application.countries.file.location}")
	private String fileLocation;
	
	@Value("${application.countries.file.force.reload}")
	private String forceCSVReloadOnBoot;

	/**
	 * Method used to load country data from csv file.
	 */
	@Override
	public void loadCountriesFromCSV() {
		
		if(ApplicationConstants.String_N.equals(forceCSVReloadOnBoot) && this.countryDAO.findByCountryId("India").isPresent()) {
			return;
		}
		
		File initialFile = new File(fileLocation);
		List<CountryInputDTO> countryList;
	    
		try {
			InputStream targetStream = new FileInputStream(initialFile);
			countryList = CsvUtils.read(CountryInputDTO.class, targetStream);
		} catch(IOException e) {
			throw new IllegalArgumentException("Data csv containing Country data is not found or is not able to read");
		}
		
		if (CollectionUtils.isEmpty(countryList)) {
			throw new IllegalArgumentException("Country list is empty, Kindly check the data csv file");
		}
		
		Set<Country> countries = convertToEntities(countryList);
		
		this.countryDAO.saveAll(countries);
	}
	
	private Set<Country> convertToEntities(List<CountryInputDTO> countries) {
		Set<Country> countrySet = new HashSet<>();
		
		for (CountryInputDTO countryDTO: countries) {
			String countryName = countryDTO.getCountry();
			
			Optional<Country> countryOpt = countrySet.stream()
														.filter(c -> c.getCountryName().equals(countryName))
														.findFirst();
			Country country = countryOpt.orElse(new Country(countryName));
			
			String regionName = countryDTO.getState();
			Optional<Region> regionOpt = country.getRegions().stream()
																.filter(r -> r.getRegionName().equals(regionName))
																.findFirst();
			Region region = regionOpt.orElse(new Region(regionName));
			
			region.addCity(new City(countryDTO.getCity(), countryDTO.getZipcode()));
			country.addRegion(region);
			countrySet.add(country);
		}
		
		return countrySet;
	}

	private static class CsvUtils {

		private static final CsvMapper mapper = new CsvMapper();

		public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
			CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
			ObjectReader reader = mapper.readerFor(clazz).with(schema);
			return reader.<T>readValues(stream).readAll();
		}
	}
}
