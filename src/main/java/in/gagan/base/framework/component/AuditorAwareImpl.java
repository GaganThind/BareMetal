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

package in.gagan.base.framework.component;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.AuditorAware;

import in.gagan.base.framework.util.UserHelperUtil;

/**
 * This class is used to provide the principal/logged-in username for database operation.
 * 
 * @author gaganthind
 *
 */
public class AuditorAwareImpl implements AuditorAware<String> {

	/**
	 * This method is  used to get the name of the current logged-in user.
	 * 
	 * @return Optional<String> - Logged-in username
	 */
	@Override
	public Optional<String> getCurrentAuditor() {
		String principal = UserHelperUtil.loggedInUsername();
		String username = StringUtils.defaultIfBlank(principal, "System");
		return Optional.of(username);
	}

}
