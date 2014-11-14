package api.report;

import javafx.beans.property.SimpleStringProperty;

/**
 * Row class of TableReport
 * @author Punit_Ghodasara
 *
 */
public interface TableReportFormat {
	/**
	 * Title of column i
	 * @param i
	 * @return
	 */
	public String getColumnHeader(int i);
	
	/**
	 * Binding parameter of column i. We work on String only.
	 * @param i
	 * @return
	 */
	public SimpleStringProperty getColumnBinding(int i);

}
