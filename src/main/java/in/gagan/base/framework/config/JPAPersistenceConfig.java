/*
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

package in.gagan.base.framework.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import in.gagan.base.framework.component.AuditorAwareImpl;
import in.gagan.base.framework.constant.PersistenceConstants;

/**
 * This class has the JPA persistence configurations needed for the persistence to work.
 * 
 * @author gaganthind
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@PropertySource("classpath:jpaConfiguration.properties")
public class JPAPersistenceConfig {

	/**
	 * Environment variable
	 */
	private final Environment environment;
	
	@Autowired
	public JPAPersistenceConfig(Environment environment) {
		this.environment = environment;
	}

	/**
	 * Default Entity Manager instance
	 * 
	 * @return LocalContainerEntityManagerFactoryBean
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(PersistenceConstants.BASE_ENTITY_PATH, 
				PersistenceConstants.APPLICATION_ENTITY_PATH);
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		em.setJpaProperties(additionalProperties());

		return em;
	}

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("jpa.datasource.driver-class-name"));
        dataSource.setUrl(environment.getProperty("jpa.datasource.url"));
        dataSource.setUsername(environment.getProperty("jpa.datasource.username"));
        dataSource.setPassword(environment.getProperty("jpa.datasource.password"));

        return dataSource;
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
	 * Auditor Aware
	 * 
	 * @return AuditorAware<String> - AuditorAware logging
	 */
	@Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
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
