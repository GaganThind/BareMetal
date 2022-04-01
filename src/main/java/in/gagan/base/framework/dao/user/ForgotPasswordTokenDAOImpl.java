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

package in.gagan.base.framework.dao.user;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import in.gagan.base.framework.dao.base.AbstractBaseDAO;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import in.gagan.base.framework.entity.user.ForgotPasswordToken;

/**
 * This class provides CRUD operations on the FORGOT_PASSWORD_TOKEN table using DAO pattern.
 * 
 * @author gaganthind
 *
 */
@Repository
public class ForgotPasswordTokenDAOImpl extends AbstractBaseDAO<ForgotPasswordToken, Long> implements ForgotPasswordTokenDAO {
	
	private final static String WHERE_TOKEN_CLAUSE = " where token = :token ";
	private final static String TOKEN = "token";

	/**
	 * Method used to fetch a token record.
	 * 
	 * @param token - token to fetch record
	 * @return Optional<ForgotPasswordToken> - ForgotPasswordToken record
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Optional<ForgotPasswordToken> fetchByToken(String token) {
		String selectQuery = new StringBuilder(LITERAL_FROM).append(getTableName()).append(WHERE_TOKEN_CLAUSE).toString();
		Query query = entityManager.createQuery(selectQuery);
		query.setParameter(TOKEN, token);
		List<ForgotPasswordToken> forgotPasswordTokens = (List<ForgotPasswordToken>) query.getResultList();
		
		return !CollectionUtils.isEmpty(forgotPasswordTokens) ? Optional.of(forgotPasswordTokens.get(0)) : Optional.empty();
	}

}
