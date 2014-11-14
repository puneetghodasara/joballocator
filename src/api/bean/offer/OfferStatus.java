package api.bean.offer;

/**
 * Enum of various offer status
 * 
 * Pre-job allocation statuses :
 * ACTUAL_OFFER informs job is confirmed
 * WAITLIST_OFFER informs job is in waitlist.
 * 
 * Post-job allocation statuses :
 * ACCEPTED informs job is accepted
 * REJECTED informs job is declined by student due to other higher prefferd job
 * WAITLIST_OFFER informs that it needs not be processed as all ACTUAL offers are accepted
 * 
 * @author Punit_Ghodasara
 */
public enum OfferStatus {

	ACTUAL_OFFER,
	WAITLIST_OFFER,
	REJECTED,
	ACCEPTED
}
