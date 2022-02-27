/*
 * SpringBoot_Hibernate_Framework
 * 
 * Copyright (C) 2020-2022  Gagan Thind

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
