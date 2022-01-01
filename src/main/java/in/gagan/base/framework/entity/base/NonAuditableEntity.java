package in.gagan.base.framework.entity.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * This class enables auditing of persistence data.
 * 
 * @author gaganthind
 *
 */
@MappedSuperclass
public abstract class NonAuditableEntity implements BaseEntity {
	
	public NonAuditableEntity() {  }
	
	public NonAuditableEntity(char activeSw) {
		super();
		this.activeSw = activeSw;
	}
	
	@Column(name="ACTIVE_SW", nullable = false, length = 1)
	private char activeSw;
	
	@Override
	public char isActive() {
		return activeSw;
	}

	@Override
	public void setActiveSw(char activeSw) {
		this.activeSw = activeSw;
	}

}
