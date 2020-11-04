package in.gagan.base.framework.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

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
        UserDTO userDTO = (UserDTO) obj;
        boolean passwordNull = StringUtils.isEmpty(userDTO.getPassword()) || StringUtils.isEmpty(userDTO.getMatchingPassword());
        boolean passwordEqual = !passwordNull ? userDTO.getPassword().equals(userDTO.getMatchingPassword()) : false;
        return passwordEqual;
    }
	
}
