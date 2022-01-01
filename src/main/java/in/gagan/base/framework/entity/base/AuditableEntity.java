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
	
	public AuditableEntity() { }
	
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
	@Column(name="UPDATE_DT", nullable = true)
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
	@Column(name="UPDATE_USER_ID", nullable = true)
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

	public void setCreateDt(LocalDateTime createDt) {
		this.createDt = createDt;
	}

	public LocalDateTime getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(LocalDateTime updateDt) {
		this.updateDt = updateDt;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
}
