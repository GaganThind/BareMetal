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

package in.gagan.base.framework.controller;

import in.gagan.base.framework.controller.base.AbstractController;
import in.gagan.base.framework.dto.location.CityDTO;
import in.gagan.base.framework.dto.location.CountryDTO;
import in.gagan.base.framework.dto.location.RegionDTO;
import in.gagan.base.framework.dto.location.ZipcodeDTO;
import in.gagan.base.framework.entity.location.City;
import in.gagan.base.framework.entity.location.Country;
import in.gagan.base.framework.entity.location.Region;
import in.gagan.base.framework.service.location.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static in.gagan.base.framework.util.ExceptionHelperUtil.noSuchElementExceptionSupplier;
import static java.util.stream.StreamSupport.stream;

/**
 * This controller class provides the functionality for retrieving city, state,
 * country and zip.
 * 
 * @author gaganthind
 *
 */
@RestController
@RequestMapping(value = "/v1/address")
public class AddressController extends AbstractController {

	private final AddressService addressSvc;

	@Autowired
	public AddressController(AddressService addressSvc) {
		this.addressSvc = addressSvc;
	}
	
	/**
	 * Method used get all the countries.
	 * 
	 * @return countries - data consisting of all the countries
	 */
	@GetMapping(value = "/admin/country", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<CountryDTO>> getCountriesForAdmin() {
		final String message = getMessage("message.address.countries.list.empty");
		Iterable<Country> countries =
				this.addressSvc.getCountriesForAdmin()
								.orElseThrow(noSuchElementExceptionSupplier(message));

		// return all data inside country object including regions/states and cities
		Set<CountryDTO> countryDTOs = stream(countries.spliterator(), false)
										.map(this::convertToDTO)
										.collect(Collectors.toSet());

		return new ResponseEntity<>(countryDTOs, HttpStatus.OK);
	}

	/**
	 * Method used get all the countries' names.
	 * 
	 * @return countries - data consisting of all the countries
	 */
	@GetMapping(value = "/country", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<CountryDTO>> getCountries() {
		final String message = getMessage("message.address.countries.list.empty");
		Iterable<Country> countries =
				this.addressSvc.getCountries()
						.orElseThrow(noSuchElementExceptionSupplier(message));

		// Return only names
		Set<CountryDTO> countryDTOs = stream(countries.spliterator(), false)
										.map(country -> new CountryDTO(country.getCountryName()))
										.collect(Collectors.toSet());

		return new ResponseEntity<>(countryDTOs, HttpStatus.OK);
	}

	/**
	 * Method used to return the country data. This method would only return country
	 * specific info and not state or city info.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @return country - data consisting of all the states and respective cities
	 */
	@GetMapping(value = "/country/{countryId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CountryDTO> getCountry(@PathVariable String countryId) {
		final String message = getMessage("message.address.country.not.found", countryId);
		Country country =
				this.addressSvc.getCountry(countryId)
						.orElseThrow(noSuchElementExceptionSupplier(message));

		Set<RegionDTO> sortedSet = getSortedRegionDTOS(country.getRegions());
		CountryDTO countryDTO = new CountryDTO(country.getCountryName(), sortedSet);

		return new ResponseEntity<>(countryDTO, HttpStatus.OK);
	}

	/**
	 * Method used to return all the states data based on passed country.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @return states - data consisting of all the states of the country and
	 *         respective cities
	 */
	@GetMapping(value = "/country/{countryId}/states", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<RegionDTO>> getRegions(@PathVariable String countryId) {
		final String message = getMessage("message.address.states.not.found", countryId);
		Iterable<Region> regions =
				this.addressSvc.getRegions(countryId)
						.orElseThrow(noSuchElementExceptionSupplier(message));

		Set<RegionDTO> sortedSet = getSortedRegionDTOS(regions);

		return new ResponseEntity<>(sortedSet, HttpStatus.OK);
	}

	/**
	 * Method used to return the state data based on passed state.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param stateId   - state id for which data is to be searched
	 * @return state - data consisting of all the cities of the state
	 */
	@GetMapping(value = "/country/{countryId}/states/{stateId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegionDTO> getRegion(@PathVariable String countryId, @PathVariable String stateId) {
		final String message = getMessage("message.address.state.not.found", stateId, countryId);
		Region region =
				this.addressSvc.getRegion(countryId, stateId)
						.orElseThrow(noSuchElementExceptionSupplier(message));

		Set<CityDTO> sortedSet = getSortedCityDTOS(region.getCities());
		RegionDTO regionDTO = new RegionDTO(region.getRegionName(), sortedSet);

		return new ResponseEntity<>(regionDTO, HttpStatus.OK);
	}

	/**
	 * Method used to return the cities based on country and state.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param stateId   - state id for which data is to be searched
	 * @return cities - data consisting of all the cities of the state
	 */
	@GetMapping(value = "/country/{countryId}/states/{stateId}/cities", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<CityDTO>> getCities(@PathVariable String countryId, @PathVariable String stateId) {
		final String message = getMessage("message.address.cities.not.found", stateId, countryId);
		Iterable<City> cities =
				this.addressSvc.getCities(countryId, stateId)
						.orElseThrow(noSuchElementExceptionSupplier(message));

		Set<CityDTO> sortedSet = getSortedCityDTOS(cities);

		return new ResponseEntity<>(sortedSet, HttpStatus.OK);
	}

	/**
	 * Method used to return the city based on country, state and city.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param stateId   - state id for which data is to be searched
	 * @return city - data consisting of city details
	 */
	@GetMapping(value = "/country/{countryId}/states/{stateId}/cities/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CityDTO> getCity(@PathVariable String countryId, @PathVariable String stateId,
			@PathVariable String cityId) {
		final String message = getMessage("message.address.city.not.found", cityId, stateId, countryId);
		Iterable<City> cities =
				this.addressSvc.getCity(countryId, stateId, cityId)
						.orElseThrow(noSuchElementExceptionSupplier(message));

		Set<Long> zipcodes = stream(cities.spliterator(), false)
								.map(City::getZipcode)
								.sorted()
								.collect(Collectors.toCollection(LinkedHashSet::new));

		CityDTO cityDTO = new CityDTO(((List<City>) cities).get(0).getCityName(), zipcodes);

		return new ResponseEntity<>(cityDTO, HttpStatus.OK);
	}

	/**
	 * Method used to return data based on passed zipcode.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param zipcode   - state id for which data is to be searched
	 * @return zipcode - data containing details based on zipcode
	 */
	@GetMapping(value = "/country/{countryId}/zipcode/{zipcode}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ZipcodeDTO> getDataBasedOnZipCode(@PathVariable String countryId, @PathVariable Long zipcode) {
		final String message = getMessage("message.address.zipcode.not.found", zipcode, countryId);
		ZipcodeDTO zipcodeObj =
				this.addressSvc.getDataBasedOnZipcode(countryId, zipcode)
								.orElseThrow(noSuchElementExceptionSupplier(message));

		return new ResponseEntity<>(zipcodeObj, HttpStatus.OK);
	}

	private Set<RegionDTO> getSortedRegionDTOS(Iterable<Region> regions) {
		return stream(regions.spliterator(), false)
				.map(Region::getRegionName)
				.map(RegionDTO::new)
				.sorted(Comparator.comparing(RegionDTO::getName))
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	private Set<CityDTO> getSortedCityDTOS(Iterable<City> cities) {
		return stream(cities.spliterator(), false)
				.map(City::getCityName)
				.map(CityDTO::new)
				.sorted(Comparator.comparing(CityDTO::getName))
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	private CountryDTO convertToDTO(Country country) {
		CountryDTO countryDTO = new CountryDTO(country.getCountryName());

		for (Region region : country.getRegions()) {
			RegionDTO regionDTO = new RegionDTO(region.getRegionName());

			for (City city : region.getCities()) {
				Set<CityDTO> cityDTOs = regionDTO.getCities();
				String cityName = city.getCityName();

				Optional<CityDTO> cityOpt = cityDTOs.stream()
													.filter(c -> c.getName().equals(cityName))
													.findFirst();

				CityDTO cityDTO = cityOpt.orElse(new CityDTO(cityName));
				cityDTO.addZipcode(city.getZipcode());

				regionDTO.addCity(cityDTO);
			}

			countryDTO.addRegion(regionDTO);
		}

		return countryDTO;
	}

}
