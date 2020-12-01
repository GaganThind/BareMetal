package in.gagan.base.framework.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This DTO captures the exception details and re-throws them in default format. 
 * 
 * @author gaganthind
 *
 */
public class ExceptionDetailDTO implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 360375444649679577L;

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
	public ExceptionDetailDTO(Exception ex) {
		this.createDt = LocalDateTime.now();
		this.exceptionDetails = ex.toString();
		this.exceptionMessage = null != ex.getMessage() ? ex.getMessage() : "No Message";
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
	
	@Override
	public String toString() {
		return "ExceptionDetail [createDt=" + createDt + ", exceptionDetails=" + exceptionDetails
				+ ", exceptionMessage=" + exceptionMessage + "]";
	}
	
}
