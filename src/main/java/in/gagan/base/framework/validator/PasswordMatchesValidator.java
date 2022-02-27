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

package in.gagan.base.framework.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.dto.PasswordResetDTO;
import in.gagan.base.framework.dto.UserDTO;

/**
 * This Validator class is used to validate if password and matchingPassword are equal.
 * 
 * @author gaganthind
 *
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
	
	@Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
		
		String password = ApplicationConstants.BLANK;
		String matchingPassword = ApplicationConstants.BLANK;
		
		if (obj instanceof UserDTO) {
			UserDTO userDTO = (UserDTO) obj;
			password = userDTO.getPassword();
			matchingPassword = userDTO.getMatchingPassword();
		} else if (obj instanceof PasswordResetDTO) {
			PasswordResetDTO passwordResetDTO = (PasswordResetDTO) obj;
			password = passwordResetDTO.getPassword();
			matchingPassword = passwordResetDTO.getMatchingPassword();
		} else {
			throw new IllegalArgumentException("@PasswordMatches not applicable for: " + obj.getClass());
		}
		
        boolean passwordNull = StringUtils.isEmpty(password) || StringUtils.isEmpty(matchingPassword);
        boolean passwordEqual = !passwordNull ? password.equals(matchingPassword) : false;
        return passwordEqual;
    }
	
}
