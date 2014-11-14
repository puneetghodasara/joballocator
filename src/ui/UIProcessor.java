package ui;

import java.util.ArrayList;

import ui.bean.BeanConverter;
import ui.bean.UICompanyBean;
import ui.bean.UIDBLoginCredential;
import ui.bean.UILoginCredential;
import ui.bean.UIOfferBean;
import api.bean.Company;
import api.bean.Student;
import api.bean.offer.OfferStatus;
import api.context.GlobalContext;
import api.credential.DBCredential;
import api.credential.LoginCredential;
import controller.ActionProcessor;

public class UIProcessor {

	public static void processLoginPage(UILoginCredential uiLoginBean){
		LoginCredential loginCred = BeanConverter.convert(uiLoginBean);
		boolean validLogin = GlobalContext.getAuthenticator().isValidLogin(loginCred);
		if(validLogin)
			Main.gotoDBLogin(null, false);
		else{
			Main.showError("Login Error",GlobalContext.LOGIN_ATTEMPT_FAILURE);
//			Main.gotoLogin(GlobalContext.LOGIN_ATTEMPT_FAILURE, true);
		}
	}
	
	public static void processDBPage(UIDBLoginCredential uidbLoginBean){
		DBCredential dbCred = BeanConverter.convert(uidbLoginBean);
		
		boolean validCred = ActionProcessor.process(dbCred);
		if(!validCred){
			Main.showError("DB Login Error", GlobalContext.DATA_ATTEMPT_FAILURE);
//			Main.gotoDBLogin(GlobalContext.DATA_ATTEMPT_FAILURE, true);
			return;
		}
		
		ArrayList<Company> compList = ActionProcessor.getCompaniesToShow();
		ArrayList<UICompanyBean> uiCompList = BeanConverter.convert(compList);
		Main.gotoAfterDB(uiCompList, false);
	}

	public static void processAddOffer(UICompanyBean comp, String roll, OfferStatus offerstatus, int offerrank) {
		Student student = GlobalContext.getLocalStore().searchStudent(roll);
		if(student!=null){
			Company company = comp.getCompany();
			ActionProcessor.processAddOffer(company,student,offerstatus,offerrank);
		}
		Main.showOfferPage(comp, true);
	}

	public static void processCallEditOffer(UICompanyBean selComp) {
		Main.showOfferPage(selComp, false);
	}

	public static void processCloseOffers() {
		ArrayList<Company> compList = ActionProcessor.getCompaniesToShow();
		ArrayList<UICompanyBean> uiCompList = BeanConverter.convert(compList);
		Main.gotoAfterDB(uiCompList, false);
	}

	public static void processRemOffer(UICompanyBean uiCompanybean, UIOfferBean uijoboffer) {
		ActionProcessor.processDelOffer(uiCompanybean.getCompany(),uijoboffer.getOffer());
		Main.showOfferPage(uiCompanybean, true);
	}

	public static void processAllData() {
		ActionProcessor.processAllOffers();
		ArrayList<Company> compList = ActionProcessor.getCompaniesToShow();
		ArrayList<UICompanyBean> uiCompList = BeanConverter.convert(compList);
		Main.gotoJobReport(uiCompList);
	}

	public static void exit() {
		Main.exit();
	}
}
