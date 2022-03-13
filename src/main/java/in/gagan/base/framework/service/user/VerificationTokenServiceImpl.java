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
import in.gagan.base.framework.dao.user.VerificationTokenDAO;
import in.gagan.base.framework.entity.user.User;
import in.gagan.base.framework.entity.user.VerificationToken;

/**
 * This class provides the implementation of VerificationTokenService interface and provides operations for Account Verification functionality.
 * 
 * @author gaganthind
 *
 */
@Transactional
@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
	
	private final VerificationTokenDAO verificationTokenDAO;
	
	private final VerificationTokenProps verificationTokenProps;
	
	@Autowired
	public VerificationTokenServiceImpl(VerificationTokenDAO verificationTokenDAO, VerificationTokenProps verificationTokenProps) {
		this.verificationTokenDAO = verificationTokenDAO;
		this.verificationTokenProps = verificationTokenProps;
	}
	
	/**
	 * This method generates random token for account verification.
	 * 
	 * @param user - User record to insert
	 * @return String - Random verification token
	 */
	@Override
	public String generateTokenForUser(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken(token);
		verificationToken.setUser(user);
		verificationToken.setExpiryDate(this.verificationTokenProps.getExpiryTimeFromNow());
		this.verificationTokenDAO.save(verificationToken);
		
		return token;
	}
	
	/**
	 * This method is used to fetch the provided token in the system.
	 * 
	 * @param token - Random verification token already sent in email
	 * @return VerificationToken - Verification_Token record from database
	 */
	@Override
	public VerificationToken fetchByToken(String token) {
		VerificationToken verificationToken = 
				this.verificationTokenDAO.fetchByToken(token).orElseThrow(() -> new IllegalArgumentException("Invalid token!!!"));
		
		if(verificationToken.isExpiredToken()) {
			String newToken = UUID.randomUUID().toString();
			verificationToken.setToken(newToken);
			this.verificationTokenDAO.save(verificationToken);
		}
		
		return verificationToken;
	}
	
	/**
	 * This method is used to delete the token once successfully verified.
	 * 
	 * @param verificationToken - Verification_Token record from database
	 */
	@Override
	public void deleteToken(VerificationToken verificationToken) {
		this.verificationTokenDAO.hardDelete(verificationToken);
	}
	
}
