package in.gagan.base.framework.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

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
	 * @param <T> - Generic type that extends Exception
	 * @param ex - Exception thrown in application
	 * @return ExceptionDetailDTO - Exception Details object thrown to user
	 */
	private final <T extends Exception> ExceptionDetailDTO handleException(final T ex, boolean enableLogging) {
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
	 * @param ex - Exception thrown in application
	 * @return ExceptionDetailDTO - Exception Details object thrown to user
	 */
	private final ExceptionDetailDTO handleException(final Exception ex) {
		return handleException(ex, true);
	}
	
	/**
	 * Global exception handler for UsernameExistException
	 * 
	 * @param ex - Exception thrown in application
	 * @param request - Request Parameter to get details
	 * @return ResponseEntity<?> - Response Entity object
	 */
	@ExceptionHandler(UsernameExistException.class)
	public final ResponseEntity<?> usernameExistExceptionHandler(final UsernameExistException ex, WebRequest request) {
		return new ResponseEntity<>(handleException(ex), HttpStatus.FOUND);
	}
	
	/**
	 * Global exception handler for UserNameNotFoundException
	 * 
	 * @param ex - Exception thrown in application
	 * @param request - Request Parameter to get details
	 * @return ResponseEntity<?> - Response Entity object
	 */
	@ExceptionHandler(UsernameNotFoundException.class)
	public final ResponseEntity<?> usernameNotFoundException(final UsernameNotFoundException ex, WebRequest request) {
		return new ResponseEntity<>(handleException(ex, false), HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Global exception handler for MethodArgumentNotValidException
	 * 
	 * @param ex - Exception thrown in application
	 * @param request - Request Parameter to get details
	 * @return ResponseEntity<?> - Response Entity object
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<?> methodArgumentNotValidException(final MethodArgumentNotValidException ex, WebRequest request) {
		BindingResult result = ex.getBindingResult();
		List<ObjectError> objectErrors = result.getAllErrors();
		List<String> errorMessages = objectErrors.stream()
														.map(ObjectError::getDefaultMessage)
														.collect(Collectors.toUnmodifiableList());
		
		Exception exption = new IllegalArgumentException(errorMessages.toString());
		
		return new ResponseEntity<>(handleException(exption), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Global exception handler for HttpMessageNotReadableException
	 * 
	 * @param ex - Exception thrown in application
	 * @param request - Request Parameter to get details
	 * @return ResponseEntity<?> - Response Entity object
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public final ResponseEntity<?> httpMessageNotReadableException(final HttpMessageNotReadableException ex, WebRequest request) {
		
		final String INVALID_JSON = "Invalid JSON";
		Throwable throwable = ex.getCause();
		Exception excption = ex;
		
		if (throwable instanceof JsonParseException) {
			excption = new IllegalArgumentException(INVALID_JSON);
		} else if (throwable instanceof InvalidFormatException) {
			
			if (throwable.getMessage().contains("value not one of declared Enum instance names")) {
				excption = new IllegalArgumentException(INVALID_JSON + ": Incorect Enum values present");
			} else {
				excption = new IllegalArgumentException(INVALID_JSON);
			}
		}
		
		return new ResponseEntity<>(handleException(excption), HttpStatus.BAD_REQUEST);
	}
	
	
	/**
	 * Global exception handler for Exception
	 * 
	 * @param ex - Exception thrown in application
	 * @param request - Request Parameter to get details
	 * @return ResponseEntity<?> - Response Entity object
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<?> globalExceptionHandler(final Exception ex, WebRequest request) {
		return new ResponseEntity<>(handleException(ex), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
