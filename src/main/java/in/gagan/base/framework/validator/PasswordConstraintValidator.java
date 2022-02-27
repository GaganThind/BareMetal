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

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.UsernameRule;
import org.passay.WhitespaceRule;

/**
 * This Validator class is used to validate if password contains desired characters, special characters and digits.
 * 
 * @author gaganthind
 *
 */
public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		PasswordValidator validator = new PasswordValidator(new LengthRule(8, 30), 
															new WhitespaceRule(), 
															new CharacterRule(EnglishCharacterData.UpperCase),
															new CharacterRule(EnglishCharacterData.LowerCase, 3),
															new CharacterRule(EnglishCharacterData.Special),
															new CharacterRule(EnglishCharacterData.Digit),
															new UsernameRule(),
															new IllegalSequenceRule(EnglishSequenceData.USQwerty));

		RuleResult result = validator.validate(new PasswordData(password));

		if (result.isValid()) {
			return true;
		}

		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(validator.getMessages(result).toString()).addConstraintViolation();
		return false;
	}

}
