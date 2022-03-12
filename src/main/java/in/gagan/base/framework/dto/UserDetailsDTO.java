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

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.util.UserHelperUtil;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This DTO captures the user details from the user entity object to be used to authentication by the spring security.
 * 
 * @author gaganthind
 *
 */
public class UserDetailsDTO {

	private String password;
	
	private boolean passwordExpired;

	private String email;

	private boolean accountLocked;
	
	private boolean activeSw;
	
	private Set<UserRoleDTO> userRole = new HashSet<>();
	
	public UserDetailsDTO() { }
	
	public UserDetailsDTO(User user) {
		this.password = user.getPassword();
		this.passwordExpired = checkIfPasswordExpired(user);
		this.email = user.getEmail();
		this.accountLocked = user.isAccountLocked() == ApplicationConstants.CHAR_Y;
		this.activeSw = user.isActive() == ApplicationConstants.CHAR_Y;
		this.userRole = UserHelperUtil.convertRoleToDTO(user.getUserRole());
	}

	public String getPassword() {
		return password;
	}

	public boolean isPasswordExpired() {
		return passwordExpired;
	}

	public String getEmail() {
		return email;
	}

	public boolean isAccountLocked() {
		return accountLocked;
	}

	public boolean isActive() {
		return activeSw;
	}

	public Set<UserRoleDTO> getUserRole() {
		return userRole;
	}
	
	private boolean checkIfPasswordExpired(User user) {
		return LocalDateTime.now().isAfter(user.getPasswordExpireDate());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDetailsDTO that = (UserDetailsDTO) o;
		return email.equals(that.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}
}
