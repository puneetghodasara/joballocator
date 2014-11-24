package ui.bean;

import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import api.bean.Company;
import api.bean.offer.OfferStatus;

public class UICompanyBean implements Comparable<UICompanyBean> {

	private Company comp;
	
	public UICompanyBean(Company comp){
		this.comp = comp;
	}
		
	public StringProperty getDisplayNameProperty(){
		return new SimpleStringProperty(comp.getCompanyName() + " : " + comp.getJafNo());
	}
	
	public List<UIOfferBean> getActualOffers(){
		return comp.getAgent().getOffers()
				.stream()
				.filter(o->o.getCurrentStatus().equals(OfferStatus.ACTUAL_OFFER))
				.map(o->new UIOfferBean(o))
				.collect(Collectors.toList());
				
	}
	public List<UIOfferBean> getWLOffers(){
		return comp.getAgent().getOffers()
				.stream()
				.filter(o->o.getCurrentStatus().equals(OfferStatus.WAITLIST_OFFER))
				.map(o->new UIOfferBean(o))
				.collect(Collectors.toList());
				
	}
	public List<UIOfferBean> getAcceptedOffers(){
		return comp.getAgent().getOffers()
				.stream()
				.filter(o->o.getCurrentStatus().equals(OfferStatus.ACCEPTED))
				.map(o->new UIOfferBean(o))
				.collect(Collectors.toList());
				
	}
	public List<UIOfferBean> getAllOffers(){
		return comp.getAgent().getOffers()
				.stream()
				.map(o->new UIOfferBean(o))
				.collect(Collectors.toList());
				
	}
	
	public StringProperty jafNoProperty() {
		return new SimpleStringProperty(String.valueOf(comp.getJafNo()));
	}
	public StringProperty companyNameProperty() {
		return new SimpleStringProperty(comp.getCompanyName());
	}

	public Company getCompany() {
		return comp;
	}

	@Override
	public int compareTo(UICompanyBean arg0) {
		return this.comp.compareTo(arg0.comp);
	}

	@Override
	public String toString() {
		return getDisplayNameProperty().getValue();
	}
}
