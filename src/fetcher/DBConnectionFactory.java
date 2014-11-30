package fetcher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import api.context.GlobalContext;

/**
 * Factory class for DB connection.
 * @author Punit_Ghodasara
 *
 */
public class DBConnectionFactory {

	// No Object
	private DBConnectionFactory(){
	}
	
	public static Connection newConneciton(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+GlobalContext.DB_HOST_NAME+"/"+GlobalContext.DB_SCHMEA_NAME,
					GlobalContext.DB_USER_NAME,GlobalContext.DB_PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQL Exception : Can not make connection.");
			e.printStackTrace();
			return null;
		}catch(Exception e){
			System.out.println("Exception : Can not make connection.");
			return null;
		}
		System.out.println("SQLConnection Made.");
		return conn;
	}
}
