package api.context;

import store.Repository;
import api.credential.Authenticator;
import api.fetcher.DataFetcher;
import api.store.Localstore;
import credential.DummyAuthenticator;
import fetcher.DBDataFetcher;
import fetcher.DummyDataFetcher;

/**
 * Global Parameters
 * Class also connects of data fetcher, local store and auth 
 * @author Punit_Ghodasara
 *
 */
public class GlobalContext {

	public static String BATCH = "2013";
	public static int DAY = 1;
	public static int SLOT = 1;
	
	public static String DB_HOST_NAME = "10.99.0.26";
	public static String DB_SCHMEA_NAME = "placements";
	public static String DB_PASSWORD = "webco1213";
	public static String DB_USER_NAME = "jobuser";

	
	public static final String COMPANY_SLOTTING_TABLE = "scheduling_company_info";
	public static final String STUDENT_PREF_TABLE = "scheduling_student_preferences_daywise";
	public static final String STUDENT_TABLE = "student";

	public static final String COMPID_COLUMN = "complogin";
	public static final String PROFILEID_COLUMN = "jafsrno";
	public static final String PREF_RANK_COLUMN = "preference";
	public static final String STUDENTID_COLUMN = "rollno";
	public static final String BATCH_COLUMN = "batch";
	public static final String DAY_COLUMN = "day";
	public static final String SLOT_COLUMN = "slot";
	
	public static final String LOGIN_ATTEMPT_FAILURE = "Username / Password are invalid. Try again.";
	public static final String DATA_ATTEMPT_FAILURE = "Credential for data conneciton is invalid.";
	public static final String CONFIRM_EXIT = "Do you really want to exit the application?";
	
	
	public static DataFetcher getDataFetcher(){
//		return new DummyDataFetcher();
		return new DBDataFetcher();
	}
	
	public static Localstore getLocalStore(){
		return Repository.getInstance();
	}

	public static Authenticator getAuthenticator(){
		return new DummyAuthenticator();
	}
}
