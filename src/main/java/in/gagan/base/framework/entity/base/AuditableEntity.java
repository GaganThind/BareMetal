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

package in.gagan.base.framework.entity.base;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * This class enables auditing of persistence data.
 * 
 * @author gaganthind
 *
 */
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public abstract class AuditableEntity implements BaseEntity {
	
	public AuditableEntity(char activeSw) {
		super();
		this.activeSw = activeSw;
	}

	@Column(name="ACTIVE_SW", nullable = false, length = 1)
	private char activeSw;
	
	/**
	 * Record create date
	 */
	@CreatedDate
	@Column(name="CREATE_DT", updatable = false)
	private LocalDateTime createDt;
	
	/**
	 * Record update date
	 */
	@LastModifiedDate
	@Column(name="UPDATE_DT")
	private LocalDateTime updateDt;
	
	/**
	 * Record create user id
	 */
	@CreatedBy
	@Column(name="CREATE_USER_ID", updatable = false)
	private String createUserId;
	
	/**
	 * Record update user id
	 */
	@LastModifiedBy
	@Column(name="UPDATE_USER_ID")
	private String updateUserId;
	
	@Override
	public char isActive() {
		return activeSw;
	}

	@Override
	public void setActiveSw(char activeSw) {
		this.activeSw = activeSw;
	}

	public LocalDateTime getCreateDt() {
		return createDt;
	}

	public LocalDateTime getUpdateDt() {
		return updateDt;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

}
