package in.gagan.base.framework.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

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
		
		String exDetails = StringUtils.defaultIfEmpty(ex.toString(), "No Message");
		this.exceptionDetails = StringUtils.left(exDetails, 500);
		
		String exMessage = StringUtils.defaultIfEmpty(ex.getMessage(), "No Message");
		this.exceptionMessage = StringUtils.left(exMessage, 500);
		
		String stacktrace = Arrays.toString(ex.getStackTrace());
		this.stackTrace = StringUtils.left(stacktrace, 500);
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
