package report;

import java.lang.reflect.Field;
import java.util.List;

import api.bean.Company;
import api.bean.Preference;
import api.bean.Student;
import api.bean.offer.JobOffer;
import api.bean.offer.Offer;
import api.report.TableReportFormat;
import javafx.beans.property.SimpleStringProperty;

/**
 * A concrete class of Table Row for Student Report
 * @author Punit_Ghodasara
 *
 */
public class StudentPrefFormat implements TableReportFormat{

	private SimpleStringProperty rollno;
	private SimpleStringProperty prefrank;
	private SimpleStringProperty companyName;
	private SimpleStringProperty jafno;
	
	
	public StudentPrefFormat(Preference pref) {
		this.rollno = new SimpleStringProperty(pref.getOwner().getRollno());
		this.jafno = new SimpleStringProperty(String.valueOf(pref.getCompany().getJafNo()));
		this.companyName = new SimpleStringProperty(pref.getCompany().getCompanyName());
		this.prefrank = new SimpleStringProperty(String.valueOf(pref.getRank()));
	}

	@Override
	public String getColumnHeader(int i) {
		Field[] declaredFields = getClass().getDeclaredFields();
		if(i>declaredFields.length)
			return "N.A.";
		else
			return declaredFields[i].getName();
	}

	@Override
	public SimpleStringProperty getColumnBinding(int i) {
			try {
				Field[] declaredFields = getClass().getDeclaredFields();
				
				if(i<declaredFields.length){
					return (SimpleStringProperty) declaredFields[i].get(this);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			return new SimpleStringProperty();
	}

}
