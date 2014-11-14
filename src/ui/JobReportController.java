/*
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package ui;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import store.Repository;
import ui.bean.JobOfferUI;
import ui.bean.UICompanyBean;
import ui.bean.UIOfferBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;

/**
 * Login Controller.
 */
public class JobReportController extends AnchorPane implements Initializable {


//	@FXML
//	Button load;
	@FXML
	Button close;
	@FXML
	Button print;
	
	@FXML Tab fullReportTab;
	@FXML TreeTableView<JobOfferUI> offerTable;
	
	@FXML Tab compTab;
	@FXML ListView<UICompanyBean> companySelectList;
	@FXML TreeTableView<JobOfferUI> companywiseOfferTable;
	
	@FXML Tab studentTab;
	@FXML TreeTableView<JobOfferUI> studentwiseOfferTable;
	
	TreeTableColumn<JobOfferUI, String> compNameColumn = new TreeTableColumn<>("Compnay Name");
	TreeTableColumn<JobOfferUI, String> jafNoColumn = new TreeTableColumn<>("JAF");
	TreeTableColumn<JobOfferUI, String> rollNumberColumn = new TreeTableColumn<>("Roll No");
	TreeTableColumn<JobOfferUI, String> initStatusColumn = new TreeTableColumn<>("Initial");
	TreeTableColumn<JobOfferUI, String> finalStatusColumn = new TreeTableColumn<>("Final");
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	close.setOnAction(e->{
    		UIProcessor.exit();
    	});
    	
    	fullReportTab.setOnSelectionChanged(e->{
    		prepareFullReportView();
    	});
    	compTab.setOnSelectionChanged(e->{
    		prepareCompanyReportView(companySelectList.getSelectionModel().getSelectedItem());
    	});
    	companySelectList.getSelectionModel().selectedItemProperty().addListener((e,oldComp,newComp)->{
    		if(newComp!=null)
    			prepareCompanyReportView(newComp);
    	});
    	studentTab.setOnSelectionChanged(e->{
    		prepareStudentReportView();
    	});
    	
    	compNameColumn.setCellValueFactory(cellData->cellData.getValue().getValue().compnameProperty());
    	jafNoColumn.setCellValueFactory(cellData->cellData.getValue().getValue().jafnoProperty());
    	rollNumberColumn.setCellValueFactory(cellData->cellData.getValue().getValue().rollnumberProperty());
    	initStatusColumn.setCellValueFactory(cellData->cellData.getValue().getValue().initialStatusProperty());
    	finalStatusColumn.setCellValueFactory(cellData->cellData.getValue().getValue().finalStatusProperty());
	
    	prepareFullReportView();
    }

    private ArrayList<UICompanyBean> uiCompList;
	public void setCompanies(ArrayList<UICompanyBean> uiCompList) {
		this.uiCompList = uiCompList;

		companySelectList.getItems().clear();
		companySelectList.getItems().addAll(uiCompList);
		
		prepareStudentReportView();
		prepareFullReportView();
		
		companySelectList.getSelectionModel().selectFirst();
		prepareCompanyReportView(companySelectList.getSelectionModel().getSelectedItem());
	}
	
	private void prepareFullReportView(){
		clearColumns();
		
		offerTable.getColumns().add(compNameColumn);
		offerTable.getColumns().add(jafNoColumn);
		offerTable.getColumns().add(rollNumberColumn);
		offerTable.getColumns().add(initStatusColumn);
		offerTable.getColumns().add(finalStatusColumn);
		
		TreeItem<JobOfferUI> root = new TreeItem<>(new JobOfferUI());
		uiCompList.stream()
		.sorted()
		.forEach(comp->{
    		TreeItem<JobOfferUI> compRoot = new TreeItem<>(new JobOfferUI(comp));
    		comp.getAllOffers().stream().forEach(offer->
	    		compRoot.getChildren().add(new TreeItem<>(new JobOfferUI(comp, offer)))
	    	);
    		root.getChildren().add(compRoot);
//    		compRoot.setExpanded(true);
		});
		
		offerTable.setRoot(root);
		root.setExpanded(true);
	}
	
	private void prepareCompanyReportView(UICompanyBean comp){
		if(comp==null)
			return;
		clearColumns();
		TreeItem<JobOfferUI> compRoot = new TreeItem<>(new JobOfferUI(comp));
		comp.getAllOffers().stream().forEach(offer->
    		compRoot.getChildren().add(new TreeItem<>(new JobOfferUI(comp, offer)))
    	);
		
		companywiseOfferTable.setRoot(compRoot);
		companywiseOfferTable.getColumns().add(compNameColumn);
		companywiseOfferTable.getColumns().add(jafNoColumn);
		companywiseOfferTable.getColumns().add(rollNumberColumn);
		companywiseOfferTable.getColumns().add(initStatusColumn);
		companywiseOfferTable.getColumns().add(finalStatusColumn);
		compRoot.setExpanded(true);
	}

	private void prepareStudentReportView(){
		clearColumns();
		
		studentwiseOfferTable.getColumns().add(rollNumberColumn);
		studentwiseOfferTable.getColumns().add(compNameColumn);
		studentwiseOfferTable.getColumns().add(jafNoColumn);
		studentwiseOfferTable.getColumns().add(initStatusColumn);
		studentwiseOfferTable.getColumns().add(finalStatusColumn);
		
		TreeItem<JobOfferUI> root = new TreeItem<>(new JobOfferUI());
		uiCompList.stream().flatMap(comp->comp.getAcceptedOffers().stream())
		.sorted((o1,o2)->{return o1.getOffer().getStudent().getRollno().compareTo(o1.getOffer().getStudent().getRollno());})
		.forEach(offer->{
    		root.getChildren().add(new TreeItem<>(new JobOfferUI(offer.getUICompBean(), offer)));
		});
		
		studentwiseOfferTable.setRoot(root);
		root.setExpanded(true);
	}

	private void clearColumns(){
		offerTable.setRoot(null);
		offerTable.getColumns().clear();
		
		studentwiseOfferTable.setRoot(null);
		studentwiseOfferTable.getColumns().clear();
		
		companywiseOfferTable.setRoot(null);
		companywiseOfferTable.getColumns().clear();
	}
	
}
