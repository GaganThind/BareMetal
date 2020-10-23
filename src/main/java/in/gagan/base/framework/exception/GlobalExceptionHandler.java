package in.gagan.base.framework.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import in.gagan.base.framework.dto.ExceptionDetailDTO;
import in.gagan.base.framework.dto.ExceptionMonitorDTO;
import in.gagan.base.framework.service.ExceptionMonitoringService;

/**
 * This class is a used to provide support for global exception handling.
 * 
 * @author gaganthind
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	private final ExceptionMonitoringService exceptionMonitoringSvc;
	
	@Autowired
	public GlobalExceptionHandler(ExceptionMonitoringService exceptionMonitoringSvc) {
		this.exceptionMonitoringSvc = exceptionMonitoringSvc;
	}

	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	/**
	 * Convert the thrown exception into custom format using the Exception details class and log it
	 * 
	 * @param ex
	 * @return
	 */
	private final ExceptionDetailDTO handleException(final Exception ex, boolean enableLogging) {
		ExceptionDetailDTO exceptionDetailDTO = new ExceptionDetailDTO(ex);
		
		if (enableLogging) {
			ExceptionMonitorDTO exceptionMonitorDTO = new ExceptionMonitorDTO(ex);
			this.exceptionMonitoringSvc.insertException(exceptionMonitorDTO);
			logger.error("Exception with cause = {}", exceptionMonitorDTO.toString());
		}
		
		return exceptionDetailDTO;
	}
	
	/**
	 * Convert the thrown exception into custom format using the Exception details class and log it
	 * 
	 * @param ex
	 * @return
	 */
	private final ExceptionDetailDTO handleException(final Exception ex) {
		return handleException(ex, true);
	}
	
	/**
	 * Global exception handler for UsernameExistException
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(UsernameExistException.class)
	public final ResponseEntity<?> usernameExistExceptionHandler(final Exception ex, WebRequest request) {
		return new ResponseEntity<>(handleException(ex, true), HttpStatus.FOUND);
	}
	
	/**
	 * Global exception handler for UserNameNotFoundException
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(UsernameNotFoundException.class)
	public final ResponseEntity<?> usernameNotFoundException(final Exception ex, WebRequest request) {
		return new ResponseEntity<>(handleException(ex, false), HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Global exception handler for Exception
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<?> globalExceptionHandler(final Exception ex, WebRequest request) {
		return new ResponseEntity<>(handleException(ex), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
