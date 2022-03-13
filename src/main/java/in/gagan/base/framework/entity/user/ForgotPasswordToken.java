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

package in.gagan.base.framework.entity.user;

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

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.entity.base.AuditableEntity;
import in.gagan.base.framework.entity.base.BaseEntity;

/**
 * Entity representing the FORGOT_PASSWORD_TOKEN table 
 * 
 * @author gaganthind
 *
 */
@Entity
@Table(name="FORGOT_PASSWORD_TOKEN")
public class ForgotPasswordToken extends AuditableEntity implements BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TOKEN_ID", nullable = false, unique = true, length = 10)
	private long tokenId;
	
	@Column(name="TOKEN", nullable = false, unique = true)
	private String token;
	
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="USER_ID", nullable = false)
	private User user;
	
	@Column(name="EXPIRY_DATE", nullable = false)
	private LocalDateTime expiryDate;
	
	public ForgotPasswordToken() {
		super(ApplicationConstants.CHAR_Y);
	}
	
	public ForgotPasswordToken(String token) { 
		super(ApplicationConstants.CHAR_Y);
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
		ForgotPasswordToken other = (ForgotPasswordToken) obj;
		if (token == null) {
			return other.token == null;
		} else return token.equals(other.token);
	}

}
