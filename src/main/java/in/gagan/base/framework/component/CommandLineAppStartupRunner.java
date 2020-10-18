package in.gagan.base.framework.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import in.gagan.base.framework.service.DummyDataService;
import in.gagan.base.framework.service.UserRegisterationService;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
	
	private final UserRegisterationService userRegistrationSvc;
	
	private final DummyDataService dummyDataSvc;
	
	@Autowired
	public CommandLineAppStartupRunner(UserRegisterationService userRegistrationSvc, DummyDataService dummyDataSvc) {
		this.userRegistrationSvc = userRegistrationSvc;
		this.dummyDataSvc = dummyDataSvc;
	}
	
	@Override
	public void run(String... args) throws Exception {
		this.userRegistrationSvc.createAdminUser();
		this.dummyDataSvc.createDummyData();
	}

}
