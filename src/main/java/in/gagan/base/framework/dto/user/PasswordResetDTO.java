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

import in.gagan.base.framework.validator.EmailValidator;
import in.gagan.base.framework.validator.Password;
import in.gagan.base.framework.validator.PasswordMatches;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This DTO captures the password reset details.
 * 
 * @author gaganthind
 *
 */
@PasswordMatches
public class PasswordResetDTO {

	@Password
	@NotNull(message = "{message.registration.password}")
	@Size(min = 8, message = "{message.registration.password}")
	private String password;
	
	@NotNull(message = "{message.registration.password}")
	@Size(min = 1, message = "{message.registration.password}")
	private String matchingPassword;
	
	@NotNull(message = "{message.registration.email}")
	@Size(min = 4, message = "{message.registration.email}")
	@EmailValidator
	private String email;

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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
