package in.gagan.base.framework.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * This class is a used to provide support for global exception handling
 * 
 * @author gaganthind
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	/**
	 * Convert the thrown exception into custom format using the Exception details class
	 * 
	 * @param ex
	 * @return
	 */
	private final ExceptionDetail handleException(final Exception ex) {
		logger.error("Exception with cause = {}", null != ex.toString() ? ex.toString() : "Unknown");
		return new ExceptionDetail(ex);
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
		return new ResponseEntity<>(handleException(ex), HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Global exception handler for UserNameNotFoundException
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(UsernameNotFoundException.class)
	public final ResponseEntity<?> UsernameNotFoundException(final Exception ex, WebRequest request) {
		return new ResponseEntity<>(handleException(ex), HttpStatus.NOT_FOUND);
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
