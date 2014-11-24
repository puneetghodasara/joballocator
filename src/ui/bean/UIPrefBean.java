package ui.bean;

import api.bean.Preference;
import javafx.beans.property.SimpleStringProperty;


public class UIPrefBean {

	
	private SimpleStringProperty rollNoColumn = new SimpleStringProperty();
	private SimpleStringProperty studNameColumn = new SimpleStringProperty();
	
	private SimpleStringProperty compNameColumn = new SimpleStringProperty();
	private SimpleStringProperty jafNoColumn = new SimpleStringProperty();
	
	private SimpleStringProperty rankColumn = new SimpleStringProperty();

	private Preference pref;
	
	public UIPrefBean(Preference pref) {
		super();
		this.pref = pref;
		rollNoColumn = new SimpleStringProperty(pref.getOwner().getRollno());
		studNameColumn = new SimpleStringProperty(pref.getOwner().getName());
		compNameColumn = new SimpleStringProperty(pref.getCompany().getCompanyName());
		jafNoColumn = new SimpleStringProperty(String.valueOf(pref.getCompany().getJafNo()));
		rankColumn = new SimpleStringProperty(String.valueOf(pref.getRank()));
	}

	public SimpleStringProperty getRollNoColumn() {
		return rollNoColumn;
	}
	public SimpleStringProperty getStudNameColumn() {
		return studNameColumn;
	}
	public SimpleStringProperty getCompNameColumn() {
		return compNameColumn;
	}
	public SimpleStringProperty getJafNoColumn() {
		return jafNoColumn;
	}
	public SimpleStringProperty getRankColumn() {
		return rankColumn;
	}

	public Preference getPref() {
		return pref;
	}
	
	
}
