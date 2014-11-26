package fetcher;

import java.util.ArrayList;

import api.bean.Company;
import api.bean.Preference;
import api.bean.Student;
import api.fetcher.DataFetcher;

public class DummyDataFetcher implements DataFetcher {

	static ArrayList<Student> studs = new ArrayList<>();
	static ArrayList<Company> comps = new ArrayList<>();
	static{
		studs.add(new Student("101"));
		studs.add(new Student("102"));
//		studs.add(new Student("103"));
//		studs.add(new Student("104"));
//		studs.add(new Student("105"));
//		studs.add(new Student("106"));
		
	
		comps.add(new Company("Google", 1));
		comps.add(new Company("MS", 1));
//		comps.add(new Company("FB", 1));
//		comps.add(new Company("WAY", 1));
		
	}
	
	@Override
	public ArrayList<Company> fetchCompanies(String batch, int day, int slot) {
		ArrayList<Company> comp = new ArrayList<>();
		for(Company c : comps)
			comp.add(c);
		return comp;
	}

	@Override
	public ArrayList<Student> fetchStudents(String batch, int day, int slot) {
		ArrayList<Student> stud = new ArrayList<>();
		for(Student s:studs)
			stud.add(s);
		return stud;
	}

	@Override
	public ArrayList<Preference> fetchPreferences(String batch, int day, int slot) {
		ArrayList<Preference> prefs = new ArrayList<>();
		prefs.add(new Preference(9, comps.get(1), studs.get(0)));
		prefs.add(new Preference(2, comps.get(0), studs.get(0)));
//		prefs.add(new Preference(10, comps.get(0), studs.get(0)));
		
		prefs.add(new Preference(1, comps.get(1), studs.get(1)));
//		prefs.add(new Preference(2, comps.get(2), studs.get(1)));
		
//		prefs.add(new Preference(1, comps.get(1), studs.get(2)));
//		prefs.add(new Preference(1, comps.get(1), studs.get(3)));
//		prefs.add(new Preference(2, comps.get(0), studs.get(3)));
//		
//		prefs.add(new Preference(1, comps.get(1), studs.get(4)));
//		prefs.add(new Preference(1, comps.get(1), studs.get(5)));
		
		return prefs;
	}

	@Override
	public boolean testConnection() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}

}
