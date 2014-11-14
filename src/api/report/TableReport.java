package api.report;

import java.util.List;

/**
 * Table Report should have a sample Row and data
 * @author Punit_Ghodasara
 *
 */
public interface TableReport {
	/**
	 * A sample row. Default is first row.
	 * @return
	 */
	public default TableReportFormat getSample(){
		return getReport().get(0);
	}
	
	/**
	 * Full data
	 * @return
	 */
	public List<TableReportFormat> getReport();
}
