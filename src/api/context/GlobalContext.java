package api.context;

import store.Repository;
import api.credential.Authenticator;
import api.fetcher.DataFetcher;
import api.store.Localstore;
import credential.DummyAuthenticator;
import fetcher.DummyDataFetcher;

/**
 * Global Parameters
 * Class also connects of data fetcher, local store and auth 
 * @author Punit_Ghodasara
 *
 */
public class GlobalContext {

	public static final String BATCH = "2012";
	public static final int DAY = 2;
	public static final int SLOT = 1;
	
	public static String DB_HOST_NAME = null;
	public static String DB_SCHMEA_NAME = null;
	public static String DB_PASSWORD = null;
	public static String DB_USER_NAME = null;

	
	public static final String COMPANY_SLOTTING_TABLE = "comp_slotting_info";
	public static final String STUDENT_PREF_TABLE = "stud_pref_info";
	public static final String STUDENT_TABLE = "stud_pref_info";

	public static final String COMPID_COLUMN = "compid";
	public static final String PROFILEID_COLUMN = "jafsrno";
	public static final String PREF_RANK_COLUMN = "pref";
	public static final String STUDENTID_COLUMN = "rollno";
	public static final String BATCH_COLUMN = "batch";
	public static final String DAY_COLUMN = "day";
	public static final String SLOT_COLUMN = "slot";
	
	public static final String LOGIN_ATTEMPT_FAILURE = "Username / Password are invalid. Try again.";
	public static final String DATA_ATTEMPT_FAILURE = "Credential for data conneciton is invalid.";
	public static final String CONFIRM_EXIT = "Do you really want to exit the application?";
	
	
	public static DataFetcher getDataFetcher(){
		return new DummyDataFetcher();
	}
	
	public static Localstore getLocalStore(){
		return Repository.getInstance();
	}

	public static Authenticator getAuthenticator(){
		return new DummyAuthenticator();
	}
}
