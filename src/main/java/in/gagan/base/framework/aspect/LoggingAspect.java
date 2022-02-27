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

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
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
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		if(logger.isDebugEnabled()) {
			logger.debug("Enter: {}.{}() with argument[s] = {}", 
					joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), 
					Arrays.toString(joinPoint.getArgs()));
		}
		
		Object result = joinPoint.proceed();
		
		if(logger.isDebugEnabled()) {
			logger.debug("Exit: {}.{}()", 
					joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName());
		}
		
		return result;
	}
	
}
