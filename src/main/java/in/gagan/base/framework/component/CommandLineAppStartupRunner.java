package in.gagan.base.framework.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import in.gagan.base.framework.service.UserSecurityService;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
	
	@Autowired
	private UserSecurityService userSecuritySvc;
	
	@Override
	public void run(String... args) throws Exception {
		this.userSecuritySvc.createAdminUser();
	}

}
