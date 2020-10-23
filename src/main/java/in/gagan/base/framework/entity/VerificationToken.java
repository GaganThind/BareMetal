package in.gagan.base.framework.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entity representing the VerificationToken table 
 * 
 * @author gaganthind
 *
 */
@Entity
@Table(name="VERIFICATION_TOKEN")
public class VerificationToken extends AbstractBaseEntity {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -8653909583404953788L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TOKEN_ID", nullable = false, unique = true, length = 10)
	private long tokenId;
	
	@Column(name="TOKEN", nullable = false, unique = true)
	private String token;
	
	@OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name="USER_ID", nullable = false)
	private User user;
	
	@Column(name="EXPIRY_DATE", nullable = false)
	private LocalDateTime expiryDate;

	public VerificationToken() { super(); }
	
	public VerificationToken(String token) { 
		super();
		this.token = token;
	}

	public long getTokenId() {
		return tokenId;
	}

	public void setTokenId(long tokenId) {
		this.tokenId = tokenId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public boolean isExpiredToken() {
		return LocalDateTime.now().isAfter(this.expiryDate);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VerificationToken other = (VerificationToken) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}
	
}
