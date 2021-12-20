package in.gagan.base.framework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.gagan.base.framework.dto.CountryDTO;
import in.gagan.base.framework.dto.StateDTO;
import in.gagan.base.framework.dto.ZipcodeDTO;
import in.gagan.base.framework.service.AddressService;

/**
 * This controller class provides the functionality for retrieving city, state, country and zip.
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
	
	@GetMapping(value = "/country/{countryId}/states", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CountryDTO> getStates(@PathVariable String countryId) {
		CountryDTO country = this.addressSvc.getCountryData(countryId);
		return new ResponseEntity<CountryDTO>(country, HttpStatus.OK);
	}
	
	@GetMapping(value = "/country/{countryId}/states/{stateId}/cities", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StateDTO> getCities(@PathVariable String countryId, @PathVariable String stateId) {
		StateDTO state = this.addressSvc.getStateData(countryId, stateId);
		return new ResponseEntity<StateDTO>(state, HttpStatus.OK);
	}
	
	@GetMapping(value = "/country/{countryId}/zipcode/{zipcode}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ZipcodeDTO> getDataBasedOnZipCode(@PathVariable String countryId, @PathVariable Long zipcode) {
		ZipcodeDTO zipcodeDto = this.addressSvc.getDataBasedOnZipcode(countryId, zipcode);
		return new ResponseEntity<ZipcodeDTO>(zipcodeDto, HttpStatus.OK);
	}
	
}
