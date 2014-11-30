package api.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import api.bean.offer.JobOffer;
import api.bean.offer.OfferTaker;
import api.context.GlobalContext;
import api.exception.IllegalPreferenceException;

/**
 * A student info class with preferences
 * @author Punit_Ghodasara
 *
 */
public class Student implements OfferTaker, Comparable<Student> {

	private String rollno;
	private String name;
	
	private Collection<Preference> jobpref = new ArrayList<Preference>();

	private JobOffer acceptedOffer = null;
	
	public Student(String rollno, String name) {
		super();
		this.rollno = rollno;
		this.name = name;
	}

	@Override
	public void accept(JobOffer offer) {
		acceptedOffer = offer;
//		List<Company> rejectedComps = GlobalContext.getLocalStore().getCompanies().stream().filter(c->c.getAgent().hasOfferFor(this)).collect(Collectors.toList());
//		for(Company c : rejectedComps){
//			System.out.println("Student rejecting "+c);
//			c.getAgent().getOfferOf(this).setRejected();
//		}
	}
	
	/**
	 * Adds a job preference of this student. 
	 * @param pref
	 * @throws IllegalPreferenceException
	 */
	public void addPreference(Preference pref) throws IllegalPreferenceException{
//		if(jobpref.stream().anyMatch(p->p.getRank()==pref.getRank()))
//			throw new IllegalPreferenceException("Preference Number "+pref.getRank()+" is already added.");
//		else
			jobpref.add(pref);
	}
	
	/**
	 * @returns ordered list of companies based on preferences. 
	 */
	public List<Preference> getPreferences() {
		return jobpref
				.stream()
				.sorted()
				.collect(Collectors.toList());
	}
	
	// Getters
	public String getRollno() {
		return rollno;
	}
	public String getName() {
		return name;
	}
	public JobOffer getAcceptedOffer(){
		return acceptedOffer;
	}
	public boolean isPlaced(){
		return acceptedOffer!=null;
	}
	public boolean hasNoPreference(){
		return jobpref.isEmpty();
	}

	@Override
	public String toString() {
		return getRollno()+":"+getName();
	}

	@Override
	public int compareTo(Student o) {
		return o.rollno.compareTo(this.rollno);
	}
}
