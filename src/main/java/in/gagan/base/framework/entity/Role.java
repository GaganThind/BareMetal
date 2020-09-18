package in.gagan.base.framework.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import in.gagan.base.framework.constant.ApplicationConstants;

/**
 * Entity representing the Role table 
 * 
 * @author gaganthind
 *
 */
@Entity
@Table(name="ROLES")
public class Role extends AbstractBaseEntity {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 994388367262603543L;
	
	public Role() { super(); }
	
	public Role(String roleName) {
		super(ApplicationConstants.CHAR_Y);
		this.roleName = roleName;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ROLE_ID", nullable = false, unique = true, length = 10)
	private long roleId;
	
	@Column(name = "ROLE_NAME", nullable = false)
	private String roleName;
	
	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (roleId ^ (roleId >>> 32));
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
		
		Role other = (Role) obj;
		
		return roleId == other.roleId;
	}

}
