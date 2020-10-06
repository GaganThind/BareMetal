package in.gagan.base.framework.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import in.gagan.base.framework.service.UserRegisterationService;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
	
	private final UserRegisterationService userRegistrationSvc;
	
	@Autowired
	public CommandLineAppStartupRunner(UserRegisterationService userRegistrationSvc) {
		this.userRegistrationSvc = userRegistrationSvc;
	}
	
	@Override
	public void run(String... args) throws Exception {
		this.userRegistrationSvc.createAdminUser();
	}

}
