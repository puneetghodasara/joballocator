package core;

import static org.junit.Assert.*;

import org.junit.Test;

import api.bean.Company;
import api.bean.offer.JobOffer;
import api.bean.offer.OfferStatus;

public class PreferenceOrder {

	@Test
	public void testWaitlistSequentialProcessing() {
		Company c = new Company("Google",1);
		JobOffer o1 = new JobOffer("CS1", OfferStatus.ACTUAL_OFFER, 1);
		JobOffer o2 = new JobOffer("CS2", OfferStatus.ACTUAL_OFFER, 2);
		JobOffer o3 = new JobOffer("CS3", OfferStatus.WAITLIST_OFFER, 3);
		JobOffer o4 = new JobOffer("CS4", OfferStatus.WAITLIST_OFFER, 5);
		JobOffer o5 = new JobOffer("CS5", OfferStatus.WAITLIST_OFFER, 4);
		
		c.addOffer(o1);
		c.addOffer(o2);
		c.addOffer(o3);
		c.addOffer(o4);
		c.addOffer(o5);

		c.reject(o1);
		c.reject(o2);

		// Offer 4 should be in WL,
		// But Offer 5 should now actual.
		assertTrue(o4.getCurrentStatus().equals(OfferStatus.WAITLIST_OFFER));
		assertTrue(o5.getCurrentStatus().equals(OfferStatus.ACTUAL_OFFER));
		
	}

}
