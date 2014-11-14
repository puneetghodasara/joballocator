package fetcher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import store.Repository;
import api.bean.Company;
import api.bean.Preference;
import api.bean.Student;
import api.context.GlobalContext;
import api.fetcher.DataFetcher;

/**
 * Implementation of Data Fetcher from Database
 * @author Punit_Ghodasara
 *
 */
public class DBDataFetcher implements DataFetcher{

	private static final String COMP_QRY = "select *"
			+ " from "+GlobalContext.COMPANY_SLOTTING_TABLE
			+ " where "+GlobalContext.BATCH_COLUMN+"=? and "+GlobalContext.DAY_COLUMN+"=? and "+GlobalContext.SLOT_COLUMN+"? ";
	
	@Override
	public ArrayList<Company> fetchCompanies(String batch, int day, int slot) {

		ArrayList<Company> companyList = new ArrayList<>();

		Connection conn = DBConnectionFactory.newConneciton();
		if(conn == null)
			return companyList;
		
		try (PreparedStatement pstmt = conn.prepareStatement(COMP_QRY)){
			pstmt.setString(0, batch);
			pstmt.setInt(1, day);
			pstmt.setInt(2, slot);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String compname = rs.getString(GlobalContext.COMPID_COLUMN);
				int jafsrno = rs.getInt(GlobalContext.PROFILEID_COLUMN);
				
				Company c = new Company(compname, jafsrno);
				companyList.add(c);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQL Exception : Getting Companies Query.");
		}
		return companyList;

	}
	
	private static final String STUD_QRY = "select distinct *"
			+ " from "+GlobalContext.STUDENT_TABLE
			+ " where "+GlobalContext.BATCH_COLUMN+"=? and "+GlobalContext.DAY_COLUMN+"=? and "+GlobalContext.SLOT_COLUMN+"? ";
	

	@Override
	public ArrayList<Student> fetchStudents(String batch, int day, int slot) {
		ArrayList<Student> studentList = new ArrayList<>();
		
		Connection conn = DBConnectionFactory.newConneciton();
		if(conn == null)
			return studentList;
		
		try (PreparedStatement pstmt = conn.prepareStatement(STUD_QRY)){
			pstmt.setString(0, batch);
			pstmt.setInt(1, day);
			pstmt.setInt(2, slot);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String rollno = rs.getString(GlobalContext.STUDENTID_COLUMN);
				studentList.add(new Student(rollno));
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQL Exception : Getting Preferences Query.");
		}
		return studentList;
	}

	
	private static final String PREF_QRY = "select *"
			+ " from "+GlobalContext.STUDENT_PREF_TABLE
			+ " where "+GlobalContext.BATCH_COLUMN+"=? and "+GlobalContext.DAY_COLUMN+"=? and "+GlobalContext.SLOT_COLUMN+"? "
			+ " order by "+GlobalContext.STUDENTID_COLUMN+","+GlobalContext.PREF_RANK_COLUMN;
	
	
	@Override
	public ArrayList<Preference> fetchPreferences(String batch, int day, int slot) {
		ArrayList<Preference> prefList = new ArrayList<>();
		
		Connection conn = DBConnectionFactory.newConneciton();
		if(conn == null)
			return prefList;
		
		try (PreparedStatement pstmt = conn.prepareStatement(PREF_QRY)){
			pstmt.setString(0, batch);
			pstmt.setInt(1, day);
			pstmt.setInt(2, slot);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String rollno = rs.getString(GlobalContext.STUDENTID_COLUMN);
				String comp = rs.getString(GlobalContext.COMPID_COLUMN);
				int jaf = rs.getInt(GlobalContext.PROFILEID_COLUMN);
				int rank = rs.getInt(GlobalContext.PREF_RANK_COLUMN);
				
				Company company = GlobalContext.getLocalStore().searchCompany(comp, jaf);
				Student student = GlobalContext.getLocalStore().searchStudent(rollno);
				if(company==null || student==null)
					continue;
				
				prefList.add(new Preference(rank, company, student));
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQL Exception : Getting Preferences Query.");
		}
		return prefList;
	}


	@Override
	public boolean testConnection() {
		return DBConnectionFactory.newConneciton()!=null;
	}

}
