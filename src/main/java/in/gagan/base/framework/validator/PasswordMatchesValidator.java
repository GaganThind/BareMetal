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
