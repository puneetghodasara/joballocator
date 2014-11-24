package report;

import java.util.ArrayList;
import java.util.List;

import ui.bean.UICompanyBean;
import api.bean.Company;
import api.bean.Student;
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
	
	public static TableReport generateOfferReport(UICompanyBean comp){
		List<TableReportFormat> compReportData = new ArrayList<>();
		comp.getCompany().getAgent().getOffers().stream().sorted().forEach(offer->{
			compReportData.add(new CompanyReportFormat(offer));				
		});
		return ()->compReportData;
	}
	
	public static TableReport generatePreferenceReport(List<Student> students){
		List<TableReportFormat> prefReportData = new ArrayList<>();
		students.stream().forEach(stud->{
			stud.getPreferences().stream().sorted().forEach(pref->{
				prefReportData.add(new StudentPrefFormat(pref));				
			});
		});
		return ()->prefReportData;
	}
}
