package in.gagan.base.framework.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import in.gagan.base.framework.constant.ApplicationConstants;

/**
 * This class provides performance monitoring functionality by using the spring AOP
 * 
 * @author gaganthind
 *
 */
@Aspect
@Order(2)
@Component
public class PerformanceMonitorAspect {
	
	Logger logger = LoggerFactory.getLogger(PerformanceMonitorAspect.class);
	
	@Value("${application.performance.monitor}")
	private String performanceMonitoring;
	
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
	 * Advice that logs the time taken to execute a method.
	 *
	 * @param joinPoint join point for advice
	 * @return result
	 * @throws Throwable throws IllegalArgumentException
	 */
	@Around("baseFrameworkPointcut() || applicationPointcut()")
	public Object performanceMonitor(ProceedingJoinPoint joinPoint) throws Throwable {
		
		long startTime = System.currentTimeMillis();
				
		Object result = joinPoint.proceed();
		
		if(ApplicationConstants.String_Y.equals(performanceMonitoring)) {
			long endTime = System.currentTimeMillis();
			long executionTime = endTime - startTime;
			
			logger.info("Successfully executed method: {}.{}() in {} ms", 
					joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), 
					executionTime);
		}
		
		return result;
	}

}
