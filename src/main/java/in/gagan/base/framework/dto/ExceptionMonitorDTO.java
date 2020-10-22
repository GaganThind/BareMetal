package in.gagan.base.framework.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * This DTO captures the exception details and log them in database. 
 * 
 * @author gaganthind
 *
 */
public class ExceptionMonitorDTO implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -2490654778345545308L;
	
	// Create date
	private final LocalDateTime createDt;

	// Exception details
	private final String exceptionDetails;

	// Exception message
	private final String exceptionMessage;
	
	// StackTrace
	private final String stackTrace;

	public ExceptionMonitorDTO(Exception ex) {
		this.createDt = LocalDateTime.now();
		this.exceptionDetails = ex.toString();
		this.exceptionMessage = null != ex.getMessage() ? ex.getMessage() : "No Message";
		this.stackTrace = Arrays.toString(ex.getStackTrace()).substring(0, 500);
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

	public String getStackTrace() {
		return stackTrace;
	}

	@Override
	public String toString() {
		return "ExceptionMonitorDTO [createDt=" + createDt + ", exceptionDetails=" + exceptionDetails
				+ ", exceptionMessage=" + exceptionMessage + ", stackTrace=" + stackTrace + "]";
	}
	
}
