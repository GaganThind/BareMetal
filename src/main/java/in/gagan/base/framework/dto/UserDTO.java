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

package in.gagan.base.framework.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.enums.Gender;
import in.gagan.base.framework.enums.UserRoles;
import in.gagan.base.framework.util.UserHelperUtil;
import in.gagan.base.framework.validator.EmailValidator;
import in.gagan.base.framework.validator.Password;
import in.gagan.base.framework.validator.PasswordMatches;

/**
 * This DTO captures the user details from user entity and is used for internal data transfer.
 * 
 * @author gaganthind
 *
 */
@PasswordMatches
public class UserDTO {

	@NotNull(message = "{message.registration.first.name}")
	@Size(min = 1, max = 25, message = "{message.registration.first.name.length}")
	private String firstName;

	@NotNull(message = "{message.registration.last.name}")
	@Size(min = 1, max = 25, message = "{message.registration.last.name.length}")
	private String lastName;
	
	@NotNull(message = "{message.registration.email}")
	@Size(min = 4, message = "{message.registration.email}")
	@EmailValidator
	private String email;
	
	@Password
	@NotNull(message = "{message.registration.password}")
	@Size(min = 8, message = "{message.registration.password}")
	private String password;
	
	@NotNull(message = "{message.registration.password}")
	@Size(min = 1, message = "{message.registration.password}")
	private String matchingPassword;

	@Past(message = "{message.registration.dob.invalid}")
	private LocalDate dob;
	
	private int age;

	private Gender gender;
	
	private long phoneNumber;
	
	private String addressLine1;
	
	private String addressLine2;
	
	private String country;
	
	private String state;
	
	private String city;
	
	private long zipcode;
	
	private boolean activeSw;
	
	// Even if no roles are added by the user, a default role will be added.
	private Set<UserRoleDTO> userRole = new HashSet<>();
	
	public UserDTO() { super(); }
	
	/*
	 * Bare minimum user details. This constructor contains required fields.
	 */
	public UserDTO(String firstName, String lastName, String email, String password, Set<UserRoleDTO> userRole) {
		this(firstName, lastName, email, password, null, Gender.NULL, userRole, 0, null, null, null, 0, null, null);
	}
	
	public UserDTO(String firstName, String lastName, String email, String password, LocalDate dob, Gender gender,
			Set<UserRoleDTO> userRole, long phoneNumber, String country, String state, String city, long zipcode,
			String addressLine1, String addressLine2) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = this.matchingPassword = password;
		this.dob = dob;
		this.gender = gender;
		this.age = UserHelperUtil.calculateAge(dob);
		this.phoneNumber = phoneNumber;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.country = country;
		this.state = state;
		this.city = city;
		this.zipcode = zipcode;
		
		// Assign default role if not present
		if (null == userRole) {
			Set<UserRoleDTO> roles = new HashSet<>();
			roles.add(new UserRoleDTO(UserRoles.USER_BASIC));
			userRole = roles;
		}
		this.userRole = userRole;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getZipcode() {
		return zipcode;
	}

	public void setZipcode(long zipcode) {
		this.zipcode = zipcode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonIgnore
	public String getUsername() {
		return new StringBuilder(firstName).append(ApplicationConstants.BLANK).append(lastName).toString();
	}

	public String getEmail() {
		return email.toLowerCase();
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isActiveSw() {
		return activeSw;
	}

	public void setActiveSw(boolean activeSw) {
		this.activeSw = activeSw;
	}

	public Set<UserRoleDTO> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRoleDTO> userRole) {
		this.userRole = userRole;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDTO userDTO = (UserDTO) o;
		return email.equals(userDTO.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}
}
