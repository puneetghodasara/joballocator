package service;

import javafx.concurrent.Task;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.transform.Scale;
import ui.bean.UIOfferBean;
import ui.bean.UIRepStudentJob;
import api.context.GlobalContext;

public class PrintTask extends Task<Void>{

	
	private TableView<UIOfferBean> content;
	private Printer printer;
	private String msg;
	
	public PrintTask(Node node) {
		content = new UIRepStudentJob();
		printer = Printer.getDefaultPrinter();
    	PageLayout pageLayout = printer.getDefaultPageLayout();
    	
    	msg = "";
    	msg+="<table>";
    	content.getItems().stream().forEach(us->{
    		msg+="<tr>";
    		msg+="<td>"+us.compnameProperty().get()+"</td>";
    		msg+="<td>"+us.jafnoProperty().get()+"</td>";
    		msg+="<td>"+us.rollnumberProperty().get()+"</td>";
    		msg+="<td>"+us.studentnameProperty().get()+"</td>";
    		msg+="<td>"+us.finalStatusProperty().get()+"</td>";
    		msg+="</tr>";
    	});
    	
    	content.setMaxWidth(pageLayout.getPrintableWidth());
//    	content.getTransforms().add(new Scale(scaleX, 1));
	}
	
	public void print(){
    	GlobalContext.es.submit(this);
	}

	@Override
	protected Void call() throws Exception {
		updateMessage("1");
		updateProgress(-1, 100);

    	
    	PrinterJob printerJob = PrinterJob.createPrinterJob(printer);
    	boolean status = printerJob.printPage(content);
    	
    	System.out.println(status);
    	printerJob.endJob();
		
    	System.out.println(printerJob.getJobStatus());
    	
    	updateMessage("1");
		updateProgress(100, 100);
    	
		return null;
	}
	

}
