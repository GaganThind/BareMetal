package in.gagan.base.framework.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import in.gagan.base.framework.service.AdminService;
import in.gagan.base.framework.service.DummyDataService;

/**
 * This class is the implementation of CommandLineRunner interface and provides a way to perform operations
 * on application startup.
 * Here we have leveraged this class to create admin account and some dummy users on application startup
 * 
 * @author gaganthind
 *
 */
@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
	
	private final AdminService adminSvc;
	
	private final DummyDataService dummyDataSvc;
	
	@Autowired
	public CommandLineAppStartupRunner(AdminService adminSvc, DummyDataService dummyDataSvc) {
		this.adminSvc = adminSvc;
		this.dummyDataSvc = dummyDataSvc;
	}
	
	@Override
	public void run(String... args) throws Exception {
		this.adminSvc.createAdminUser();
		this.dummyDataSvc.createDummyData();
	}

}
