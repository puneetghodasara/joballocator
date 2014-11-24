package ui;

import java.util.ArrayList;

import ui.bean.BeanConverter;
import ui.bean.UICompanyBean;
import ui.bean.UIDBLoginCredential;
import ui.bean.UILoginCredential;
import ui.bean.UIOfferBean;
import ui.bean.UIPrefBean;
import ui.bean.UIStudentBean;
import api.bean.Company;
import api.bean.Student;
import api.bean.offer.OfferStatus;
import api.context.GlobalContext;
import api.credential.DBCredential;
import api.credential.LoginCredential;
import controller.ActionProcessor;
import controller.Processor;

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

	public static void processAddOffer(UICompanyBean comp, UIStudentBean selectedItem, OfferStatus offerstatus, int offerrank) {
		Company company = comp.getCompany();
		Student student = GlobalContext.getLocalStore().searchStudent(selectedItem.getStudent().getRollno());
		ActionProcessor.processAddOffer(company,student,offerstatus,offerrank);
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
	
	//TODO remove DEBUG METHOD
	public static void entry(){
		Processor.init();
		processAllData();
	}
	
	public static void exit() {
		Main.exit();
	}
}
