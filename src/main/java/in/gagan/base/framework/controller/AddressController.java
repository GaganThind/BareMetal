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

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.gagan.base.framework.dto.CityDTO;
import in.gagan.base.framework.dto.CountryDTO;
import in.gagan.base.framework.dto.RegionDTO;
import in.gagan.base.framework.dto.ZipcodeDTO;
import in.gagan.base.framework.service.AddressService;

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
		Optional<Set<CountryDTO>> countryDTOs = this.addressSvc.getCountries();
		Set<CountryDTO> countries = countryDTOs.orElseThrow(noSuchElementExceptionSupplier(
				message.getMessage("message.address.countries.list.empty", null, Locale.ENGLISH)));
		return new ResponseEntity<Set<CountryDTO>>(countries, HttpStatus.OK);
	}

	/**
	 * Method used get all the countries.
	 * 
	 * @return countries - data consisting of all the countries
	 */
	@GetMapping(value = "/country", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<CountryDTO>> getCountries() {
		Optional<Set<CountryDTO>> countryDTOs = this.addressSvc.getCountries();
		Set<CountryDTO> countries = countryDTOs.orElseThrow(noSuchElementExceptionSupplier(
				message.getMessage("message.address.countries.list.empty", null, Locale.ENGLISH)));
		return new ResponseEntity<Set<CountryDTO>>(countries, HttpStatus.OK);
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
		Optional<CountryDTO> countryDTO = this.addressSvc.getCountry(countryId);
		CountryDTO country = countryDTO.orElseThrow(noSuchElementExceptionSupplier(
				message.getMessage("message.address.country.not.found", new Object[] { countryId }, Locale.ENGLISH)));
		return new ResponseEntity<CountryDTO>(country, HttpStatus.OK);
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
		Optional<Set<RegionDTO>> regionDTOs = this.addressSvc.getRegions(countryId);
		Set<RegionDTO> regions = regionDTOs.orElseThrow(noSuchElementExceptionSupplier(
				message.getMessage("message.address.states.not.found", new Object[] { countryId }, Locale.ENGLISH)));
		return new ResponseEntity<Set<RegionDTO>>(regions, HttpStatus.OK);
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
		Optional<RegionDTO> regionDTO = this.addressSvc.getRegion(countryId, stateId);
		RegionDTO state = regionDTO.orElseThrow(noSuchElementExceptionSupplier(message
				.getMessage("message.address.state.not.found", new Object[] { stateId, countryId }, Locale.ENGLISH)));
		return new ResponseEntity<RegionDTO>(state, HttpStatus.OK);
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
		Optional<Set<CityDTO>> cityDTOs = this.addressSvc.getCities(countryId, stateId);
		Set<CityDTO> cities = cityDTOs.orElseThrow(noSuchElementExceptionSupplier(message
				.getMessage("message.address.cities.not.found", new Object[] { stateId, countryId }, Locale.ENGLISH)));
		return new ResponseEntity<Set<CityDTO>>(cities, HttpStatus.OK);
	}

	/**
	 * Method used to return the city based on country, state and city.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param stateId   - state id for which data is to be searched
	 * @return city - data consisting of city details
	 */
	@GetMapping(value = "/country/{countryId}/states/{stateId}/cities/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CityDTO> getCities(@PathVariable String countryId, @PathVariable String stateId,
			@PathVariable String cityId) {
		Optional<CityDTO> cityDTO = this.addressSvc.getCity(countryId, stateId, cityId);
		CityDTO city = cityDTO
				.orElseThrow(noSuchElementExceptionSupplier(message.getMessage("message.address.city.not.found",
						new Object[] { cityId, stateId, countryId }, Locale.ENGLISH)));
		return new ResponseEntity<CityDTO>(city, HttpStatus.OK);
	}

	/**
	 * Method used to return data based on passed zipcode.
	 * 
	 * @param countryId - country id for which data is to be searched
	 * @param zipcode   - state id for which data is to be searched
	 * @return zipcode - data containing details based on zipcode
	 */
	@GetMapping(value = "/country/{countryId}/zipcode/{zipcode}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ZipcodeDTO> getDataBasedOnZipCode(@PathVariable String countryId,
			@PathVariable Long zipcode) {
		Optional<ZipcodeDTO> zipcodeDTO = this.addressSvc.getDataBasedOnZipcode(countryId, zipcode);
		ZipcodeDTO zipcodeObj = zipcodeDTO.orElseThrow(noSuchElementExceptionSupplier(message
				.getMessage("message.address.zipcode.not.found", new Object[] { zipcode, countryId }, Locale.ENGLISH)));
		return new ResponseEntity<ZipcodeDTO>(zipcodeObj, HttpStatus.OK);
	}

	private Supplier<? extends NoSuchElementException> noSuchElementExceptionSupplier(String message) {
		return () -> new NoSuchElementException(message);
	}

}
