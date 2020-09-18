package in.gagan.base.framework.exception;

/**
 * User not found exception.
 * 
 * @author gaganthind
 *
 */
public final class UsernameExistException extends Exception {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -7880518881602624383L;
	
	public UsernameExistException(String id) {
		super(new StringBuilder("User with Username/Email_id: ").append(id).append(" already exists").toString());
	}
	
}
