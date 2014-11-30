package controller;

import api.context.GlobalContext;
import javafx.concurrent.Task;

public class InitProcessor extends Task<Boolean>{

	@Override
	protected Boolean call() throws Exception {
		updateProgress(-1, 100);
		updateMessage("1");
		boolean status = GlobalContext.getDataFetcher().testConnection();
		if(status)
			Processor.init();
		updateProgress(100, 100);
		updateMessage("1");
		return status;
	}

}
