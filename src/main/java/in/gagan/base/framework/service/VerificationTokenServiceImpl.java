package in.gagan.base.framework.service;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.component.VerificationTokenProps;
import in.gagan.base.framework.dao.VerificationTokenDAO;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.entity.VerificationToken;

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
		verificationToken.setExpiryDate(LocalDateTime.now().plusDays(this.verificationTokenProps.getVerificationTokenExpiryDays()));
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
