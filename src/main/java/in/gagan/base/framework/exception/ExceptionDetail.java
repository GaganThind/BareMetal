package in.gagan.base.framework.exception;

import java.time.LocalDateTime;
import java.util.Arrays;

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
	
	// StackTrace
	private final String stackTrace;

	/**
	 * Param constructor
	 * 
	 * @param ex
	 */
	public ExceptionDetail(Exception ex) {
		this.createDt = LocalDateTime.now();
		this.exceptionDetails = ex.toString();
		this.exceptionMessage = null != ex.getMessage() ? ex.getMessage() : "NULL";
		this.stackTrace = Arrays.toString(ex.getStackTrace()).substring(0, 500);
	}

	public LocalDateTime getCreateDt() {
		return this.createDt;
	}

	public String getExceptionDetails() {
		return this.exceptionDetails;
	}

	public String getExceptionMessage() {
		return this.exceptionMessage;
	}
	
	public String getStackTrace() {
		return this.stackTrace;
	}

}
