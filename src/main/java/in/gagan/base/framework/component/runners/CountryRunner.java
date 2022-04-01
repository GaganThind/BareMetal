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

package in.gagan.base.framework.component.runners;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.dao.CountryDAO;
import in.gagan.base.framework.entity.location.City;
import in.gagan.base.framework.entity.location.Country;
import in.gagan.base.framework.entity.location.Region;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * This class is used to add the Country data to Country, Region and City tables.
 *
 * @author gaganthind
 */
@Order(2)
@Component
public class CountryRunner implements CommandLineRunner {

    private static final String INDIA = "India";

    private final CountryDAO countryDAO;

    @Value("${application.countries.file.location}")
    private String fileLocation;

    @Value("${application.countries.file.force.reload}")
    private String forceCSVReloadOnBoot;

    @Autowired
    public CountryRunner(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if(isDataAlreadyLoaded()) {
            return;
        }

        List<CountryInputDTO> countryList = getCountryInputDTOs();
        Set<Country> countries = toEntities(countryList);
        this.countryDAO.saveAll(countries);
    }

    private boolean isDataAlreadyLoaded() {
        return ApplicationConstants.String_N.equals(forceCSVReloadOnBoot)
                && doesIndiaExistsInDatabase();
    }

    private boolean doesIndiaExistsInDatabase() {
        return this.countryDAO.findByCountryId(INDIA).isPresent();
    }

    private List<CountryInputDTO> getCountryInputDTOs() {
        List<CountryInputDTO> countryList = readCountryFromCSVIntoList();
        if (CollectionUtils.isEmpty(countryList)) {
            throw new IllegalStateException("Country list is empty, Kindly check the data csv file");
        }
        return countryList;
    }

    private List<CountryInputDTO> readCountryFromCSVIntoList() throws RuntimeException {
        try (InputStream targetStream = new FileInputStream(fileLocation)) {
            List<CountryInputDTO> countryList = CsvUtils.read(CountryInputDTO.class, targetStream);
            return countryList;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Set<Country> toEntities(List<CountryInputDTO> countries) {
        Set<Country> countrySet = new HashSet<>();
        for (CountryInputDTO countryDTO: countries) {
            addRecordToCountry(countrySet, countryDTO);
        }
        return countrySet;
    }

    private void addRecordToCountry(Set<Country> countrySet, CountryInputDTO countryDTO) {
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

    private static class CsvUtils {

        private static final CsvMapper mapper = new CsvMapper();

        public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
            CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
            ObjectReader reader = mapper.readerFor(clazz).with(schema);
            return reader.<T>readValues(stream).readAll();
        }
    }

    /**
     * This DTO captures the country specific data from csv file.
     *
     * @author gaganthind
     *
     */
    private static class CountryInputDTO {

        private String city;

        private String state;

        private String country;

        private long zipcode;

        public CountryInputDTO() { }

        public CountryInputDTO(String city, String state, String country, long zipcode) {
            this.city = city;
            this.state = state;
            this.country = country;
            this.zipcode = zipcode;
        }

        public long getZipcode() {
            return zipcode;
        }

        public void setZipcode(long zipcode) {
            this.zipcode = zipcode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CountryInputDTO that = (CountryInputDTO) o;
            return zipcode == that.zipcode && city.equals(that.city) && state.equals(that.state) && country.equals(that.country);
        }

        @Override
        public int hashCode() {
            return Objects.hash(city, state, country, zipcode);
        }
    }
}
