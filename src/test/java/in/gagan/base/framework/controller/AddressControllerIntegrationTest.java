/*
 * Copyright (C) 2020-2022  Gagan Thind
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package in.gagan.base.framework.controller;

import in.gagan.base.framework.BareMetalApplication;
import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.dto.location.CityDTO;
import in.gagan.base.framework.dto.location.CountryDTO;
import in.gagan.base.framework.dto.location.RegionDTO;
import in.gagan.base.framework.dto.location.ZipcodeDTO;
import in.gagan.base.framework.entity.user.Role;
import in.gagan.base.framework.entity.user.User;
import in.gagan.base.framework.service.location.AddressService;
import in.gagan.base.framework.service.user.UserDataService;
import in.gagan.base.framework.util.TestRestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Set;

import static in.gagan.base.framework.enums.UserRoles.ADMIN;
import static in.gagan.base.framework.enums.UserRoles.USER;
import static in.gagan.base.framework.util.CreateUserUtil.USER_PASSWORD;

/**
 * This class is used to test the functionality of the AddressController class.
 *
 * @author gaganthind
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BareMetalApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressControllerIntegrationTest {

    @Autowired
    private AddressService addressSvc;

    @Autowired
    private UserDataService userDataSvc;

    @Autowired
    private TestRestUtil testRestUtil;

    private static final String INTEGRATION_TEST_USER = "addressintergrationtest@e.com";

    private static boolean initialized = false;

    @Transactional
    @Before
    public void setup() {
        if (initialized) {
            return;
        }

        User adminUser = new User("Admin", "Testing", INTEGRATION_TEST_USER, USER_PASSWORD);
        adminUser.addRole(new Role(ADMIN));
        adminUser.setActiveSw(ApplicationConstants.CHAR_Y);
        this.userDataSvc.saveUser(adminUser);

        User nonAdminUser = new User("NonAdmin", "Testing", "nonadminaddress@e.com",
                USER_PASSWORD);
        nonAdminUser.addRole(new Role(USER));
        nonAdminUser.setActiveSw(ApplicationConstants.CHAR_Y);
        this.userDataSvc.saveUser(nonAdminUser);

        initialized = true;
    }

    @Test
    public void testCountriesLoaded() {
        Assert.assertTrue(this.addressSvc.getCountry("India").isPresent());
    }

    @Test
    public void testGetCountriesForAdminWithAdminUser() {
        ResponseEntity<Set<CountryDTO>> responseEntity =
                this.testRestUtil.get(
                        new ParameterizedTypeReference<>() { },
                        "/v1/address/admin/country",
                        INTEGRATION_TEST_USER,
                        USER_PASSWORD
                );

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Set<CountryDTO> countryDTOs = responseEntity.getBody();
        Assert.assertNotNull("Response object is null", countryDTOs);
        Assert.assertFalse("Country list is empty", countryDTOs.isEmpty());
    }

    @Test
    public void testGetCountriesForAdminWithNonAdminUser() {
        ResponseEntity<Set<CountryDTO>> responseEntity =
                this.testRestUtil.get(
                        new ParameterizedTypeReference<>() { },
                        "/v1/address/admin/country",
                        "nonadminaddress@e.com",
                        USER_PASSWORD
                );

        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    public void testGetCountryNames() {
        ResponseEntity<Set<CountryDTO>> responseEntity =
                this.testRestUtil.get(
                        new ParameterizedTypeReference<>() { },
                        "/v1/address/country",
                        "nonadminaddress@e.com",
                        USER_PASSWORD
                );

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Set<CountryDTO> countryDTOs = responseEntity.getBody();
        Assert.assertNotNull("Response object is null", countryDTOs);
        Assert.assertFalse("Country names are empty", countryDTOs.isEmpty());
    }

    @Test
    public void testGetCountry() {
        ResponseEntity<CountryDTO> responseEntity =
                this.testRestUtil.get(
                        CountryDTO.class,
                        "/v1/address/country/India",
                        "nonadminaddress@e.com",
                        USER_PASSWORD
                );

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        CountryDTO countryDTO = responseEntity.getBody();
        Assert.assertNotNull("Response object is null", countryDTO);
        Assert.assertNotNull("Country name cannot be null", countryDTO.getName());
    }

    @Test
    public void testGetCountryWithInvalidCountryId() {
        ResponseEntity<CountryDTO> responseEntity =
                this.testRestUtil.get(
                        CountryDTO.class,
                        "/v1/address/country/IndiaNot",
                        "nonadminaddress@e.com",
                        USER_PASSWORD
                );

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testGetRegions() {
        ResponseEntity<Set<RegionDTO>> responseEntity =
                this.testRestUtil.get(
                        new ParameterizedTypeReference<>() { },
                        "/v1/address/country/India/states",
                        "nonadminaddress@e.com",
                        USER_PASSWORD
                );

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Set<RegionDTO> regionDTOs = responseEntity.getBody();
        Assert.assertNotNull("Response object is null", regionDTOs);
        Assert.assertFalse("Region list is empty", regionDTOs.isEmpty());
    }

    @Test
    public void testGetRegion() {
        ResponseEntity<RegionDTO> responseEntity =
                this.testRestUtil.get(
                        RegionDTO.class,
                        "/v1/address/country/India/states/Goa",
                        "nonadminaddress@e.com",
                        USER_PASSWORD
                );

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        RegionDTO regionDTO = responseEntity.getBody();
        Assert.assertNotNull("Response object is null", regionDTO);
        Assert.assertNotNull("Region name cannot be null", regionDTO.getName());
    }

    @Test
    public void testGetRegionWithInvalidRegionId() {
        ResponseEntity<RegionDTO> responseEntity =
                this.testRestUtil.get(
                        RegionDTO.class,
                        "/v1/address/country/India/states/Goan",
                        "nonadminaddress@e.com",
                        USER_PASSWORD
                );

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testGetCities() {
        ResponseEntity<Set<CityDTO>> responseEntity =
                this.testRestUtil.get(
                        new ParameterizedTypeReference<>() { },
                        "/v1/address/country/India/states/Goa/cities",
                        "nonadminaddress@e.com",
                        USER_PASSWORD
                );

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Set<CityDTO> cityDTOs = responseEntity.getBody();
        Assert.assertNotNull("Response object is null", cityDTOs);
        Assert.assertFalse("City list is empty", cityDTOs.isEmpty());
    }

    @Test
    public void testGetCity() {
        ResponseEntity<CityDTO> responseEntity =
                this.testRestUtil.get(
                        CityDTO.class,
                        "/v1/address/country/India/states/Assam/cities/CACHAR",
                        "nonadminaddress@e.com",
                        USER_PASSWORD
                );

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        CityDTO cityDTO = responseEntity.getBody();
        Assert.assertNotNull("Response object is null", cityDTO);
        Assert.assertNotNull("City name cannot be null", cityDTO.getName());
    }

    @Test
    public void testGetCityWithInvalidCiyId() {
        ResponseEntity<CityDTO> responseEntity =
                this.testRestUtil.get(
                        CityDTO.class,
                        "/v1/address/country/India/states/Assam/cities/CACHARwqewq",
                        "nonadminaddress@e.com",
                        USER_PASSWORD
                );

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testGetDataBasedOnZipCode() {
        ResponseEntity<ZipcodeDTO> responseEntity =
                this.testRestUtil.get(
                        ZipcodeDTO.class,
                        "/v1/address/country/India/zipcode/400708",
                        "nonadminaddress@e.com",
                        USER_PASSWORD
                );

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ZipcodeDTO zipcodeDTO = responseEntity.getBody();
        Assert.assertNotNull("Response object is null", zipcodeDTO);
        Assert.assertNotNull("Valid zipcode return null value", zipcodeDTO.getCityId());
    }

    @Test
    public void testGetDataBasedOnZipCodeWithInvalidZipcode() {
        ResponseEntity<ZipcodeDTO> responseEntity =
                this.testRestUtil.get(
                        ZipcodeDTO.class,
                        "/v1/address/country/India/zipcode/400708354234",
                        "nonadminaddress@e.com",
                        USER_PASSWORD
                );

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}
