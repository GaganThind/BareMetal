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
