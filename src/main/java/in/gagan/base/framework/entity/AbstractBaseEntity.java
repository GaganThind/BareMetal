package in.gagan.base.framework.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Abstract Base entity for all entity classes. This abstract implementation of base entity will have common columns.
 * 
 * @author gaganthind
 *
 */
@MappedSuperclass
public abstract class AbstractBaseEntity implements Serializable, BaseEntity {
	
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -1287823037715243132L;
	
	public AbstractBaseEntity() { }
	
	public AbstractBaseEntity(char activeSw) {
		super();
		this.activeSw = activeSw;
	}

	@Column(name="ACTIVE_SW", nullable = false, length = 1)
	private char activeSw;
	
	/**
	 * Record create date
	 */
	@CreationTimestamp
	@Column(name="CREATE_DT", nullable = true)
	private LocalDateTime createDt;
	
	/**
	 * Record update date
	 */
	@UpdateTimestamp
	@Column(name="UPDATE_DT", nullable = true)
	private LocalDateTime updateDt;
	
	/**
	 * Record create user id
	 */
	@Column(name="CREATE_USER_ID", nullable = true)
	private String createUserId;
	
	/**
	 * Record update user id
	 */
	@Column(name="UPDATE_USER_ID", nullable = true)
	private String updateUserId;
	
	public char isActive() {
		return activeSw;
	}

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
