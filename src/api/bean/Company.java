package api.bean;

/**
 * A company class with login ID, Jaf No and an agent
 * (We assume each JAF as company for this Job Allocation procedure same as slotting.) 
 * @author Punit_Ghodasara
 *
 */
public class Company implements Comparable<Company>{
	private String companyName;
	private int jafNo;
	private CompanyAgent recruiter;
	
	public Company(String companyName, int jafNo) {
		super();
		this.companyName = companyName;
		this.jafNo = jafNo;
		this.recruiter = new CompanyAgent(this);
	}

	// Getters
	public String getCompanyName() {
		return companyName;
	}
	public int getJafNo() {
		return jafNo;
	}
	public CompanyAgent getAgent(){
		return recruiter;
	}
	
	public boolean isSealed(){
		return getAgent().hasSealed();
	}
	
	@Override
	public String toString() {
		return companyName+" "+jafNo;
	}

	@Override
	public int compareTo(Company o) {
		int match = this.companyName.compareTo(o.companyName);
		return match==0?this.getJafNo()-o.jafNo:match;
	}

}
