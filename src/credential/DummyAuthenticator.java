package credential;

import api.credential.Authenticator;
import api.credential.LoginCredential;

public class DummyAuthenticator implements Authenticator {

	@Override
	public boolean isValidLogin(LoginCredential cred) {
		return cred.getUsername().equals("placementoffice") && cred.getPassword().equals("j0b@iit");
	}

}
