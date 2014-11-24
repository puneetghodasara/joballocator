package ui.bean;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;

public class UIRepCompanyLetter extends TreeTableView<UIOfferBean>{

	
	private TreeTableColumn<UIOfferBean, String> compNameColumn = new TreeTableColumn<>("Compnay Name");
	private TreeTableColumn<UIOfferBean, String> jafNoColumn = new TreeTableColumn<>("JAF");
	private TreeTableColumn<UIOfferBean, String> rollNumberColumn = new TreeTableColumn<>("Roll No");
	private TreeTableColumn<UIOfferBean, String> initStatusColumn = new TreeTableColumn<>("Initial");
	private TreeTableColumn<UIOfferBean, String> finalStatusColumn = new TreeTableColumn<>("Final");
	
	public UIRepCompanyLetter(UICompanyBean comp){
		
		TreeItem<UIOfferBean> compRoot = new TreeItem<>(new UIOfferBean());
		comp.getCompany().getAgent().getOffers().stream().forEach(offer->
    		compRoot.getChildren().add(new TreeItem<>(new UIOfferBean(offer)))
    	);
		
		compNameColumn.setCellValueFactory(cellData->cellData.getValue().getValue().compnameProperty());
    	jafNoColumn.setCellValueFactory(cellData->cellData.getValue().getValue().jafnoProperty());
    	rollNumberColumn.setCellValueFactory(cellData->cellData.getValue().getValue().rollnumberProperty());
    	initStatusColumn.setCellValueFactory(cellData->cellData.getValue().getValue().initialStatusProperty());
    	finalStatusColumn.setCellValueFactory(cellData->cellData.getValue().getValue().finalStatusProperty());
		
		this.getColumns().add(compNameColumn);
		this.getColumns().add(jafNoColumn);
		this.getColumns().add(rollNumberColumn);
		this.getColumns().add(initStatusColumn);
		this.getColumns().add(finalStatusColumn);
		
		AnchorPane.setTopAnchor(this,0d);
		AnchorPane.setLeftAnchor(this,0d);
		AnchorPane.setRightAnchor(this,0d);
		AnchorPane.setBottomAnchor(this,0d);
		
		compRoot.setExpanded(true);
		this.setRoot(compRoot);
	}
}
