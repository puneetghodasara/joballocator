package api.bean;

import java.util.ArrayList;
import java.util.Optional;

import api.bean.offer.JobOffer;
import api.bean.offer.OfferStatus;
import api.bean.offer.OfferSubscriber;
/**
 * A CompanyAgent who is subscribed to offers a company presents
 * and having privileges of doing operations
 * defined in OfferSubscriber.  
 * @author Punit_Ghodasara
 *
 */
public class CompanyAgent implements OfferSubscriber{

	private ArrayList<JobOffer> offers = new ArrayList<>();
	private Company company;
		
	public CompanyAgent(Company comp) {
		super();
		this.company = comp;
	}

	public ArrayList<JobOffer> getOffers() {
		return offers;
	}
	
	/**
	 * Agent can present an offer to a student on behalf of company
	 * @param student
	 * @param offerstatus
	 * @param offerrank
	 */
	public void presentOffer(Student student, OfferStatus offerstatus,int offerrank) {
		offers.add(new JobOffer(company, student, offerstatus, offerrank));
	}

	/**
	 * Agent can delete an offer
	 * @param offer
	 */
	public void revokeOffer(JobOffer offer) {
		offers.remove(offer);
	}


	/**
	 * To check if student has been assigned a job
	 * in this company in any of the offers
	 * @param aRoll
	 * @return if student has a chance of job (actual offer or waitlist) in this company
	 */
	public boolean hasOfferFor(Student aStud){
		return offers.stream()
				.filter(o->o.getStudent().equals(aStud))
				.findFirst().isPresent();
	}
	
	/**
	 * To get the actual offer object
	 * @param aStud
	 * @return offer object
	 */
	public JobOffer getOfferOf(Student aStud){
		if(!hasOfferFor(aStud))
			return null;
		return offers.stream()
				.filter(o->o.getStudent().equals(aStud))
				.findFirst().get();
	}

	/**
	 * Converts offer into rejected
	 * It takes care to uplift waitlist status to actual offer
	 * @param offer
	 */
	@Override
	public void offerRejected(JobOffer offer) {
		// When one rejects, WL goes to ACTUAL
		Optional<JobOffer> wlOffers = offers.stream()
					.sorted()
					.filter(o->o.getCurrentStatus().equals(OfferStatus.WAITLIST_OFFER))
					.findFirst();
		if(wlOffers.isPresent())
			wlOffers.get().setUplifted();		
	}

	/**
	 * Company offer letter can be said sealed 
	 * if total_accepted offer reaches actual_offer
	 * if it doesn't reach, there should not be any waitlist.
	 * @return company seal status
	 */
	public boolean hasSealed(){
		return actualOffered()==totalAccepted()
				?true
				:(currentPending()<=0)?true:false;
	}

	// Stat functions
	private int actualOffered(){
		return (int) offers.stream()
				.filter(o->o.getInitStatus().equals(OfferStatus.ACTUAL_OFFER))
				.count();
	}
	private int totalAccepted(){
		return (int) offers.stream()
				.filter(o->o.getCurrentStatus().equals(OfferStatus.ACCEPTED))
				.count();
	}
	private int currentPending(){
		return (int) offers.stream()
				.filter(o->o.getCurrentStatus().equals(OfferStatus.WAITLIST_OFFER))
				.count();
	}

}
