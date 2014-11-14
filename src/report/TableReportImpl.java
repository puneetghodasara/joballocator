package report;

import java.util.List;

import api.report.Report;
import api.report.TableReport;
import api.report.TableReportFormat;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * A concrete Table Report
 * @author Punit_Ghodasara
 *
 */
public class TableReportImpl implements Report {
	
	private TableView<TableReportFormat> table;
	private TableReportFormat data;
	private int noColumn;
	private List<TableReportFormat> list;
	
	public TableReportImpl(TableView<TableReportFormat> table, TableReport report, int noColumn) {
		this.table = table;
		this.data = report.getSample();
		this.list = report.getReport();
		this.noColumn = noColumn;
	}
	
	@Override
	public void executeReport() {
		for(int i=0;i<noColumn;i++){
			final int colNum = i;
			TableColumn<TableReportFormat, String> aColumn = new TableColumn<>(data.getColumnHeader(colNum));
			aColumn.setCellValueFactory(cellData -> cellData.getValue().getColumnBinding(colNum));
			table.getColumns().add(aColumn);	
		}
		table.setItems(FXCollections.observableArrayList(list));
	}

}
