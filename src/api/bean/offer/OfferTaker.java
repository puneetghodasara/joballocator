package api.bean.offer;

/**
 * An OfferTaker interface.
 * @author Punit_Ghodasara
 *
 */
public interface OfferTaker {

	/**
	 * Process to take offer.
	 * @param offer
	 */
	public void accept(JobOffer offer);
	
	/**
	 * Process to reject offer. Default is do nothing.
	 * @param offer
	 */
	public default void reject(JobOffer offer){
		// Default implementation is DO NOTHING,
		// but in future it might add some logic
		// like penalty etc.
	}
}
