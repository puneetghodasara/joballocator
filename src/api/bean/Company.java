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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + jafNo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (jafNo != other.jafNo)
			return false;
		return true;
	}

	
}
