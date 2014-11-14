package ui.bean;

import api.bean.offer.JobOffer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class JobOfferUI {

	private StringProperty compname = new SimpleStringProperty();
	private StringProperty jafno = new SimpleStringProperty();
	private StringProperty rollnumber = new SimpleStringProperty();
	private StringProperty initialStatus = new SimpleStringProperty();
	private StringProperty finalStatus = new SimpleStringProperty();
	
	public JobOfferUI() {
		compname = new SimpleStringProperty("Offers");
	}
	
	public JobOfferUI(UICompanyBean comp) {
		compname = comp.getDisplayNameProperty();
		int a = comp.getCompany().getAgent().getOffers().size();
		rollnumber = new SimpleStringProperty(String.valueOf(a));
//		setRollnumber(String.valueOf(comp.getActualOffers().size()+comp.getWLOffers().size()));
	}
	
	public JobOfferUI(UICompanyBean c, UIOfferBean o) {
		compname = c.companyNameProperty();
		jafno = c.jafNoProperty();
		rollnumber = o.rollnumberProperty();
		initialStatus = o.initialStatusProperty();
		finalStatus = o.finalStatusProperty();
	}

	public final StringProperty compnameProperty() {
		return this.compname;
	}
	public final StringProperty jafnoProperty() {
		return this.jafno;
	}
	public final StringProperty rollnumberProperty() {
		return this.rollnumber;
	}
	public final StringProperty initialStatusProperty() {
		return this.initialStatus;
	}
	public final StringProperty finalStatusProperty() {
		return this.finalStatus;
	}

}
