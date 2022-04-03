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

package in.gagan.base.framework;

import in.gagan.base.framework.dto.user.UserDTO;
import in.gagan.base.framework.dto.user.UserRoleDTO;
import in.gagan.base.framework.enums.Gender;
import in.gagan.base.framework.enums.UserRoles;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class TestHelperUtil {

    public static final String USER_PASSWORD = "TestUser@123";

    private TestHelperUtil() { }

    /**
     * Method used to create a new userDTO object to be sent to the controller class.
     *
     * @return UserDTO - UserDTO object from method
     */
    public static UserDTO createDummyUserDTOWithAllDetails(String email) {
        Set<UserRoleDTO> userRoles = new HashSet<>(List.of(new UserRoleDTO(UserRoles.USER_BASIC)));

        return new UserDTO("Test", "User", email, USER_PASSWORD,
                LocalDate.of(1982, Month.APRIL, 1), Gender.M,
                userRoles, 0, "ABC", "DEF", "GHI",
                456478, "123", "345");
    }

    /**
     * Method used to create a new userDTO object to be sent to the controller class.
     *
     * @return UserDTO - UserDTO object from method
     */
    public static UserDTO createDummyUserDTO(String email) {
        return new UserDTO("Test", "User", email, USER_PASSWORD, null);
    }

    /**
     * Method used to create a new UserDTO object with email and dob.
     *
     * @return UserDTO - User details object
     */
    public static UserDTO createDummyUserDTO(String email, LocalDate dob) {
        UserDTO inputUserDTO = createDummyUserDTO(email);
        inputUserDTO.setDob(dob);
        return inputUserDTO;
    }

    /**
     * Method to create a header with contentType as parameter type.
     *
     * @param mediaType - Media type for header creation
     * @return HttpHeaders  -HttpHeaders object
     */
    public static HttpHeaders createHttpHeader(MediaType mediaType) {
        return createHttpHeader(mediaType, null);
    }

    /**
     * Method to create a header with contentType as parameter type.
     *
     * @param mediaType - Media type for header creation
     * @param bearerToken - bearer token
     * @return HttpHeaders  -HttpHeaders object
     */
    public static HttpHeaders createHttpHeader(MediaType mediaType, String bearerToken) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(mediaType);
        if (StringUtils.isNotBlank(bearerToken)) {
            requestHeaders.setBearerAuth(bearerToken);
        }
        return requestHeaders;
    }

}
