package in.gagan.base.framework.exception;

import java.time.LocalDateTime;

/**
 * This class captures the exception details and rethrows them in default format. 
 * 
 * @author gaganthind
 *
 */
public class ExceptionDetail {

	// Create date
	private final LocalDateTime createDt;

	// Exception details
	private final String exceptionDetails;

	// Exception message
	private final String exceptionMessage;

	/**
	 * Param constructor
	 * 
	 * @param ex
	 */
	public ExceptionDetail(Exception ex) {
		this.createDt = LocalDateTime.now();
		this.exceptionDetails = ex.toString();
		this.exceptionMessage = null != ex.getMessage() ? ex.getMessage() : "NULL";
	}

	public LocalDateTime getCreateDt() {
		return createDt;
	}

	public String getExceptionDetails() {
		return exceptionDetails;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

}
