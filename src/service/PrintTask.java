package service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ui.bean.UIRepStudentJob;
import javafx.concurrent.Task;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class PrintTask extends Task<Void>{

	
	public static ExecutorService es = Executors.newSingleThreadExecutor();
	
	private Node content;
	public PrintTask(Node node) {
		content = new UIRepStudentJob();
		
	}
	
	public void print(){
    	es.submit(this);
	}

	@Override
	protected Void call() throws Exception {
		updateMessage("1");
		updateProgress(-1, 100);

    	Printer printer = Printer.getDefaultPrinter();
    	PageLayout pageLayout = printer.getDefaultPageLayout();
    	
    	
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
