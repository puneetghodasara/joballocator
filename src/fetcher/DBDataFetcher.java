package fetcher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.ActionProcessor;
import store.Repository;
import api.bean.Company;
import api.bean.Preference;
import api.bean.Student;
import api.bean.offer.JobOffer;
import api.bean.offer.OfferStatus;
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
			+ " where "+GlobalContext.BATCH_COLUMN+"=? and "+GlobalContext.DAY_COLUMN+"=? "
			+ " and "+GlobalContext.SLOT_COLUMN+"=? ";
	
	private static final String COMP_QRY_D1 = "select *"
			+ " from "+GlobalContext.COMPANY_SLOTTING_TABLE
			+ " where "+GlobalContext.BATCH_COLUMN+"=? and "+GlobalContext.DAY_COLUMN+"=? ";
	
	@Override
	public ArrayList<Company> fetchCompanies(String batch, int day, int slot) {

		ArrayList<Company> companyList = new ArrayList<>();

		Connection conn = DBConnectionFactory.newConneciton();
		if(conn == null)
			return companyList;
		
		try (PreparedStatement pstmt = conn.prepareStatement(day!=1?COMP_QRY:COMP_QRY_D1)){
			pstmt.setString(1, batch);
			pstmt.setInt(2, day);
			if(day!=1)
				pstmt.setInt(3, slot);
			System.out.println("Comp Q : "+pstmt);
			
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
			e.printStackTrace();
		}
		return companyList;

	}
	
	private static final String STUD_QRY = "select distinct "+GlobalContext.STUDENTID_COLUMN
			+ ","+ GlobalContext.STUDENTNAME_COLUMN
			+ " from "+GlobalContext.STUDENT_TABLE
			+ " where "+GlobalContext.BATCH_COLUMN+"=? ";
	
	@Override
	public ArrayList<Student> fetchStudents(String batch, int day, int slot) {
		ArrayList<Student> studentList = new ArrayList<>();
		
		Connection conn = DBConnectionFactory.newConneciton();
		if(conn == null)
			return studentList;
		
		try (PreparedStatement pstmt = conn.prepareStatement(STUD_QRY)){
			pstmt.setString(1, batch);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String rollno = rs.getString(GlobalContext.STUDENTID_COLUMN);
				String name = rs.getString(GlobalContext.STUDENTNAME_COLUMN);
				studentList.add(new Student(rollno, name));
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQL Exception : Getting student Query.");
		}
		return studentList;
	}

	
	private static final String PREF_QRY = "select *"
			+ " from "+GlobalContext.STUDENT_PREF_TABLE
			+ " where "+GlobalContext.BATCH_COLUMN+"=? and "+GlobalContext.DAY_COLUMN+"=? and "+GlobalContext.SLOT_COLUMN+"=? "
			+ " order by "+GlobalContext.STUDENTID_COLUMN+","+GlobalContext.PREF_RANK_COLUMN;
	
	private static final String PREF_QRY_D1 = "select *"
			+ " from "+GlobalContext.STUDENT_PREF_TABLE
			+ " where "+GlobalContext.BATCH_COLUMN+"=? and "+GlobalContext.DAY_COLUMN+"=? "
			+ " order by "+GlobalContext.STUDENTID_COLUMN+","+GlobalContext.PREF_RANK_COLUMN;
	
	
	@Override
	public ArrayList<Preference> fetchPreferences(String batch, int day, int slot) {
		ArrayList<Preference> prefList = new ArrayList<>();
		
		Connection conn = DBConnectionFactory.newConneciton();
		if(conn == null)
			return prefList;
		
		try (PreparedStatement pstmt = conn.prepareStatement(day!=1?PREF_QRY:PREF_QRY_D1)){
			pstmt.setString(1, batch);
			pstmt.setInt(2, day);
			if(day!=1)
				pstmt.setInt(3, slot);
			
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
			e.printStackTrace();
		}
		return prefList;
	}


	@Override
	public boolean testConnection() {
		return DBConnectionFactory.newConneciton()!=null;
	}


	private static final String OFFER_QRY = "insert into "+GlobalContext.OFFER_TABLE
			+ " (batch,day,slot,complogin,jafsrno,rollno,"+GlobalContext.TYPE_COLUMN+",rank) values  "
			+ " (?,?,?,?,?,?,?,?)";
	
	@Override
	public void pushOffer(JobOffer offer) {
		
		Connection conn = DBConnectionFactory.newConneciton();
		if(conn == null)
			return ;
		
		try (PreparedStatement pstmt = conn.prepareStatement(OFFER_QRY)){
			pstmt.setString(1, GlobalContext.BATCH);
			pstmt.setInt(2, GlobalContext.DAY);
			pstmt.setInt(3, GlobalContext.SLOT);
			pstmt.setString(4, offer.getCompany().getCompanyName());
			pstmt.setInt(5, offer.getCompany().getJafNo());
			pstmt.setString(6, offer.getStudent().getRollno());
			pstmt.setString(7, offer.getInitStatus()==OfferStatus.ACTUAL_OFFER?"AC":"WL");
			pstmt.setInt(8, offer.getRank());
			
			int rs = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL Exception : Pushing Offer Query.");
			e.printStackTrace();
		}
		return;
	}

	private static final String OFFER_DEL_QRY = "delete from "+GlobalContext.OFFER_TABLE
			+ " where batch=?, day=? ,slot=? , complogin=?, jafsrno=?, rollno=?";
	
	
	@Override
	public void delOffer(JobOffer offer) {
		
		Connection conn = DBConnectionFactory.newConneciton();
		if(conn == null)
			return ;
		
		try (PreparedStatement pstmt = conn.prepareStatement(OFFER_DEL_QRY)){
			pstmt.setString(1, GlobalContext.BATCH);
			pstmt.setInt(2, GlobalContext.DAY);
			pstmt.setInt(3, GlobalContext.SLOT);
			pstmt.setString(4, offer.getCompany().getCompanyName());
			pstmt.setInt(5, offer.getCompany().getJafNo());
			pstmt.setString(6, offer.getStudent().getRollno());
			
			int rs = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL Exception : DEL Offer Query.");
			e.printStackTrace();
		}
		return;
	}

	private static final String OFFER_FETCH_QRY = "select *"
			+ " from "+GlobalContext.OFFER_TABLE
			+ " where "+GlobalContext.BATCH_COLUMN+"=? and "+GlobalContext.DAY_COLUMN+"=? "
			+ " and "+GlobalContext.SLOT_COLUMN+"=? ";
	
	private static final String OFFER_FETCH_QRY_D1 = "select *"
			+ " from "+GlobalContext.OFFER_TABLE
			+ " where "+GlobalContext.BATCH_COLUMN+"=? and "+GlobalContext.DAY_COLUMN+"=? ";
	
	@Override
	public ArrayList<JobOffer> fetchOffers() {

		ArrayList<JobOffer> offerList = new ArrayList<>();

		Connection conn = DBConnectionFactory.newConneciton();
		if(conn == null)
			return offerList;

		int day= GlobalContext.DAY;
		try (PreparedStatement pstmt = conn.prepareStatement(day!=1?OFFER_FETCH_QRY:OFFER_FETCH_QRY_D1)){
			pstmt.setString(1, GlobalContext.BATCH);
			pstmt.setInt(2, GlobalContext.DAY);
			
			if(day!=1)
				pstmt.setInt(3, GlobalContext.SLOT);
			System.out.println("Offer Q : "+pstmt);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String compname = rs.getString(GlobalContext.COMPID_COLUMN);
				int jafsrno = rs.getInt(GlobalContext.PROFILEID_COLUMN);
				String rollno = rs.getString(GlobalContext.STUDENTID_COLUMN);
				
				Company company = GlobalContext.getLocalStore().searchCompany(compname, jafsrno);
				Student student = GlobalContext.getLocalStore().searchStudent(rollno);
				OfferStatus os = rs.getString(GlobalContext.TYPE_COLUMN).equals("AC")?
						OfferStatus.ACTUAL_OFFER:OfferStatus.WAITLIST_OFFER;
				int offerrank = rs.getInt(GlobalContext.RANK_COLUMN);
				
				ActionProcessor.processAddOffer(company, student, os, offerrank, true);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQL Exception : Getting Companies Query.");
			e.printStackTrace();
		}
		return offerList;

	}

}
