package in.gagan.base.framework.exception;

/**
 * User not authorized exception.
 * 
 * @author gaganthind
 *
 */
public final class UserNotAuthorizedException extends Exception {
	
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -8542123456415367799L;

	public UserNotAuthorizedException(String exceptionMessage) {
		super();
		this.exceptionMessage = exceptionMessage;
	}

	private final String exceptionMessage;

	public String getExceptionMessage() {
		return exceptionMessage;
	}

}
