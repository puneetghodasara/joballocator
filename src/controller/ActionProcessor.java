package controller;

import java.util.ArrayList;

import api.bean.Company;
import api.bean.Student;
import api.bean.offer.JobOffer;
import api.bean.offer.OfferStatus;
import api.context.GlobalContext;
import api.credential.DBCredential;
import api.store.Localstore;

/**
 * UI calls with parameters to take actions
 * @author Punit_Ghodasara
 *
 */
public class ActionProcessor {

	public static boolean process(DBCredential dbCred){
		// Save credentials provided
		GlobalContext.DB_HOST_NAME = dbCred.getHostname();
		GlobalContext.DB_SCHMEA_NAME = dbCred.getDbname();
		GlobalContext.DB_USER_NAME = dbCred.getUsername();
		GlobalContext.DB_PASSWORD = dbCred.getPassword();
		
		// Test the status and return
		boolean status = GlobalContext.getDataFetcher().testConnection();
		if(status)
			Processor.init();
		
		return status;
	}

	public static ArrayList<Company> getCompaniesToShow() {
		// Do filtering if we do not wish to show all companies
		return GlobalContext.getLocalStore().getCompanies();
	}

	/**
	 * A call of adding an offer from 
	 * @param company to 
	 * @param student of type
	 * @param offerstatus with
	 * @param offerrank if WL
	 */
	public static void processAddOffer(Company company, Student student,
			OfferStatus offerstatus, int offerrank) {
		company.getAgent().presentOffer(student, offerstatus, offerrank);
	}

	/**
	 * To remove offer of
	 * @param company
	 * @param offer
	 */
	public static void processDelOffer(Company company, JobOffer offer) {
		company.getAgent().revokeOffer(offer);
	}

	/**
	 * Main Algorithm start point.
	 * We call it five times, but it should be till it converge.
	 * TODO
	 */
	public static void processAllOffers() {
		int iter=5;
		while(iter-->0){
			Processor.process();
			System.out.println("AGAIN  "+iter);
		}
	}
}
