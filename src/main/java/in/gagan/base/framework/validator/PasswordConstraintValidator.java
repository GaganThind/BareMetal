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
