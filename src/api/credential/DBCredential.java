package api.credential;

import ui.bean.UIDBLoginCredential;

/**
 * Bean class of DB credentials
 * @author Punit_Ghodasara
 *
 */
public class DBCredential {

	private String username;
	private String password;
	private String hostname;
	private String ip;
	private String dbname;
	
	private String batch;
	private int day;
	private int slot;
	
	public DBCredential(UIDBLoginCredential uidbLoginBean) {
		super();
		ip = uidbLoginBean.getIp();
		hostname = (uidbLoginBean.getHostname());
		dbname = (uidbLoginBean.getDbname());
		username = (uidbLoginBean.getUsername());
		password = (uidbLoginBean.getPassword());
		batch = (uidbLoginBean.getBatch());
		day = (uidbLoginBean.getDay());
		slot = (uidbLoginBean.getSlot());
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getHostname() {
		return hostname;
	}

	public String getIp() {
		return ip;
	}

	public String getDbname() {
		return dbname;
	}

	public String getBatch() {
		return batch;
	}

	public int getDay() {
		return day;
	}

	public int getSlot() {
		return slot;
	}
	
}
