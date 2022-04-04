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

package in.gagan.base.framework.util;

import in.gagan.base.framework.dto.user.UsernamePasswordAuthDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TestRestUtil {

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Method used to perform get operation.
     *
     * @param output Expected class type from the output object
     * @param url API endpoint url
     * @param username Authentication user
     * @param password Authentication user's password
     * @param <T> Object type to be fetched
     * @return Returning object
     */
    public <T> ResponseEntity<T> get(Class<T> output, String url, String username, String password) {
        String bearerToken = getBearerToken(username, password);
        HttpHeaders headers = createHttpHeader(MediaType.TEXT_PLAIN, bearerToken);
        HttpEntity<T> httpEntity = new HttpEntity<>(headers);
        return this.restTemplate.exchange(url, HttpMethod.GET, httpEntity, output);
    }

    /**
     * Method used to perform get operation.
     *
     * @param <T> Object type to be fetched
     * @param output Expected class type from the output object
     * @param url API endpoint url
     * @param username Authentication user
     * @param password Authentication user's password
     * @return Returning object
     */
    public <T> ResponseEntity<T> get(ParameterizedTypeReference<T> output, String url, String username, String password) {
        String bearerToken = getBearerToken(username, password);
        HttpHeaders headers = createHttpHeader(MediaType.TEXT_PLAIN, bearerToken);
        HttpEntity<T> httpEntity = new HttpEntity<>(headers);
        return this.restTemplate.exchange(url, HttpMethod.GET, httpEntity, output);
    }

    /**
     * Method used to perform delete operation.
     *
     * @param url API endpoint url
     * @param username Authentication user
     * @param password Authentication user's password
     * @return Returning string message
     */
    public ResponseEntity<String> delete(String url, String username, String password) {
        String bearerToken = getBearerToken(username, password);
        HttpHeaders headers = createHttpHeader(MediaType.TEXT_PLAIN, bearerToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return this.restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, String.class);
    }

    /**
     * Method used to perform patch operation.
     *
     * @param input Input object to send to server
     * @param output Expected class type from the output object
     * @param url API endpoint url
     * @param username Authentication user
     * @param password Authentication user's password
     * @param <T> Input object type
     * @param <R> Result object type
     * @return result object
     */
    public <T, R> ResponseEntity<R> patch(T input, Class<R> output, String url, String username, String password) {
        String bearerToken = getBearerToken(username, password);
        HttpHeaders headers = createHttpHeader(MediaType.APPLICATION_JSON, bearerToken);
        HttpEntity<T> httpEntity = new HttpEntity<>(input, headers);
        return this.restTemplate.exchange(url, HttpMethod.PATCH, httpEntity, output);
    }

    /**
     * Method used to perform post operation.
     *
     * @param input Input object to send to server
     * @param output Expected class type from the output object
     * @param url API endpoint url
     * @param username Authentication user
     * @param password Authentication user's password
     * @param <T> Input object type
     * @param <R> Result object type
     * @return result object
     */
    public <T, R> ResponseEntity<R> post(T input, Class<R> output, String url, String username, String password) {
        String bearerToken = getBearerToken(username, password);
        HttpHeaders requestHeaders = createHttpHeader(MediaType.APPLICATION_JSON, bearerToken);
        HttpEntity<T> httpEntity = new HttpEntity<>(input, requestHeaders);
        return this.restTemplate.postForEntity(url, httpEntity, output);
    }

    /**
     * Method used to perform put operation.
     *
     * @param input Input object to send to server
     * @param output Expected class type from the output object
     * @param url API endpoint url
     * @param username Authentication user
     * @param password Authentication user's password
     * @param <T> Input object type
     * @param <R> Result object type
     * @return result object
     */
    public <T, R> ResponseEntity<R> put(T input, Class<R> output, String url, String username, String password) {
        String bearerToken = getBearerToken(username, password);
        HttpHeaders headers = createHttpHeader(MediaType.APPLICATION_JSON, bearerToken);
        HttpEntity<T> httpEntity = new HttpEntity<>(input, headers);
        return this.restTemplate.exchange(url, HttpMethod.PUT, httpEntity, output);
    }

    /**
     * Method to create a bearer token for user authentication with provided username and password.
     *
     * @param username - email id of user
     * @param password - password of user
     * @return String - Bearer token
     */
    public String getBearerToken(String username, String password) {
        HttpHeaders requestHeaders = createHttpHeader(MediaType.APPLICATION_JSON);
        UsernamePasswordAuthDTO usernamePasswordAuthDTO = new UsernamePasswordAuthDTO(username, password);
        HttpEntity<UsernamePasswordAuthDTO> httpEntity = new HttpEntity<>(usernamePasswordAuthDTO, requestHeaders);
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("/login", httpEntity, String.class);
        String authorizationHeader = responseEntity.getHeaders().getFirst("Authorization");
        return Objects.requireNonNull(authorizationHeader).replace("Bearer ", "");
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

    /**
     * Method to create a header with contentType as parameter type.
     *
     * @param mediaType - Media type for header creation
     * @return HttpHeaders  -HttpHeaders object
     */
    public static HttpHeaders createHttpHeader(MediaType mediaType) {
        return createHttpHeader(mediaType, null);
    }
}
