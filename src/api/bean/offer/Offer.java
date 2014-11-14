package api.bean.offer;


/**
 * An Offer interface with required functionalties.
 * @author Punit_Ghodasara
 *
 */
public interface Offer {
	public OfferStatus getCurrentStatus();
	public void setAccepted();
	public void setRejected();
	public void setUplifted();
}
