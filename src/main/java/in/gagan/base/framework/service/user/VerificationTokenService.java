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

import in.gagan.base.framework.entity.user.User;
import in.gagan.base.framework.entity.user.VerificationToken;
import in.gagan.base.framework.exception.InvalidTokenException;

/**
 * This Service interface defines the basic CRUD operations on a VERIFICATION_TOKEN table.
 * 
 * @author gaganthind
 *
 */
public interface VerificationTokenService {
	
	/**
	 * This method generates random token for account verification.
	 * 
	 * @param user - User record to insert
	 * @return String - Random verification token
	 */
	String generateTokenForUser(User user);
	
	/**
	 * This method is used to fetch the provided token in the system.
	 * 
	 * @param token - Random verification token already sent in email
	 * @throws InvalidTokenException
	 * @return VerificationToken - Verification_Token record from database
	 */
	VerificationToken fetchByToken(String token) throws InvalidTokenException;
	
	/**
	 * This method is used to delete the token once successfully verified.
	 * 
	 * @param verificationToken - Verification_Token record from database
	 */
	void deleteToken(VerificationToken verificationToken);

}
