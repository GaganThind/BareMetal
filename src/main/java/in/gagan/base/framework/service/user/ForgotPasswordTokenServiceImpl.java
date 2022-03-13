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

package in.gagan.base.framework.service.user;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.component.VerificationTokenProps;
import in.gagan.base.framework.dao.ForgotPasswordTokenDAO;
import in.gagan.base.framework.entity.ForgotPasswordToken;
import in.gagan.base.framework.entity.User;

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
	public String generateForgotPasswordToken(User user) {
		String token = UUID.randomUUID().toString();
		ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken(token);
		forgotPasswordToken.setUser(user);
		forgotPasswordToken.setExpiryDate(this.verificationTokenProps.getExpiryTimeFromNow());
		this.forgotPasswordTokenDAO.save(forgotPasswordToken);
		
		return forgotPasswordToken.getToken();
	}

	/**
	 * This method is used to fetch the provided token in the system.
	 * 
	 * @param token - Random verification token already sent in email
	 * @return ForgotPasswordToken - ForgotPasswordToken record from database
	 */
	@Override
	public ForgotPasswordToken fetchByToken(String token) {
		ForgotPasswordToken forgotPasswordToken =
				this.forgotPasswordTokenDAO.fetchByToken(token)
						.orElseThrow(() -> new IllegalArgumentException("Invalid token!!!"));
		
		if (forgotPasswordToken.isExpiredToken()) {
			String newToken = UUID.randomUUID().toString();
			forgotPasswordToken.setToken(newToken);
			this.forgotPasswordTokenDAO.save(forgotPasswordToken);
		}
		
		return forgotPasswordToken;
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

}
