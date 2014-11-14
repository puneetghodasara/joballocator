package api.credential;

/**
 * An Authenticator interface.
 * @author Punit_Ghodasara
 *
 */
public interface Authenticator {

	/**
	 * @param cred
	 * @return if credential is valid or not
	 */
	public boolean isValidLogin(LoginCredential cred);
}
