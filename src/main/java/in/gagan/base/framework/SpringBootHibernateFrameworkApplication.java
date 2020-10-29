package in.gagan.base.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Spring boot starter file
 * 
 * @author gaganthind
 *
 */
@EnableAspectJAutoProxy
@EnableAsync
@SpringBootApplication
public class SpringBootHibernateFrameworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHibernateFrameworkApplication.class, args);
	}

}
