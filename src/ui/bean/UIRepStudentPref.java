package ui.bean;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import api.bean.Student;

public class UIRepStudentPref extends TableView<UIPrefBean>{

	
	private TableColumn<UIPrefBean, String> compNameColumn = new TableColumn<>("Compnay Name");
	private TableColumn<UIPrefBean, String> jafNoColumn = new TableColumn<>("JAF");
	
	private TableColumn<UIPrefBean, String> rollNumberColumn = new TableColumn<>("Roll No");
	private TableColumn<UIPrefBean, String> nameColumn = new TableColumn<>("Name");
	
	private TableColumn<UIPrefBean, String> rankColumn = new TableColumn<>("Rank");
	
	
	public UIRepStudentPref(UIStudentBean newStud){

		List<UIPrefBean> uiPrefs = newStud.getStudent().getPreferences()
										.stream()
										.map(pref->new UIPrefBean(pref))
										.collect(Collectors.toList());
		this.setItems(FXCollections.observableArrayList(uiPrefs));
		
		compNameColumn.setCellValueFactory(cellData->cellData.getValue().getCompNameColumn());
    	jafNoColumn.setCellValueFactory(cellData->cellData.getValue().getJafNoColumn());
    	rollNumberColumn.setCellValueFactory(cellData->cellData.getValue().getRollNoColumn());
    	nameColumn.setCellValueFactory(cellData->cellData.getValue().getStudNameColumn());
    	rankColumn.setCellValueFactory(cellData->cellData.getValue().getRankColumn());
		
		this.getColumns().add(rollNumberColumn);
		this.getColumns().add(nameColumn);
		this.getColumns().add(compNameColumn);
		this.getColumns().add(jafNoColumn);
		this.getColumns().add(rankColumn);
		
		AnchorPane.setTopAnchor(this,0d);
		AnchorPane.setLeftAnchor(this,0d);
		AnchorPane.setRightAnchor(this,0d);
		AnchorPane.setBottomAnchor(this,0d);
		
	}
}
