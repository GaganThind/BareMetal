package in.gagan.base.framework.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * This class provides the logging functionality by using spring AOP
 * 
 * @author gaganthind
 *
 */
@Aspect
@Order(1)
@Component
public class LoggingAspect {

	Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	/**
	 * Pointcut that matches all repositories, services and Web REST endpoints.
	 */
	@Pointcut("within(@org.springframework.stereotype.Repository *) "
			+ "|| within(@org.springframework.stereotype.Service *)"
			+ "|| within(@org.springframework.web.bind.annotation.RestController *)")
	private void springBeanPointcut() {
		/** Dummy method for Pointcut, the implementations are in the advices. */
	}
	
	/**
	 * Pointcut that matches all Spring beans in the application's main packages.
	 */
	@Pointcut("execution(* in.gagan.base.framework.service.*.*(..)) "
			+ "|| execution(* in.gagan.base.framework.controller.*.*(..)) "
			+ "|| execution(* in.gagan.base.framework.dao.*.*(..))")
	private void baseFrameworkPointcut() {
		/** Dummy method for Pointcut, the implementations are in the advices. */
	}
	
	/**
	 * Pointcut that matches all Spring beans in the application's main packages.
	 */
	@Pointcut("execution(* in.gagan.application.service.*.*(..)) "
			+ "|| execution(* in.gagan.application.controller.*.*(..)) "
			+ "|| execution(* in.gagan.application.dao.*.*(..)) ")
	private void applicationPointcut() {
		/** Dummy method for Pointcut, the implementations are in the advices. */
	}
	
	/**
	 * Advice that logs when a method is entered and exited.
	 *
	 * @param joinPoint join point for advice
	 * @return result
	 * @throws Throwable throws IllegalArgumentException
	 */
	@Around("baseFrameworkPointcut() || applicationPointcut() || springBeanPointcut()")
	public void logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		if(logger.isDebugEnabled()) {
			logger.debug("Enter: {}.{}() with argument[s] = {}", 
					joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), 
					Arrays.toString(joinPoint.getArgs()));
		}
		
		joinPoint.proceed();
		
		if(logger.isDebugEnabled()) {
			logger.debug("Exit: {}.{}()", 
					joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName());
		}
	}
	
	/**
	 * Advice that logs methods throwing exceptions.
	 *
	 * @param joinPoint join point for advice
	 * @param e         exception
	 */
	@AfterThrowing(pointcut = "baseFrameworkPointcut() || applicationPointcut()", throwing = "ex")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		logger.error("Exception in {}.{}() with cause = {}", 
				joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), 
				null != ex.toString() ? ex.toString() : "Unknown");
	}
	
}
