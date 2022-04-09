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

package in.gagan.base.framework.service.user;

import in.gagan.base.framework.entity.user.ForgotPasswordToken;
import in.gagan.base.framework.entity.user.User;

import java.util.Optional;

/**
 * This Service interface defines the basic CRUD operations on a FORGOT_PASSWORD_TOKEN table.
 * 
 * @author gaganthind
 *
 */
public interface ForgotPasswordTokenService {
	
	/**
	 * This method generates random token for account verification.
	 * 
	 * @param user - User entity object
	 * @return String - Random password token
	 */
	Optional<String> generateForgotPasswordToken(User user);
	
	/**
	 * This method is used to fetch the provided token in the system.
	 * 
	 * @param token - Random verification token already sent in email
	 * @return ForgotPasswordToken - ForgotPasswordToken record from database
	 */
	Optional<ForgotPasswordToken> fetchByToken(String token);
	
	/**
	 * This method is used to delete the token once successfully verified.
	 * 
	 * @param forgotPasswordToken - ForgotPasswordToken record from database
	 */
	void deleteToken(ForgotPasswordToken forgotPasswordToken);

}
