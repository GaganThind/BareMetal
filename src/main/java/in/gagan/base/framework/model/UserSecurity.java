package in.gagan.base.framework.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Model/Entity representing the UserSecurity table 
 * 
 * @author gaganthind
 *
 */
@Entity
@Table(name = "USER_SECURITY")
@NamedQuery(name = "HQL_GET_ALL_USER_SECURITY", query = "select us from UserSecurity us join fetch us.user u")
public class UserSecurity extends AbstractBaseModel {
	
	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1563028499730085442L;

	@Id
	private long securityId;
	
	@Column(name = "PASSWORD", nullable = false, length = 100)
	private String password;

	@Column(name = "FAILED_LOGIN_ATTEMPTS", nullable = false)
	private short failedLoginAttempts;

	@Column(name = "ACCOUNT_LOCKED", nullable = false)
	private char accountLocked;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name="SECURITY_ID", nullable = false)
	@Fetch(FetchMode.SELECT)
	private Set<Role> userRole = new HashSet<>();
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@MapsId
	@JoinColumn(name = "USER_ID")
	private User user;
	
	public long getUserSecurityId() {
		return securityId;
	}

	public void setUserSecurityId(long securityId) {
		this.securityId = securityId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public short getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void setFailedLoginAttempts(short failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	public char isAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(char accountLocked) {
		this.accountLocked = accountLocked;
	}

	public Set<Role> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<Role> userRole) {
		this.userRole = userRole;
	}
	
	public void addRole(Role role) {
		role.setUserSecurity(this);
		this.userRole.add(role);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (securityId ^ (securityId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		} else if(obj == null) {
			return false;
		} else if(getClass() != obj.getClass()) {
			return false;
		}
		
		UserSecurity other = (UserSecurity) obj;
		
		return securityId == other.securityId;
	}

}
