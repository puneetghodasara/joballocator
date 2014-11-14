package report;

import java.lang.reflect.Field;
import java.util.List;

import api.bean.Company;
import api.bean.offer.JobOffer;
import api.bean.offer.Offer;
import api.report.TableReportFormat;
import javafx.beans.property.SimpleStringProperty;

/**
 * A concrete class of a Table Row for Company Report
 * @author Punit_Ghodasara
 *
 */
public class CompanyReportFormat implements TableReportFormat{

	private SimpleStringProperty companyName;
	private SimpleStringProperty jafno;
	private SimpleStringProperty offeredRollno;
	private SimpleStringProperty offeredAs;
	private SimpleStringProperty offerRank;
	private SimpleStringProperty offerFinalStatus;
	
	
	public CompanyReportFormat(JobOffer offer) {
		this.companyName = new SimpleStringProperty(offer.getCompany().getCompanyName());
		this.jafno = new SimpleStringProperty(String.valueOf(offer.getCompany().getJafNo()));
		this.offeredRollno = new SimpleStringProperty(offer.getStudent().getRollno());
		this.offeredAs = new SimpleStringProperty(offer.getInitStatus().toString());
		this.offerRank = new SimpleStringProperty(String.valueOf(offer.getRank()));
		this.offerFinalStatus = new SimpleStringProperty(offer.getCurrentStatus().toString());
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
				if(i<declaredFields.length)
					return (SimpleStringProperty) declaredFields[i].get(new SimpleStringProperty());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			return new SimpleStringProperty();
	}

}
