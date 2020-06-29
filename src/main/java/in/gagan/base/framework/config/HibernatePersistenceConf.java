package in.gagan.base.framework.config;
/*
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import in.gagan.base.framework.constant.PersistenceConstants;

/**
 * Hibernate persistence config file.
 * 
 * @author gaganthind
 *
 *//*
@Configuration
@EnableTransactionManagement
public class HibernatePersistenceConf {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private Environment environment;

	@Autowired
	@Bean(name = "sessionFactory")
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan(PersistenceConstants.BASE_MODELS_PATH);
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
