package api.bean.offer;

import api.bean.Company;
import api.bean.Student;

/**
 * A Job Offer by a company to a student with the type.
 * Implements all the functionalities of Offer interface.
 * 
 * InitialStatus is provided to check-through what was initial status.
 * CurrentStatus informs if this offer has been accepted or rejected or waitlist.  
 * @author Punit_Ghodasara
 */
public class JobOffer implements Offer,Comparable<JobOffer> {
	
	private Company company;
	private Student student;
	private OfferStatus initStatus;			// Status in offer letter
	private OfferStatus currentStatus;		// Status after job allocation
	private int rank;
	
	public JobOffer(Company company, Student student, OfferStatus initStatus, int rank) {
		super();
		this.company = company;
		this.student = student;
		this.initStatus = initStatus;
		this.currentStatus = this.initStatus;
		this.rank=rank;
	}
	
	public Student getStudent() {
		return student;
	}
	public OfferStatus getInitStatus() {
		return initStatus;
	}
	public OfferStatus getCurrentStatus() {
		return currentStatus;
	}
	public int getRank(){
		return rank;
	}
	public Company getCompany(){
		return company;
	}
	
	private void notifySubscriber(){
		switch(this.currentStatus){
			case ACCEPTED:
				this.company.getAgent().offerAccepted(this);
				break;
			case ACTUAL_OFFER:
				this.company.getAgent().offerUplifted(this);
				break;
			case REJECTED:
				this.company.getAgent().offerRejected(this);
				break;
			case WAITLIST_OFFER:
				break;
		}
	}
	
	public void setAccepted(){
		this.currentStatus = OfferStatus.ACCEPTED;
		notifySubscriber();
	}
	public void setRejected(){
		this.currentStatus = OfferStatus.REJECTED;
		notifySubscriber();
	}
	public void setUplifted(){
		if(this.currentStatus!=OfferStatus.WAITLIST_OFFER)
			return;		
		this.currentStatus = OfferStatus.ACTUAL_OFFER;
		notifySubscriber();
	}
	
	@Override
	public String toString(){
		return "Roll : "+ student+" : "+initStatus+"/"+currentStatus+"\n";
	}

	@Override
	public int compareTo(JobOffer offer) {
		return this.getRank()-offer.getRank();
	}
}
