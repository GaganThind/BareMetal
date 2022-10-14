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

import in.gagan.base.framework.component.VerificationTokenProps;
import in.gagan.base.framework.dao.user.ForgotPasswordTokenDAO;
import in.gagan.base.framework.entity.user.ForgotPasswordToken;
import in.gagan.base.framework.entity.user.User;
import in.gagan.base.framework.exception.InvalidPasswordTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

/**
 * This class provides the implementation of ForgotPasswordTokenService interface and provides operations for forgot password functionality.
 * 
 * @author gaganthind
 *
 */
@Transactional
@Service
public class ForgotPasswordTokenServiceImpl implements ForgotPasswordTokenService {

	private final VerificationTokenProps verificationTokenProps;
	private final ForgotPasswordTokenDAO forgotPasswordTokenDAO;
	
	@Autowired
	public ForgotPasswordTokenServiceImpl(ForgotPasswordTokenDAO forgotPasswordTokenDAO, VerificationTokenProps verificationTokenProps) {
		this.forgotPasswordTokenDAO = forgotPasswordTokenDAO;
		this.verificationTokenProps = verificationTokenProps;
	}

	/**
	 * This method generates random token for account verification.
	 * 
	 * @param user - User entity object
	 * @return String - Random Password token
	 */
	@Override
	public Optional<String> generateForgotPasswordToken(User user) {
		String token = UUID.randomUUID().toString();
		ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken(token);
		forgotPasswordToken.setUser(user);
		forgotPasswordToken.setExpiryDate(this.verificationTokenProps.getExpiryTimeFromNow());
		this.forgotPasswordTokenDAO.save(forgotPasswordToken);
		
		return Optional.ofNullable(forgotPasswordToken.getToken());
	}

	/**
	 * This method is used to fetch the provided token in the system.
	 * 
	 * @param token - Random verification token already sent in email
	 * @return ForgotPasswordToken - ForgotPasswordToken record from database
	 */
	@Override
	public Optional<ForgotPasswordToken> fetchByToken(String token) {
		ForgotPasswordToken forgotPasswordToken =
				this.forgotPasswordTokenDAO.fetchByToken(token)
						.orElseThrow(InvalidPasswordTokenException::new);
		
		if (forgotPasswordToken.isExpiredToken()) {
			String newToken = UUID.randomUUID().toString();
			forgotPasswordToken.setToken(newToken);
			this.forgotPasswordTokenDAO.save(forgotPasswordToken);
		}
		
		return Optional.of(forgotPasswordToken);
	}

	/**
	 * This method is used to delete the token once successfully verified.
	 * 
	 * @param forgotPasswordToken - ForgotPasswordToken record from database
	 */
	@Override
	public void deleteToken(ForgotPasswordToken forgotPasswordToken) {
		this.forgotPasswordTokenDAO.hardDelete(forgotPasswordToken);
	}

	/**
	 * This method is used to fetch the token details based on provided email.
	 *
	 * @param email - email address for searching the token
	 * @return ForgotPasswordToken - ForgotPasswordToken record from database
	 */
	@Override
	public Optional<ForgotPasswordToken> fetchByEmail(String email) {
		return this.forgotPasswordTokenDAO.fetchByEmail(email);
	}

}
