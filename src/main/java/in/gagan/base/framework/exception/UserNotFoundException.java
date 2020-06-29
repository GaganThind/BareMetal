package in.gagan.base.framework.exception;

/**
 * User not found exception.
 * 
 * @author gaganthind
 *
 */
public final class UserNotFoundException extends Exception {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -7880518881602624383L;
	
	public UserNotFoundException(String exceptionMessage) {
		super();
		this.exceptionMessage = exceptionMessage;
	}
	
	private final String exceptionMessage;

	public String getExceptionMessage() {
		return exceptionMessage;
	}

}
