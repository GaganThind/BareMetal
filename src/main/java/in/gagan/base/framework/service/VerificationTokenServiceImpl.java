package in.gagan.base.framework.service;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.component.VerificationTokenProps;
import in.gagan.base.framework.dao.AbstractBaseDAO;
import in.gagan.base.framework.dao.VerificationTokenDAO;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.entity.VerificationToken;

@Transactional
@Service
public class VerificationTokenServiceImpl extends AbstractBaseDAO<VerificationToken, Long> implements VerificationTokenService {
	
	private final VerificationTokenDAO verificationTokenDAO;
	
	private final VerificationTokenProps verificationTokenProps;
	
	@Autowired
	public VerificationTokenServiceImpl(VerificationTokenDAO verificationTokenDAO, VerificationTokenProps verificationTokenProps) {
		this.verificationTokenDAO = verificationTokenDAO;
		this.verificationTokenProps = verificationTokenProps;
	}
	
	public String generateTokenForUser(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken(token);
		verificationToken.setUser(user);
		verificationToken.setExpiryDate(LocalDateTime.now().plusDays(this.verificationTokenProps.getVerificationTokenExpiryDays()));
		this.verificationTokenDAO.save(verificationToken);
		
		// Testign only
		System.err.println("User: " + user.getEmail() + " with token as: " + token);
		
		return token;
	}
	
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
	
}
