package in.gagan.base.framework.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import in.gagan.base.framework.constant.PersistenceConstants;

/**
 * JPA persistence config file
 * 
 * @author gaganthind
 *
 */
@Configuration
@EnableTransactionManagement
public class JPAPersistenceConf {

	/**
	 * Default Spring data source
	 */
	@Autowired
	private DataSource dataSource;

	/**
	 * Environment variable
	 */
	@Autowired
	private Environment environment;

	/**
	 * Default Entity Manager instance
	 * 
	 * @return LocalContainerEntityManagerFactoryBean
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan(PersistenceConstants.BASE_MODELS_PATH, 
				PersistenceConstants.APPLICATION_MODELS_PATH);
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		em.setJpaProperties(additionalProperties());

		return em;
	}

	/**
	 * Transaction Manager
	 * 
	 * @return PlatformTransactionManager
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

		return transactionManager;
	}

	/**
	 * Exception post processor
	 * 
	 * @return PersistenceExceptionTranslationPostProcessor
	 */
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	/**
	 * Hibernate specific properties
	 * 
	 * @return Properties
	 */
	public final Properties additionalProperties() {
		Properties properties = new Properties();
		properties.put(PersistenceConstants.HIBERNATE_CONF_DIALECT,
				environment.getRequiredProperty(PersistenceConstants.HIBERNATE_CONF_DIALECT));
		properties.put(PersistenceConstants.HIBERNATE_CONF_SHOW_SQL,
				environment.getRequiredProperty(PersistenceConstants.HIBERNATE_CONF_SHOW_SQL));
		properties.put(PersistenceConstants.HIBERNATE_CONF_FORMAT_SQL,
				environment.getRequiredProperty(PersistenceConstants.HIBERNATE_CONF_FORMAT_SQL));
		properties.put(PersistenceConstants.HIBERNATE_CONF_HBM2DDL_AUTO,
				environment.getRequiredProperty(PersistenceConstants.HIBERNATE_CONF_HBM2DDL_AUTO));
		properties.put(PersistenceConstants.HIBERNATE_CONF_CONNECTION_POOL_SIZE,
				environment.getRequiredProperty(PersistenceConstants.HIBERNATE_CONF_CONNECTION_POOL_SIZE));
		properties.put(PersistenceConstants.HIBERNATE_CONF_CURRENT_SESSION_CONTEXT_CLASS,
				environment.getRequiredProperty(PersistenceConstants.HIBERNATE_CONF_CURRENT_SESSION_CONTEXT_CLASS));
		properties.put(PersistenceConstants.HIBERNATE_CONF_CACHE_PROVIDER_CLASS,
				environment.getRequiredProperty(PersistenceConstants.HIBERNATE_CONF_CACHE_PROVIDER_CLASS));
		return properties;
	}

}
