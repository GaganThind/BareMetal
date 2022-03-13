/*
 * SpringBoot_Hibernate_Framework
 * 
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

package in.gagan.base.framework.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import in.gagan.base.framework.service.admin.AdminService;
import in.gagan.base.framework.service.location.CountryService;
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
	
	private final CountryService countryService;
	
	@Autowired
	public CommandLineAppStartupRunner(AdminService adminSvc, DummyDataService dummyDataSvc, CountryService countryService) {
		this.adminSvc = adminSvc;
		this.dummyDataSvc = dummyDataSvc;
		this.countryService = countryService;
	}
	
	@Override
	public void run(String... args) throws Exception {
		this.adminSvc.createAdminUser();
		this.countryService.loadCountriesFromCSV();
		this.dummyDataSvc.createDummyData();
	}

}
