package application;
	
import controller.Processor;
import report.ReportFactory;
import report.TableReportImpl;
import api.report.TableReport;
import api.report.TableReportFormat;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = new VBox();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Processor.init();
			
			TableView<TableReportFormat> table = new TableView<>();
//			TableReport tableReport = ReportFactory.generatePreferenceReport();
//			TableReportImpl tri = new TableReportImpl(table, tableReport, 4);
//			tri.executeReport();
			
			root.getChildren().add(table);
			System.out.println(table.getItems().size());
			System.out.println(table.getColumns().size());
			
			table.setVisible(true);
			root.setVisible(true);
			
			Button b = new Button("HI");
//			root.getChildren().add(b);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
