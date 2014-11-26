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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import org.controlsfx.dialog.Dialogs;

import service.PrintTask;
import ui.bean.UICompanyBean;
import ui.bean.UIRepCompanyLetter;
import ui.bean.UIRepStudentJob;
import ui.bean.UIRepStudentPref;
import ui.bean.UIStudentBean;
import api.context.GlobalContext;


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
	
	@FXML TabPane tabPane;
	
	@FXML Tab compTab;
	@FXML ListView<UICompanyBean> companySelectList;
	@FXML Pane companywiseOfferTableContainer;
	
	@FXML Tab studTab;
	@FXML ListView<UIStudentBean> studentSelectList;
	@FXML Pane studentPrefTableContainer;
	
	@FXML Tab jobTab;
	@FXML Pane jobTableContainer;
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	close.setOnAction(e->{
    		UIProcessor.exit();
    	});
    	print.setOnAction(e->{
    		print();
    	});
    	
    	compTab.setOnSelectionChanged(e->{
//    		prepareCompanyReportView(companySelectList.getSelectionModel().getSelectedItem());
    	});
    	companySelectList.getSelectionModel().selectedItemProperty().addListener((e,oldComp,newComp)->{
    		if(newComp!=null){
    			// Show Company Letter
    			companywiseOfferTableContainer.getChildren().clear();
    			companywiseOfferTableContainer.getChildren().add(new UIRepCompanyLetter(newComp));
    			
    		}
    	});
    	studentSelectList.getSelectionModel().selectedItemProperty().addListener((e,oldStud,newStud)->{
    		if(newStud!=null){
    			// Show Company Letter
    			studentPrefTableContainer.getChildren().clear();
    			studentPrefTableContainer.getChildren().add(new UIRepStudentPref(newStud));
    			
    		}
    	});
    	
    	jobTableContainer.getChildren().add(new UIRepStudentJob());
    	
    	/*rollNum.setOnKeyReleased(e->{
    		String text = rollNum.getText();
    		List<Student> matchedStuds = allStudents.stream()
    				.filter(stud->stud.getRollno().matches(text))
    				.collect(Collectors.toList());
    		refreshPreferenceReport(matchedStuds);
    	});*/
    	
    }

    
//    private void refreshPreferenceReport(List<Student> studs) {
//    	TableView<TableReportFormat> a = new TableView<>();
////    	TableReport companyReport = ReportFactory.generateCompanyReport();
//    	TableReport companyReport = ReportFactory.generatePreferenceReport(studs);
//    	TableReportImpl rep = new TableReportImpl(a, companyReport, 6);
//    	rep.executeReport();
//    	prefTab.setContent(a);
//	}

	private void print() {
    	
    	
    	Tab selTab = tabPane.getSelectionModel().getSelectedItem();
    	Node content = selTab.getContent();
    	PrintTask ps = new PrintTask(content);
    	Dialogs.create().title("Printing").masthead("printing").showWorkerProgress(ps);
    	ps.print();
	}

	private List<UIStudentBean> allStudents;
	private ArrayList<UICompanyBean> uiCompList = new ArrayList<>();
	
	public void setCompanies(ArrayList<UICompanyBean> uiCompList) {
		this.uiCompList = uiCompList;

		companySelectList.getItems().clear();
		companySelectList.getItems().addAll(uiCompList);
		
		companySelectList.getSelectionModel().selectFirst();
		
		allStudents = GlobalContext.getLocalStore().getStudents()
				.stream()
				.map(stud->new UIStudentBean(stud))
				.collect(Collectors.toList());
		
		studentSelectList.getItems().clear();
		studentSelectList.getItems().addAll(allStudents);
		
		studentSelectList.getSelectionModel().selectFirst();
		
	}
	

}
