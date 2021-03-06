package ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import org.controlsfx.dialog.Dialogs;

import api.context.GlobalContext;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import ui.bean.UICompanyBean;
import ui.bean.UIOfferBean;
import ui.comp.DialogUtil;
import ui.comp.NumberedCell;

/**
 * A controller page to load companies and feed offer letters.
 * It also processes the job.
 */
public class NewInfoController extends AnchorPane implements Initializable {

	@FXML ListView<UICompanyBean> compList;
	
	@FXML TableView<UIOfferBean> actTable;
	@FXML TableColumn<UIOfferBean, Integer> actIndCol;
	@FXML TableColumn<UIOfferBean, String> actRollCol;
	@FXML TableColumn<UIOfferBean, String> actNameCol;

	@FXML TableView<UIOfferBean> wlTable;
	@FXML TableColumn<UIOfferBean, Integer> wlIndCol;
	@FXML TableColumn<UIOfferBean, String> wlRollCol;
	@FXML TableColumn<UIOfferBean, String> wlNameCol;

	@FXML Label compNameLabel;
	@FXML Label jafNumLabel;
	
	@FXML Button editJobButton;
	@FXML Button lockButton;
	@FXML Button processJobButton;
	
	@FXML Label batch;
	@FXML Label day;
	@FXML Label slot;
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    	// Adding batch, day, slot
    	batch.setText(GlobalContext.BATCH);
    	day.setText(String.valueOf(GlobalContext.DAY));
    	slot.setText(String.valueOf(GlobalContext.SLOT));
    	
    	// binding tables
    	actTable.setItems(actualList);
    	actIndCol.setCellFactory(cell->new NumberedCell<>());
    	actNameCol.setCellValueFactory(cellData -> cellData.getValue().studentnameProperty());
    	actRollCol.setCellValueFactory(cellData -> cellData.getValue().rollnumberProperty());

    	wlTable.setItems(wlList);
    	wlIndCol.setCellFactory(cell->new NumberedCell<>());
    	wlNameCol.setCellValueFactory(cellData -> cellData.getValue().studentnameProperty());
    	wlRollCol.setCellValueFactory(cellData -> cellData.getValue().rollnumberProperty());

    	// binding buttons and labels
    	editJobButton.disableProperty().bind(compList.getSelectionModel().selectedItemProperty().isNull());
    	processJobButton.disableProperty().bind(compList.getSelectionModel().selectedItemProperty().isNull());
    	
    	compNameLabel.textProperty().bind(compList.getSelectionModel().selectedItemProperty().asString());
    	
    	// event listeners
    	editJobButton.setOnAction(e->{
    		UICompanyBean selComp = compList.getSelectionModel().getSelectedItem();
    		if(selComp==null)
    			return;
    		if(selComp.getCompany().getAgent().isLetterSent())
    			DialogUtil.showError("Error", "Company Offer Letter is locked. You can not modify now.");
    		else
    			UIProcessor.processCallEditOffer(selComp);
    	});
    	lockButton.setOnAction(e->{
    		UICompanyBean selComp = compList.getSelectionModel().getSelectedItem();
    		if(selComp!=null)
    			selComp.getCompany().getAgent().sendLetter();
    		if(selComp.getCompany().getAgent().isLetterSent())
    			DialogUtil.showInformation("Success", "Company Offer Letter is locked successfully.");
    	});
//    	lockButton.setOnAction(e->{
//    		companies.stream().forEach(comp->comp.getCompany().getAgent().sendLetter());
//    		UICompanyBean selComp = compList.getSelectionModel().getSelectedItem();
//    		if(selComp!=null)
//    			selComp.getCompany().getAgent().sendLetter();
//    		if(selComp.getCompany().getAgent().isLetterSent())
//    			DialogUtil.showInformation("Success", "Company Offer Letter is locked successfully.");
//    	});
    	processJobButton.setOnAction(e->{
    		System.out.println("Process Job called.");
    		long lockedComp = companies.stream().filter(uc->uc.getCompany()
    						.getAgent()
    						.isLetterSent()).count();
    		if(lockedComp!=companies.size()) {
				boolean showError = DialogUtil.showConfirm("Warning", "Only "+lockedComp+" has been locked. Do you want to proceed?");
				if(showError)
					UIProcessor.processAllData();
			}else{
				UIProcessor.processAllData();
			}
    	});
    }

    ArrayList<UICompanyBean> companies = new ArrayList<>();
    public void setCompanies(ArrayList<UICompanyBean> companies){
    	// Adding companies
    	this.companies = companies;
    	companies.forEach(comp->{
    		compList.getItems().add(comp);
    	});
   		compList.getSelectionModel().selectFirst();
    	refreshData(compList.getSelectionModel().getSelectedItem());
    	
    	// showing offers on company selection 
    	compList.getSelectionModel().selectedItemProperty().addListener((e,oldComp,newComp)->{
    		if(newComp!=null)
    			refreshData(newComp);
    	});
    }
    
    private ObservableList<UIOfferBean> actualList = FXCollections.observableArrayList();
    private ObservableList<UIOfferBean> wlList = FXCollections.observableArrayList();
	
    /**
     * showing offers based on selected company
     * @param comp
     */
    private void refreshData(UICompanyBean comp) {
    	actualList.clear();
    	wlList.clear();
    	
    	if(comp==null)
    		return;
    	
    	actualList.addAll(comp.getActualOffers());
    	wlList.addAll(comp.getWLOffers());
	}
}
