package in.gagan.base.framework.entity.base;

/**
 * Base entity for all entity classes
 * 
 * @author gaganthind
 *
 */
public interface BaseEntity {
	
	/**
	 * Method used to get if the entity is active or not.
	 * 
	 * @return activeSw: Activation status i.e. Y is Active and N is inactive
	 */
	public char isActive();

	/**
	 * Method used to set the entity activation status.
	 * 
	 * @param activeSw: Activation status i.e. Y is Active and N is inactive
	 */
	public void setActiveSw(char activeSw);
	
}
