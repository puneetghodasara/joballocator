package api.bean;


/**
 * A numerical preference of student to a company 
 * @author Punit_Ghodasara
 *
 */
public class Preference implements Comparable<Preference>{

	private int rank;
	private Company company;
	private Student owner;

	public Preference(int rank, Company company, Student student) {
		super();
		this.rank = rank;
		this.company = company;
		this.owner = student;
	}

	public int getRank() {
		return rank;
	}
	public Company getCompany() {
		return company;
	}
	public Student getOwner(){
		return owner;
	}

	@Override
	public int compareTo(Preference offer) {
		return this.rank - offer.rank;
	}

}
