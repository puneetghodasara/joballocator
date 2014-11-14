package ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;
import ui.bean.UICompanyBean;
import ui.bean.UIOfferBean;
import ui.comp.NumberedCell;
import api.bean.offer.OfferStatus;

/**
 * A controller to feed the jobs by a company.
 */
public class JobOfferController extends AnchorPane implements Initializable {

	private UICompanyBean company;

	@FXML TableView<UIOfferBean> actTable;
	@FXML TableColumn<UIOfferBean, Integer> actIndCol;
	@FXML TableColumn<UIOfferBean, String> actRollCol;
	@FXML TableColumn<UIOfferBean, String> actNameCol;
	@FXML TableColumn<UIOfferBean, Button> actActCol;
	
	@FXML TableView<UIOfferBean> wlTable;
	@FXML TableColumn<UIOfferBean, Integer> wlIndCol;
	@FXML TableColumn<UIOfferBean, String> wlRollCol;
	@FXML TableColumn<UIOfferBean, String> wlNameCol;
	@FXML TableColumn<UIOfferBean, Button> wlActCol;
	
	@FXML Label compname; 
	@FXML Label jafno;
	
	
	@FXML TextField actTextBox;
	@FXML Button actAdd;
	@FXML TextField wlTextBox;
	@FXML Button wlAdd;
	
	@FXML Label totalAct;
	@FXML Label totalWl;

	@FXML Button processButton;
	
	private ObservableList<UIOfferBean> actOffers = FXCollections.observableArrayList();
	private ObservableList<UIOfferBean> wlOffers = FXCollections.observableArrayList();
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	// Offer tables
    	actTable.setItems(actOffers);
    	actIndCol.setCellFactory(cell->new NumberedCell<>());
    	actRollCol.setCellValueFactory(cellData -> cellData.getValue().rollnumberProperty());
    	actNameCol.setCellValueFactory(cellData -> cellData.getValue().studentnameProperty());
    	actActCol.setCellValueFactory(cellData->cellData.getValue().actionProperty());
    	
    	wlTable.setItems(wlOffers);
    	wlIndCol.setCellFactory(cell->new NumberedCell<>());
    	wlRollCol.setCellValueFactory(cellData -> cellData.getValue().rollnumberProperty());
    	wlNameCol.setCellValueFactory(cellData -> cellData.getValue().studentnameProperty());
    	wlActCol.setCellValueFactory(cellData->cellData.getValue().actionProperty());
    	
    	// Event listeners
    	actAdd.setOnAction(e->{
    		String roll = actTextBox.getText();
			UIProcessor.processAddOffer(company,roll,OfferStatus.ACTUAL_OFFER,actOffers.size()+1);
    		actTextBox.clear();
    	});
    	wlAdd.setOnAction(e->{
    		String roll = wlTextBox.getText();
			UIProcessor.processAddOffer(company,roll,OfferStatus.WAITLIST_OFFER,wlOffers.size()+1);
    	});

    	processButton.setOnAction(e->{
    		UIProcessor.processCloseOffers();
    	});
    	
    }
    

	/**
	 * Set the controller for company c
	 * fetch pre-added jobs
	 * @param selComp
	 */
	public void setCompany(UICompanyBean selComp) {
		this.company = selComp;
		compname.textProperty().unbind();
		compname.textProperty().bind(company.companyNameProperty());
		jafno.textProperty().unbind();
		jafno.textProperty().bind(company.jafNoProperty());
		actOffers.clear();
		actOffers.addAll(company.getActualOffers());
		wlOffers.clear();
		wlOffers.addAll(company.getWLOffers());
	}

}
