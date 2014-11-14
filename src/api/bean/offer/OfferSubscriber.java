package api.bean.offer;

/**
 * An OfferSubscriber interface
 * @author Punit_Ghodasara
 *
 */
public interface OfferSubscriber {

	/**
	 * Process when Offer is rejected.
	 * @param offer
	 */
	public void offerRejected(JobOffer offer);
	
	/**
	 * Process when offer is accepted. Default is do nothing.
	 * @param offer
	 */
	public default void offerAccepted(JobOffer offer){
		// Do nothing.
	}
	
	/**
	 * Process when offer has been converted from WL to Actual. Default is nothing.
	 * @param offer
	 */
	public default void offerUplifted(JobOffer offer){
		// Do nothing.
	}
	
}
