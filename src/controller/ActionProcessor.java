package controller;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.controlsfx.dialog.Dialogs;

import javafx.application.Platform;
import ui.comp.DialogUtil;
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
		
		GlobalContext.BATCH = dbCred.getBatch();
		GlobalContext.DAY = dbCred.getDay();
		GlobalContext.SLOT = dbCred.getSlot();
		
		// Test the status and return
		boolean status = GlobalContext.getDataFetcher().testConnection();
		if(status){
			InitProcessor init = new InitProcessor();
			Dialogs.create().title("Processing").masthead("Fetching Data").showWorkerProgress(init);					

			GlobalContext.es.submit(init);
			try {
				init.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
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
	 * @return 
	 */
	public static void processAddOffer(Company company, Student student,
			OfferStatus offerstatus, int offerrank, boolean isFetching) {
		JobOffer presentOffer = company.getAgent().presentOffer(student, offerstatus, offerrank);
		if(!isFetching)
			GlobalContext.getDataFetcher().pushOffer(presentOffer);
	}

	/**
	 * To remove offer of
	 * @param company
	 * @param offer
	 */
	public static void processDelOffer(Company company, JobOffer offer) {
		company.getAgent().revokeOffer(offer);
		GlobalContext.getDataFetcher().delOffer(offer);
	}

	/**
	 * Main Algorithm start point.
	 * We call it five times, but it should be till it converge.
	 * TODO
	 */
	public static void processAllOffers() {
		int iter=10;
		while(iter-->0){
			boolean finished = Processor.process();
			if(finished)
				break;
			System.out.println("AGAIN  "+iter);
		}
		System.out.println("I :"+iter);
	}
}
