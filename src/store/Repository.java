package store;

import java.util.ArrayList;
import java.util.Optional;

import api.bean.Company;
import api.bean.Preference;
import api.bean.Student;
import api.store.Localstore;

/**
 * A concrete Local Storage
 * In memory storage class of companies and student preferences
 * @author Punit_Ghodasara
 */
public class Repository implements Localstore{

	private ArrayList<Company> companies = new ArrayList<>();
	private ArrayList<Student> students = new ArrayList<>();

	private static Repository repo = new Repository();
	private Repository(){
	}
	
	public static Repository getInstance(){
		return repo;
	}
	
	@Override
	public Company searchCompany(String companyname, int jafno){
		Optional<Company> filterCompany = Repository.getInstance().getCompanies()
				.stream()
				.filter(c->c.getCompanyName().equals(companyname))
				.filter(c->c.getJafNo()==jafno)
				.findFirst();
		return filterCompany.isPresent()?filterCompany.get():null;
	}
	
	@Override
	public Student searchStudent(String rollno){
		Optional<Student> filterStudent = Repository.getInstance().getStudents()
				.stream()
				.filter(s->s.getRollno().equals(rollno))
				.findFirst();
		return filterStudent.isPresent()?filterStudent.get():null;
	}

	@Override
	public ArrayList<Company> getCompanies() {
		return companies;
	}
	@Override
	public ArrayList<Student> getStudents() {
		return students;
	}

	@Override
	public void loadCompanies(ArrayList<Company> companies) {
		this.companies.clear();
		this.companies.addAll(companies);
	}

	@Override
	public void loadStudents(ArrayList<Student> students) {
		this.students.clear();
		this.students.addAll(students);
	}

	@Override
	public void fillPreferences(ArrayList<Preference> prefs) {
		prefs.forEach(pref->{
			try {
				pref.getOwner().addPreference(pref);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
