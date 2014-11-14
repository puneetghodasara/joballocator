package report;

import java.util.ArrayList;
import java.util.List;

import api.context.GlobalContext;
import api.report.TableReport;
import api.report.TableReportFormat;

/**
 * Factory class of 
 * 1) Company Report
 * 2) Preference Report
 * @author Punit_Ghodasara
 *
 */
public class ReportFactory {

	private ReportFactory() {
	}
	
	public static TableReport generateCompanyReport(){
		List<TableReportFormat> compReportData = new ArrayList<>();
		GlobalContext.getLocalStore().getCompanies().stream().forEach(comp->{
			comp.getAgent().getOffers().stream().sorted().forEach(offer->{
				compReportData.add(new CompanyReportFormat(offer));				
			});
		});
		return ()->compReportData;
	}
	
	public static TableReport generatePreferenceReport(){
		List<TableReportFormat> prefReportData = new ArrayList<>();
		GlobalContext.getLocalStore().getStudents().stream().forEach(stud->{
			stud.getPreferences().stream().sorted().forEach(pref->{
				prefReportData.add(new StudentPrefFormat(pref));				
			});
		});
		return ()->prefReportData;
	}
}
