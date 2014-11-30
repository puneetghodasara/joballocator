package ui.comp;

import javafx.concurrent.Worker;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

import ui.Main;

public class DialogUtil {

	
	public static void showInformation(String title, String message){
		Dialogs.create().owner(Main.stage)
				.title(title)
				.message(message)
				.style(DialogStyle.NATIVE)
				.showInformation();
	}

	public static boolean showError(String title, String message) {
		Action response = Dialogs.create().owner(Main.stage)
				.title(title)
				.message(message)
				.style(DialogStyle.NATIVE)
				.showError();
		return (response==Dialog.Actions.OK)?true:false;
	}
	
	public static void showProgress(Worker<?> worker){
		Dialogs.create().title("Printing").masthead("printing").showWorkerProgress(worker);
	}
}
