/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 */
package ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

import ui.bean.UICompanyBean;
import ui.bean.UIDBLoginCredential;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import api.bean.Company;
import api.context.GlobalContext;

/**
 * Main Application. This class handles navigation and user session.
 */
public class Main extends Application {

    private static Stage stage;
    private final double MINIMUM_WINDOW_WIDTH = 390.0;
    private final double MINIMUM_WINDOW_HEIGHT = 500.0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            stage.setTitle("FXML Login Sample");
//            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
//            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);

//            gotoLogin(null, false);
            UIProcessor.entry();
            
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        } 
        Scene scene = new Scene(page/*, 800, 600*/);
        stage.setScene(scene);
        stage.sizeToScene();
        // Preventing accidental close
        stage.setOnCloseRequest(we->{
        	Action showConfirm = Dialogs.create().title("Application Exit").message(GlobalContext.CONFIRM_EXIT).style(DialogStyle.NATIVE).showConfirm();
        	if(showConfirm==Dialog.Actions.YES){
        		exit();
        	}
        	we.consume();
        });
        return (Initializable) loader.getController();
    }
    
	public static void exit() {
		stage.close();
	}

	private static Initializable currentPage = null;
	// 1. First Page
	public static void gotoLogin(String loginAttemptMsg, boolean refresh) {
		System.out.println("Called Login Page :"+loginAttemptMsg);
		try {
			if(!refresh)
				currentPage = replaceSceneContent("login.fxml");
            ((LoginController)currentPage).setMessage(loginAttemptMsg);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

	// 2. Second Page
	public static void gotoDBLogin(String msg, boolean refresh) {
		System.out.println("Called DB Page");
        UIDBLoginCredential defaultDB = new UIDBLoginCredential();
        defaultDB.setIp("10.99.0.26");
        defaultDB.setDbname("placement");
        defaultDB.setUsername("placement");

        try {
			if(!refresh)
				currentPage = replaceSceneContent("DBLogin.fxml");
            
			((DBLoginController)currentPage).setDefaultBean(defaultDB);
            ((DBLoginController)currentPage).setMessage(msg);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

	// 3. Third Page
	public static void gotoAfterDB(ArrayList<UICompanyBean> compList, boolean refresh) {
		try {
			if(!refresh)
				currentPage = replaceSceneContent("CombinedInfo.fxml");
			((NewInfoController)currentPage).setCompanies(compList);
		} catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }		
	}

	// 4. Fourth Page
	public static void showOfferPage(UICompanyBean selComp, boolean refresh) {
		System.out.println("Called Offer Page :"+selComp);
		try {
			if(!refresh)
				currentPage = replaceSceneContent("JobOffer.fxml");
            ((JobOfferController)currentPage).setCompany(selComp);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
	// 5. Fifth Page
	public static void gotoJobReport(ArrayList<UICompanyBean> uiCompList) {
		System.out.println("Called Job Report");
		try {
			currentPage = replaceSceneContent("JobReport.fxml");
			((JobReportController)currentPage).setCompanies(uiCompList);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }		
	}

	public static boolean showError(String title, String message) {
		Action response = Dialogs.create().owner(stage)
				.title(title)
				.message(message)
				.style(DialogStyle.NATIVE)
				.showError();
		return (response==Dialog.Actions.OK)?true:false;
	}

}

