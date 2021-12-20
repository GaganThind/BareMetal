package in.gagan.base.framework.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * This DTO captures the country specific data.
 * 
 * @author gaganthind
 *
 */
public final class CountryDTO implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -6852622873535636645L;
	
	private final String id;
	
	private final String name;
	
	private Set<StateDTO> states;
	
	public CountryDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public CountryDTO(String id, String name, Set<StateDTO> states) {
		this.id = id;
		this.name = name;
		this.states = states;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set<StateDTO> getStates() {
		return states;
	}
	
	public void addState(StateDTO state) {
		if (null == this.states) {
			this.states = new HashSet<>();
		}
		
		this.states.add(state);
	}

}
