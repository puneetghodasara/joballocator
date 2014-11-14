package api.fetcher;

import java.util.ArrayList;

import api.bean.Company;
import api.bean.Preference;
import api.bean.Student;

/**
 * An interface to fetch data about Companies, Students, Preferences.
 * @author Punit_Ghodasara
 *
 */
public interface DataFetcher {

	/**
	 * Test if data can be fetched
	 * @return
	 */
	public boolean testConnection();
	
	public ArrayList<Company> fetchCompanies(String batch, int day, int slot);
	public ArrayList<Student> fetchStudents(String batch, int day, int slot);
	public ArrayList<Preference> fetchPreferences(String batch, int day, int slot);
	
}
