package in.gagan.base.framework.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import in.gagan.base.framework.constant.ApplicationConstants;

/**
 * Entity representing the EXCEPTION_MONITORING table 
 * 
 * @author gaganthind
 *
 */
@Entity
@Table(name = "EXCEPTION_MONITOR")
public class ExceptionMonitor extends Auditable implements BaseEntity {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -7857559824139595351L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="EXCEPTION_ID", nullable = false, unique = true, length = 10)
	private long exceptionId;
	
	@Column(name = "EXCEPTION_DETAILS", nullable = true, length = 500)
	private String exceptionDetails;
	
	@Column(name = "EXCEPTION_MESSAGE", nullable = true, length = 500)
	private String exceptionMessage;
	
	@Column(name = "STACK_TRACE", nullable = true, length = 500)
	private String stackTrace;
	
	public ExceptionMonitor() { super(); }

	public ExceptionMonitor(String exceptionDetails, String exceptionMessage, String stackTrace) {
		super(ApplicationConstants.CHAR_Y);
		this.exceptionDetails = exceptionDetails;
		this.exceptionMessage = exceptionMessage;
		this.stackTrace = stackTrace;
	}

	public long getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(long exceptionId) {
		this.exceptionId = exceptionId;
	}

	public String getExceptionDetails() {
		return exceptionDetails;
	}

	public void setExceptionDetails(String exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (exceptionId ^ (exceptionId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExceptionMonitor other = (ExceptionMonitor) obj;
		if (exceptionId != other.exceptionId)
			return false;
		return true;
	}
	
}
