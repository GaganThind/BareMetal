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
/*
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import in.gagan.base.framework.constant.PersistenceConstants;

/**
 * This class has the Hibernate persistence configurations needed for the persistence to work.
 * 
 * @author gaganthind
 *
 */ /*
@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:jpaConfiguration.properties" })
public class HibernatePersistenceConfig {

	private final DataSource dataSource;

	private final Environment environment;
	
	@Autowired
	public HibernatePersistenceConfig(DataSource dataSource, Environment environment) {
		this.dataSource = dataSource;
		this.environment = environment;
	}

	@Autowired
	@Bean(name = "sessionFactory")
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan(PersistenceConstants.BASE_ENTITY_PATH);
		sessionFactory.setHibernateProperties(additionalProperties());
		return sessionFactory;
	}

	/**
	 * Hibernate specific properties
	 * 
	 * @return Properties
	 */ /*
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

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}
} */
