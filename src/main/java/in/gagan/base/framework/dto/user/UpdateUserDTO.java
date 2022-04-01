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

package in.gagan.base.framework.dto.user;

import in.gagan.base.framework.enums.Gender;
import in.gagan.base.framework.validator.EmailValidator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * This DTO captures the user details from user entity for updating user details and is used for internal data transfer.
 * 
 * @author gaganthind
 *
 */
public class UpdateUserDTO {

	private String firstName;

	private String lastName;
	
	@NotNull(message = "{message.registration.email}")
	@Size(min = 4, message = "{message.registration.email}")
	@EmailValidator
	private String email;
	
	private LocalDate dob;
	
	private Gender gender;
	
	private boolean activeSw;
	
	private Set<UserRoleDTO> userRole = new HashSet<>();

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

}
