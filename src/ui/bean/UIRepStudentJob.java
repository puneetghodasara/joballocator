package ui.bean;

import java.util.List;
import java.util.stream.Collectors;

import api.bean.offer.JobOffer;
import api.bean.offer.OfferStatus;
import api.context.GlobalContext;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;

public class UIRepStudentJob extends TableView<UIOfferBean>{

	
	private TableColumn<UIOfferBean, String> compNameColumn = new TableColumn<>("Compnay Name");
	private TableColumn<UIOfferBean, String> jafNoColumn = new TableColumn<>("JAF");
	private TableColumn<UIOfferBean, String> rollNumberColumn = new TableColumn<>("Roll No");
	private TableColumn<UIOfferBean, String> studNameColumn = new TableColumn<>("Name");
	private TableColumn<UIOfferBean, String> initStatusColumn = new TableColumn<>("Initial");
	private TableColumn<UIOfferBean, String> finalStatusColumn = new TableColumn<>("Final");
	
	public UIRepStudentJob(){
		
		List<UIOfferBean> acceptedUIOffers = GlobalContext.getLocalStore().getCompanies()
								.stream().flatMap(comp->comp.getAgent().getOffers().stream())
								.filter(offer->offer.getCurrentStatus().equals(OfferStatus.ACCEPTED))
								.sorted()
								.map(offer->new UIOfferBean(offer))
								.collect(Collectors.toList());
		
		this.setItems(FXCollections.observableArrayList(acceptedUIOffers));
		
		compNameColumn.setCellValueFactory(cellData->cellData.getValue().compnameProperty());
    	jafNoColumn.setCellValueFactory(cellData->cellData.getValue().jafnoProperty());
    	rollNumberColumn.setCellValueFactory(cellData->cellData.getValue().rollnumberProperty());
    	studNameColumn.setCellValueFactory(cellData->cellData.getValue().studentnameProperty());
    	initStatusColumn.setCellValueFactory(cellData->cellData.getValue().initialStatusProperty());
    	finalStatusColumn.setCellValueFactory(cellData->cellData.getValue().finalStatusProperty());
		
		this.getColumns().add(compNameColumn);
		this.getColumns().add(jafNoColumn);
		this.getColumns().add(rollNumberColumn);
		this.getColumns().add(studNameColumn);
		this.getColumns().add(initStatusColumn);
		this.getColumns().add(finalStatusColumn);
		
		AnchorPane.setTopAnchor(this,0d);
		AnchorPane.setLeftAnchor(this,0d);
		AnchorPane.setRightAnchor(this,0d);
		AnchorPane.setBottomAnchor(this,0d);
		
	}
}
