/*
 * Copyright (C) 2020-2022  Gagan Thind
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package in.gagan.base.framework.advice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import in.gagan.base.framework.dto.ExceptionDetailDTO;
import in.gagan.base.framework.dto.ExceptionMonitorDTO;
import in.gagan.base.framework.exception.InvalidTokenException;
import in.gagan.base.framework.exception.InvalidVerificationTokenException;
import in.gagan.base.framework.exception.UsernameExistException;
import in.gagan.base.framework.service.ExceptionMonitoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
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

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * This class is a used to provide support for global exception handling.
 * 
 * @author gaganthind
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	private final ExceptionMonitoringService exceptionMonitoringSvc;

	private final MessageSource messageSource;
	
	@Autowired
	public GlobalExceptionHandler(ExceptionMonitoringService exceptionMonitoringSvc,
								  @Qualifier("exceptionMessageSource") MessageSource messageSource) {
		this.exceptionMonitoringSvc = exceptionMonitoringSvc;
		this.messageSource = messageSource;
	}

	final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	/**
	 * Convert the thrown exception into custom format using the Exception details class and log it
	 * 
	 * @param <T> - Generic type that extends Exception
	 * @param ex - Exception thrown in application
	 * @return ExceptionDetailDTO - Exception Details object thrown to user
	 */
	private <T extends Exception> ExceptionDetailDTO handleException(final T ex) {
		ExceptionDetailDTO exceptionDetailDTO = new ExceptionDetailDTO(ex);
		ExceptionMonitorDTO exceptionMonitorDTO = new ExceptionMonitorDTO(ex);
		this.exceptionMonitoringSvc.insertException(exceptionMonitorDTO);
		logger.error("Exception with cause = {}", exceptionMonitorDTO);
		
		return exceptionDetailDTO;
	}
	
	/**
	 * Convert the thrown exception into custom format using the Exception details class and log it
	 * 
	 * @param <T> - Generic type that extends Exception
	 * @param ex - Exception thrown in application
	 * @return ExceptionDetailDTO - Exception Details object thrown to user
	 */
	private <T extends Exception> ExceptionDetailDTO handleExceptionWithoutLogging(final T ex) {
		return new ExceptionDetailDTO(ex);
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
		String message = getMessage("exception.user.exists", ex.getMessage());
		return new ResponseEntity<>(handleException(new UsernameExistException(message)), HttpStatus.FOUND);
	}
	
	/**
	 * Global exception handler for UserNameNotFoundException
	 * 
	 * @param ex - Exception thrown in application
	 * @param request - Request Parameter to get details
	 * @return ResponseEntity<?> - Response Entity object
	 */
	@ExceptionHandler(UsernameNotFoundException.class)
	public final ResponseEntity<?> usernameNotFoundExceptionHandler(final UsernameNotFoundException ex, WebRequest request) {
		return new ResponseEntity<>(handleExceptionWithoutLogging(ex), HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Global exception handler for MethodArgumentNotValidException
	 * 
	 * @param ex - Exception thrown in application
	 * @param request - Request Parameter to get details
	 * @return ResponseEntity<?> - Response Entity object
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<?> methodArgumentNotValidExceptionHandler(final MethodArgumentNotValidException ex, WebRequest request) {
		BindingResult result = ex.getBindingResult();
		List<ObjectError> objectErrors = result.getAllErrors();
		List<String> errorMessages = objectErrors.stream()
														.map(ObjectError::getDefaultMessage)
														.collect(Collectors.toUnmodifiableList());
		
		Exception exception = new IllegalArgumentException(errorMessages.toString());
		
		return new ResponseEntity<>(handleException(exception), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Global exception handler for HttpMessageNotReadableException
	 * 
	 * @param ex - Exception thrown in application
	 * @param request - Request Parameter to get details
	 * @return ResponseEntity<?> - Response Entity object
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public final ResponseEntity<?> httpMessageNotReadableExceptionHandler(final HttpMessageNotReadableException ex, WebRequest request) {
		
		final String INVALID_JSON = "Invalid JSON";
		Throwable throwable = ex.getCause();
		Exception exception = ex;
		
		if (throwable instanceof JsonParseException) {
			exception = new IllegalArgumentException(INVALID_JSON);
		} else if (throwable instanceof InvalidFormatException) {
			
			if (throwable.getMessage().contains("value not one of declared Enum instance names")) {
				exception = new IllegalArgumentException(INVALID_JSON + ": Incorrect Enum values present");
			} else {
				exception = new IllegalArgumentException(INVALID_JSON);
			}
		}
		
		return new ResponseEntity<>(handleException(exception), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Global exception handler for ConstraintViolationException
	 * 
	 * @param ex - Exception thrown in application
	 * @param request - Request Parameter to get details
	 * @return ResponseEntity<?> - Response Entity object
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<?> constraintViolationExceptionHandler(final ConstraintViolationException ex, WebRequest request) {
		return new ResponseEntity<>(handleException(ex), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Global exception handler for IllegalAccessException
	 * 
	 * @param ex - Exception thrown in application
	 * @param request - Request Parameter to get details
	 * @return ResponseEntity<?> - Response Entity object
	 */
	@ExceptionHandler(IllegalAccessException.class)
	public final ResponseEntity<?> illegalAccessExceptionHandler(final IllegalAccessException ex, WebRequest request) {
		return new ResponseEntity<>(handleException(ex), HttpStatus.FORBIDDEN);
	}

	/**
	 * Global exception handler for InvalidTokenException
	 *
	 * @param ex - Exception thrown in application
	 * @param request - Request Parameter to get details
	 * @return ResponseEntity<?> - Response Entity object
	 */
	@ExceptionHandler(InvalidTokenException.class)
	public final ResponseEntity<?> invalidTokenExceptionHandler(final InvalidTokenException ex, WebRequest request) {
		return new ResponseEntity<>(handleException(ex), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Global exception handler for InvalidVerificationTokenException
	 *
	 * @param ex - Exception thrown in application
	 * @param request - Request Parameter to get details
	 * @return ResponseEntity<?> - Response Entity object
	 */
	@ExceptionHandler(InvalidVerificationTokenException.class)
	public final ResponseEntity<?> invalidVerificationTokenExceptionHandler(final InvalidVerificationTokenException ex, WebRequest request) {
		String message = getMessage("exception.verification.token.invalid");
		return new ResponseEntity<>(handleException(new InvalidVerificationTokenException(message)), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Global exception handler for IllegalArgumentException
	 *
	 * @param ex - Exception thrown in application
	 * @param request - Request Parameter to get details
	 * @return ResponseEntity<?> - Response Entity object
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public final ResponseEntity<?> illegalArgumentExceptionHandler(final IllegalArgumentException ex, WebRequest request) {
		return new ResponseEntity<>(handleException(ex), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Global exception handler for NoSuchElementException
	 *
	 * @param ex - Exception thrown in application
	 * @param request - Request Parameter to get details
	 * @return ResponseEntity<?> - Response Entity object
	 */
	@ExceptionHandler(NoSuchElementException.class)
	public final ResponseEntity<?> noSuchElementExceptionHandler(final NoSuchElementException ex, WebRequest request) {
		return new ResponseEntity<>(handleException(ex), HttpStatus.BAD_REQUEST);
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

	/**
	 * Method used to get the message text from the message property file.
	 *
	 * @param message - message key corresponding to the message text
	 * @param tokens - any input token to be added to message text
	 * @return messageString - returns the complete message to be shown to user
	 */
	private String getMessage(String message, Object... tokens) {
		return this.messageSource.getMessage(message, tokens, Locale.ENGLISH);
	}

}
