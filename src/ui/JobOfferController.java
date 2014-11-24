package ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import org.controlsfx.control.textfield.TextFields;

import store.Repository;
import ui.bean.BeanConverter;
import ui.bean.UICompanyBean;
import ui.bean.UIOfferBean;
import ui.bean.UIPrefBean;
import ui.bean.UIStudentBean;
import ui.comp.FilterableComboBox;
import ui.comp.NumberedCell;
import api.bean.Student;
import api.bean.offer.OfferStatus;
import api.context.GlobalContext;

/**
 * A controller to feed the jobs by a company.
 */
public class JobOfferController extends AnchorPane implements Initializable {

	private static final int MIN_CHAR = 3;

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
	
	@FXML ComboBox<UIStudentBean> actTextBox;
	@FXML ComboBox<UIStudentBean> wlTextBox;
	
	@FXML Button actAdd;
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
    		if(actTextBox.getSelectionModel().getSelectedIndex()!=-1){
    			UIStudentBean selectedItem = actTextBox.getSelectionModel().getSelectedItem();
	    		if(selectedItem!=null){
	    			UIProcessor.processAddOffer(company,selectedItem,OfferStatus.ACTUAL_OFFER,actOffers.size()+1);
	    		}	
			}
    		actTextBox.getSelectionModel().clearSelection();
    		actTextBox.getEditor().clear();
    	});
    	wlAdd.setOnAction(e->{
    		if(wlTextBox.getSelectionModel().getSelectedIndex()!=-1){
    			UIStudentBean selectedItem = wlTextBox.getSelectionModel().getSelectedItem();
	    		if(selectedItem!=null){
	    			UIProcessor.processAddOffer(company,selectedItem,OfferStatus.WAITLIST_OFFER,wlOffers.size()+1);
	    		}	
			}
    		wlTextBox.getSelectionModel().clearSelection();
    		wlTextBox.getEditor().clear();
    	});

    	processButton.setOnAction(e->{
    		UIProcessor.processCloseOffers();
    	});
    	
    	actTextBox.setItems(allNames);
    	actTextBox.setEditable(true);
    	actTextBox.getEditor().setOnKeyTyped(e->{
    		actTextBox.getSelectionModel().clearSelection();
    		// TODO It is clearing on focus as well
    		// Make sure it doesn't
    	});
    	
    	actTextBox.getEditor().textProperty().addListener((items, oldVal, newVal)->{

			if(actTextBox.getSelectionModel().getSelectedIndex()!=-1){
				UIStudentBean selectedItem = actTextBox.getSelectionModel().getSelectedItem();
	    		if(newVal!=null && selectedItem!=null &&
	    				newVal.equals(selectedItem.toString())){
	    			return;
	    		}	
			}
			
    		String text = actTextBox.getEditor().getText();
    		actTextBox.show();
    		if(text.length()<MIN_CHAR)
    			return;
    		actTextBox.getItems().clear();
    		actTextBox.getItems().addAll(filterStudent(text));
    		return;
    	});

    	wlTextBox.setItems(allNames);
    	wlTextBox.setEditable(true);
    	wlTextBox.getEditor().setOnKeyTyped(e->{
    		wlTextBox.getSelectionModel().clearSelection();
    		// TODO It is clearing on focus as well
    		// Make sure it doesn't
    	});
    	
    	wlTextBox.getEditor().textProperty().addListener((items, oldVal, newVal)->{

			if(wlTextBox.getSelectionModel().getSelectedIndex()!=-1){
				UIStudentBean selectedItem = wlTextBox.getSelectionModel().getSelectedItem();
	    		if(newVal!=null && selectedItem!=null &&
	    				newVal.equals(selectedItem.toString())){
	    			return;
	    		}	
			}
			
    		String text = wlTextBox.getEditor().getText();
    		wlTextBox.show();
    		if(text.length()<MIN_CHAR)
    			return;
    		wlTextBox.getItems().clear();
    		wlTextBox.getItems().addAll(filterStudent(text));
    		return;
    	});

    	
    }
    
    ObservableList<UIStudentBean> allNames = FXCollections.observableArrayList();
    private Collection<UIStudentBean> filterStudent(String userText){
    	ArrayList<Student> students = GlobalContext.getLocalStore().getStudents();
		ArrayList<UIStudentBean> uiStudentList = BeanConverter.convertStudentList(students);
    	return uiStudentList.stream()
    			.filter(s->(s.getStudent().getName().contains(userText) || s.getStudent().getRollno().contains(userText)))
    			.collect(Collectors.toList());
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
