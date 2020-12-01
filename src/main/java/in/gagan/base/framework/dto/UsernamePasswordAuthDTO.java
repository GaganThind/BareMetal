package in.gagan.base.framework.dto;

import java.io.Serializable;

/**
 * This DTO captures the username and password from the user for authentication by the spring security.
 * 
 * @author gaganthind
 *
 */
public class UsernamePasswordAuthDTO implements Serializable {
	
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -8182426431134103977L;
	
	private String username;
	
	private String password;
	
	public UsernamePasswordAuthDTO() { }
	
	public UsernamePasswordAuthDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
