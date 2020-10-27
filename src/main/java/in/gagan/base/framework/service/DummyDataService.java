package in.gagan.base.framework.service;

/**
 * This Service interface is used to create dummy data on server startup. 
 * 
 * @author gaganthind
 *
 */
public interface DummyDataService {
	
	/**
	 * This method creates the dummy users on application startup.
	 */
	void createDummyData();

}
