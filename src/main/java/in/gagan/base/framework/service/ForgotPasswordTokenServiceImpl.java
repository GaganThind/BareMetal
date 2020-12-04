package in.gagan.base.framework.service;

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
	 * @param User - User entity object
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
		ForgotPasswordToken forgotPasswordToken = this.forgotPasswordTokenDAO.fetchByToken(token)
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
