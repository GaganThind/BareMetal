package in.gagan.base.framework.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * This class has the Message Source configurations needed for the dynamic local based message display to work.
 * 
 * @author gaganthind
 *
 */
@Configuration
public class MessageSourceConfig implements WebMvcConfigurer {
	
	/**
	 * Message Source.
	 * 
	 * @return MessageSource - message source bean
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	/**
	 * Local Validator Factor bean.
	 * 
	 * @param messageSource - message source bean
	 * @return LocalValidatorFactoryBean - Local Validator Factory bean
	 */
	/**
	 * @param messageSource
	 * @return
	 */
	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean(MessageSource messageSource) {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(messageSource);
		return localValidatorFactoryBean;
	}
	
	/**
	 * Locale Resolver bean.
	 * 
	 * @return LocaleResolver - Locale Resolver bean
	 */
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(Locale.US);
		return sessionLocaleResolver;
	}
	
	/**
	 * Locale Change Interceptor bean.
	 * 
	 * @return LocaleChangeInterceptor - Locale Change Interceptor bean
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}
	
	/**
	 * Method Validation Post Processor bean.
	 * 
	 * @return MethodValidationPostProcessor - Method Validation Post Processor bean 
	 */
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}
	
	/**
	 *	This method adds the localeChangeInterceptor to the interceptor registry.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
	
}
