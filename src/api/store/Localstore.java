package api.store;

import java.util.ArrayList;
import java.util.Collection;

import ui.bean.UIOfferBean;
import api.bean.Company;
import api.bean.Preference;
import api.bean.Student;

/**
 * Local storage class of companies and students
 * @author Punit_Ghodasara
 *
 */
public interface Localstore {

	// Sync methods
	public void loadCompanies(ArrayList<Company> companies);
	public void loadStudents(ArrayList<Student> students);
	public void fillPreferences(ArrayList<Preference> prefs);
	
	// Search methods
	public Student searchStudent(String rollno);
	public Company searchCompany(String companyname, int jafno);
	
	// To get object
	public ArrayList<Company> getCompanies();
	public ArrayList<Student> getStudents();
}
