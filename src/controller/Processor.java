package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import api.bean.Company;
import api.bean.Preference;
import api.bean.Student;
import api.bean.offer.JobOffer;
import api.bean.offer.OfferStatus;
import api.context.GlobalContext;

/**
 * Main algorithm class
 * @author Punit_Ghodasara
 *
 */
public class Processor {
	
	private static Set<Student> offeredStudents;
	private static ArrayList<Company> companies;
	
	/**
	 * Initializing local store.
	 */
	public static void init(){
		
		// Step 1 :: Get all companies of a day
		companies = GlobalContext.getDataFetcher().fetchCompanies(GlobalContext.BATCH, GlobalContext.DAY, GlobalContext.SLOT);
		GlobalContext.getLocalStore().loadCompanies(companies);
		System.out.println("Data Fetch : Companies :"+companies.size());
		
		// Step 2 :: Get all students
		ArrayList<Student> students = GlobalContext.getDataFetcher().fetchStudents(GlobalContext.BATCH, GlobalContext.DAY, GlobalContext.SLOT);
		GlobalContext.getLocalStore().loadStudents(students);
		System.out.println("Data Fetch : Students :"+students.size());
		
		// Step 3 :: Get all preferences
		ArrayList<Preference> prefs = GlobalContext.getDataFetcher().fetchPreferences(GlobalContext.BATCH, GlobalContext.DAY, GlobalContext.SLOT);
		GlobalContext.getLocalStore().fillPreferences(prefs);
		System.out.println("Data Fetch : Prefs :"+prefs.size());
				
//		ActionProcessor.processAddOffer(companies.get(0), students.get(0), OfferStatus.ACTUAL_OFFER, 0);
//		ActionProcessor.processAddOffer(companies.get(0), students.get(1), OfferStatus.ACTUAL_OFFER, 0);
//		ActionProcessor.processAddOffer(companies.get(0), students.get(2), OfferStatus.WAITLIST_OFFER, 0);
//		ActionProcessor.processAddOffer(companies.get(1), students.get(2), OfferStatus.ACTUAL_OFFER, 0);
//		ActionProcessor.processAddOffer(companies.get(1), students.get(3), OfferStatus.ACTUAL_OFFER, 0);
//		ActionProcessor.processAddOffer(companies.get(1), students.get(4), OfferStatus.ACTUAL_OFFER, 0);
//		ActionProcessor.processAddOffer(companies.get(1), students.get(5), OfferStatus.WAITLIST_OFFER, 0);
//		ActionProcessor.processAddOffer(companies.get(2), students.get(3), OfferStatus.ACTUAL_OFFER, 0);
//		ActionProcessor.processAddOffer(companies.get(2), students.get(0), OfferStatus.WAITLIST_OFFER, 0);
		
		
		
	}
	
	/**
	 * Process based on algorithm :
	 * 
	 * For each students S,
	 * 		For each company C of S sorted by priority,
	 * 			if process status of S in company C
	 * 	Do this till it diverge
	 */
	public static boolean process(){
		
		int noOfHolds = 0;
		// Step 4 :: Get eligible students (who has a chance of job)
		offeredStudents = companies.stream()
							.flatMap(c->c.getAgent().getOffers().stream())
							.map(o->o.getStudent())
							.collect(Collectors.toSet());
		
		for(Student aStud : offeredStudents){
			List<Company> prefComp = null;
			System.out.println("\nProcessing : "+aStud.getRollno());
			// Step 3 :: If student has not filled preferences
			if(aStud.hasNoPreference()){
				prefComp = new ArrayList<>();
			}else{
				prefComp = aStud.getPreferences()
						.stream()
						.map(pref->pref.getCompany())
//						.filter(c->c.getAgent().hasOfferFor(aStud))
						.collect(Collectors.toList());
			}
			System.out.println("Preference : "+prefComp.size());
			// Step 3 (continue) :: if no preference added, add all companies (making sure they go at last in list)
			if(prefComp.size()==0){
				boolean extraPref = companies.stream().filter(c->c.getAgent().hasOfferFor(aStud)).findAny().isPresent();
				if(extraPref){
					prefComp.addAll(companies.stream().filter(c->c.getAgent().hasOfferFor(aStud)).collect(Collectors.toList()));
					System.out.println("Company ADDED for "+aStud);
				}
			}
			// Step 4 : 
			// 		if student is unplaced and get an actual job, student accepts it.
			//		if student is placed and get an actual job, student rejects it and uplift of other job happens.
			//		if student is unplaced and get a WLjob, we hold.
			//		if company has been sealed, we DO NOTHING
			
			System.out.println("Preference : "+prefComp.size());
			
			for(Company comp : prefComp){
				
				System.out.print("\n Company :"+comp.getCompanyName());
				
				if(!comp.getAgent().isLetterSent()){
					// Hey, Wait student is praying for company which is lazy
					noOfHolds++;
					break;
				}
				
				JobOffer matchedOffer = comp.getAgent().getOfferOf(aStud);
				if(matchedOffer==null)
					continue;
				OfferStatus status = matchedOffer.getCurrentStatus();
				
				if(!aStud.isPlaced() && status.equals(OfferStatus.ACTUAL_OFFER)){
					System.out.print("  ACCEPTED");
					aStud.accept(matchedOffer);
					matchedOffer.setAccepted();
				}else if(aStud.isPlaced() && status.equals(OfferStatus.ACTUAL_OFFER)){
					System.out.print("  PLACED");
					aStud.reject(matchedOffer);
					matchedOffer.setRejected();
				}else if(status.equals(OfferStatus.WAITLIST_OFFER)){
					System.out.print("  HOLD");
					noOfHolds++;
				}
			}
		}
		return noOfHolds==0;
	}

}
