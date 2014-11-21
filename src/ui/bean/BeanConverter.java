package ui.bean;

import java.util.ArrayList;

import api.bean.Company;
import api.bean.Student;
import api.credential.DBCredential;
import api.credential.LoginCredential;

/**
 * Converter from UIbeans to Actual Beans
 * @author Punit_Ghodasara
 *
 */
public class BeanConverter {

	public static LoginCredential convert(UILoginCredential uiloginbean){
		return new LoginCredential(uiloginbean.getUsername(), uiloginbean.getPassword());
	}

	public static DBCredential convert(UIDBLoginCredential uidbLoginBean) {
		return new DBCredential(uidbLoginBean);
	}

	public static ArrayList<UICompanyBean> convert(ArrayList<Company> compList) {
		ArrayList<UICompanyBean> uiCompList = new ArrayList<>();
		compList.forEach(comp->{
			uiCompList.add(new UICompanyBean(comp));
		});
		return uiCompList;
	}

	public static ArrayList<UIStudentBean> convertStudentList(ArrayList<Student> studList) {
		ArrayList<UIStudentBean> uiStudList = new ArrayList<>();
		studList.forEach(stud->{
			uiStudList.add(new UIStudentBean(stud));
		});
		return uiStudList;
	}

}
