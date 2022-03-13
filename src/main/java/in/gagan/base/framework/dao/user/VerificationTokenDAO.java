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

package in.gagan.base.framework.dao.user;

import java.util.Optional;

import in.gagan.base.framework.dao.base.BaseDAO;
import in.gagan.base.framework.entity.user.VerificationToken;

/**
 * This DAO interface provides the CRUD operations for VERIFICATION_TOKEN table.
 * 
 * @author gaganthind
 *
 */
public interface VerificationTokenDAO extends BaseDAO<VerificationToken, Long> {
	
	/**
	 * Method used to fetch a token record.
	 * 
	 * @param token - token to fetch record
	 * @return Optional<VerificationToken> - VerificationToken record
	 */
	Optional<VerificationToken> fetchByToken(String token);
	
}
