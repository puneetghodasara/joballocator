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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ui.bean.UIDBLoginCredential;

/**
 * Login Controller.
 */
public class DBLoginController extends AnchorPane implements Initializable {

	@FXML
    TextField ip;
	@FXML
    TextField dbname;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
	TextField batch;
	@FXML
	TextField day;
	@FXML
	TextField slot;
    @FXML
    Button loginButton;
    @FXML
    Button exitButton;
    @FXML
    Label errorMsg;
    
	private UIDBLoginCredential defaultDB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        
        batch.setText(String.valueOf(date.getYear()));
        day.setText(String.valueOf(date.getDayOfMonth()));
        slot.setText(String.valueOf(date.getDayOfMonth()>5?1:(time.getHour()>22?2:1)));
        
        loginButton.setOnAction(e->{
        	defaultDB.setIp(ip.getText());
        	defaultDB.setDbname(dbname.getText());
        	defaultDB.setUsername(username.getText());
        	defaultDB.setPassword(password.getText());

        	if(!canSet(batch.getText(),day.getText(),slot.getText())){
            	errorMsg.setText("Incorrect batch/day/slot.");
            	return;
        	}
        	defaultDB.setBatch(batch.getText());
        	defaultDB.setDay(Integer.parseInt(day.getText()));
        	defaultDB.setSlot(Integer.parseInt(slot.getText()));
        	UIProcessor.processDBPage(defaultDB);
        });
        exitButton.setOnAction(e->{
        	Main.exit();
        });
    }
    
    private boolean canSet(String b, String d, String s) {
    	if(b.length()!=4)
    		return false;
    	int day,slot;
    	try{
    		day = Integer.parseInt(d);
    		slot = Integer.parseInt(d);
    	}catch(NumberFormatException nfe){
    		return false;
    	}
    	return true;
	}

	public void setDefaultBean(UIDBLoginCredential defaultDB) {
		this.defaultDB = defaultDB;
		errorMsg.setText("");
        ip.setPromptText(defaultDB.getIp());
        dbname.setPromptText(defaultDB.getDbname());
        username.setPromptText(defaultDB.getUsername());

	}

	public void setMessage(String msg) {
		if(msg!=null)
			errorMsg.setText(msg);
	}

}
