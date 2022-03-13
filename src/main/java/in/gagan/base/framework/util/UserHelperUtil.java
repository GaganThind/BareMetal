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

package in.gagan.base.framework.util;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.dto.user.UpdateUserDTO;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.enums.UserRoles;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * Utility class for user entity. This class contains methods for user to DTO conversions.
 * 
 * @author gaganthind
 *
 */
public final class UserHelperUtil {
	
	private UserHelperUtil() { }
	
	/**
	 * Calculate age based on input date.
	 * 
	 * @param inputDate - date to check
	 * @return int - Age
	 */
	public static int calculateAge(LocalDate inputDate) {
		if (null == inputDate) {
			return 0;
		}
		return Period.between(inputDate, LocalDate.now()).getYears();
	}

	/**
	 * Update User entity object with UpdateUserDTO fields. 
	 * 
	 * @param updateUserDTO - User DTO for updating user details
	 * @param user - User entity object
	 */
	public static void updateUserWithUpdateUserDTO(UpdateUserDTO updateUserDTO, User user) {
		if (null != updateUserDTO.getFirstName()) {
			user.setFirstName(updateUserDTO.getFirstName());
		}
		
		if (null != updateUserDTO.getLastName()) {
			user.setLastName(updateUserDTO.getLastName());
		}
		
		if (null != updateUserDTO.getDob()) {
			user.setDob(updateUserDTO.getDob());
		}
		
		if (null != updateUserDTO.getGender()) {
			user.setGender(updateUserDTO.getGender());
		}
		
		if (null != updateUserDTO.getUserRole() && !updateUserDTO.getUserRole().isEmpty()) {
			user.setUserRole(DTOMapper.convertDTOToRole(updateUserDTO.getUserRole()));
		}
		
	}
	
	/**
	 * This method returns the logged-in user.
	 * 
	 * @return Authentication - user authentication object
	 */
	public static Authentication loggedInUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	/**
	 * This method returns the logged-in username.
	 * 
	 * @return String - username
	 */
	public static String loggedInUsername() {
		Authentication auth = loggedInUser();
		return null == auth ? ApplicationConstants.BLANK : (String) auth.getPrincipal();
	}
	
	/**
	 * Check if the logged-in user has admin role.
	 * 
	 * @return boolean - Is Logged-In user account admin
	 */
	@SuppressWarnings("unchecked")
	public static boolean isAdminAccount() {
		Authentication auth = loggedInUser();
		String adminRoleName = new StringBuilder(ApplicationConstants.PREFIX_ROLE).append(UserRoles.ADMIN.name()).toString();
		GrantedAuthority admin = new SimpleGrantedAuthority(adminRoleName);
		
		List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) auth.getAuthorities();
		return null != authorities && authorities.contains(admin);
	}
	
	/**
	 * Check if the user is allowed to perform this action. 
	 * This method checks if logged-in user and the user trying to perform the action are same. 
	 * 
	 * @param email - Username/Email id of user
	 * @throws IllegalAccessException - Thrown if email and logged-in user are different
	 */
	public static void actionAllowed(String email) throws IllegalAccessException {
		String username = loggedInUsername();
		boolean isAdminAccount = isAdminAccount();
		
		if (!isAdminAccount && !StringUtils.equalsIgnoreCase(email, username)) {
			throw new IllegalAccessException("User can only update own password.");
		}
	}

	/**
	 *
	 * Method used to get the username from passed user object.
	 *
	 * @param user - user object
	 * @return username - firstname and lastname
	 */
	public static String getUsername(User user) {
		return new StringBuilder(user.getFirstName()).append(ApplicationConstants.BLANK)
				.append(user.getLastName()).toString();
	}
	
}
