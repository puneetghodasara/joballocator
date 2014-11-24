package ui.bean;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import ui.UIProcessor;
import api.bean.offer.JobOffer;

public class UIOfferBean {

	private UICompanyBean uiCompBean;
	private JobOffer offer;

	private StringProperty compname = new SimpleStringProperty();
	private StringProperty jafno = new SimpleStringProperty();
	private StringProperty rollnumber = new SimpleStringProperty();
	private StringProperty studentname = new SimpleStringProperty();
	private StringProperty initialStatus = new SimpleStringProperty();
	private StringProperty finalStatus = new SimpleStringProperty();
	private SimpleObjectProperty<Button> action;
	
	public UIOfferBean(JobOffer jobOffer) {
		super();
		this.offer = jobOffer;
		this.uiCompBean = new UICompanyBean(jobOffer.getCompany());
		this.compname = new SimpleStringProperty(jobOffer.getCompany().getCompanyName());
		this.jafno = new SimpleStringProperty(String.valueOf(jobOffer.getCompany().getJafNo()));
		this.rollnumber = new SimpleStringProperty(jobOffer.getStudent().getRollno());
		this.studentname = new SimpleStringProperty(jobOffer.getStudent().getName());
		this.initialStatus = new SimpleStringProperty(jobOffer.getInitStatus().toString());
		this.finalStatus = new SimpleStringProperty(jobOffer.getCurrentStatus().toString());
		Button b = new Button("X");
		b.setOnAction(e->{
			System.out.println("CALLED");
			UIProcessor.processRemOffer(uiCompBean,this);
		});
		this.action = new SimpleObjectProperty<>(b);
	}

	public UIOfferBean() {
		// TODO Auto-generated constructor stub
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
	public final StringProperty studentnameProperty() {
		return this.studentname;
	}
	public final StringProperty initialStatusProperty() {
		return this.initialStatus;
	}
	public final StringProperty finalStatusProperty() {
		return this.finalStatus;
	}
	public SimpleObjectProperty<Button> actionProperty(){
		return this.action;
	}

	public JobOffer getOffer() {
		return offer;
	}

}
