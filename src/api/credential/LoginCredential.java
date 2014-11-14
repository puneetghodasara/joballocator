package api.credential;

/**
 * Bean class of login credential
 * @author Punit_Ghodasara
 *
 */
public class LoginCredential {

	private String username;
	private String password;
	
	public LoginCredential(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
}
