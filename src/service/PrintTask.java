package service;

import java.io.File;
import java.io.FileOutputStream;

import javafx.concurrent.Task;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import ui.bean.UIRepStudentJob;
import api.context.GlobalContext;

public class PrintTask extends Task<Void>{

	
	private AnchorPane content;
	private Printer printer;
	private String msg;
	
	public PrintTask(Node node) {
		printer = Printer.getDefaultPrinter();
    	PageLayout pageLayout = printer.getDefaultPageLayout();
    	
    	msg = "";
    	msg+="<table border='1px solid black'>";
    	
    	msg+="<tr>";
		msg+="<td>Company Name</td>";
		msg+="<td>JAF No</td>";
		msg+="<td>Roll Number</td>";
		msg+="<td>Student Name</td>";
		msg+="<td>Status</td>";
		msg+="</tr>";
		
    	 (new UIRepStudentJob()).getItems()
    	 		.stream()
    	 		.sorted((o1,o2)->{
    	 			return o1.compnameProperty().get().compareTo(o2.compnameProperty().get());
    	 		})
    	 		.forEach(us->{
    		msg+="<tr>";
    		msg+="<td>"+us.compnameProperty().get()+"</td>";
    		msg+="<td>"+us.jafnoProperty().get()+"</td>";
    		msg+="<td>"+us.rollnumberProperty().get()+"</td>";
    		msg+="<td>"+us.studentnameProperty().get()+"</td>";
    		msg+="<td>"+us.finalStatusProperty().get()+"</td>";
    		msg+="</tr>";
    	});
    	msg+="</table>";
    	TextArea ta = new TextArea(msg);
    	content = new AnchorPane();
    	content.setMaxWidth(pageLayout.getPrintableWidth());
    	content.getChildren().add(ta);
//    	content.getTransforms().add(new Scale(scaleX, 1));
	}
	
	public void print(){
    	GlobalContext.es.submit(this);
	}

	@Override
	protected Void call() throws Exception {
		updateMessage("1");
		updateProgress(-1, 100);

		File fis = new File("list.html");
		FileOutputStream fos = new FileOutputStream(fis);
		fos.write(msg.getBytes(), 0, msg.getBytes().length);
		fos.flush();
		fos.close();

		// TODO make it windows independent
		Runtime.getRuntime().exec("explorer list.html");
    	
//    	PrinterJob printerJob = PrinterJob.createPrinterJob(printer);
//    	boolean status = printerJob.printPage(content);
//    	
//    	System.out.println(status);
//    	printerJob.endJob();
//		
//    	System.out.println(printerJob.getJobStatus());
//    	
    	updateMessage("1");
		updateProgress(100, 100);
    	
		return null;
	}
	

}
