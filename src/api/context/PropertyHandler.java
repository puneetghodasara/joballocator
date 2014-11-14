package api.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * To save and to load property in file
 * @author Punit_Ghodasara
 *
 */
public class PropertyHandler {
	
	private static final String propertyFile = "joballocator.prop";
	
	private static Properties appProps = new Properties();
	
	static{
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream is = cl.getResourceAsStream(propertyFile);
		if(is!=null){
			try {
				appProps.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}

	public String get(String key){
		return appProps.getProperty(key);
	}
	public void set(String key, String value){
		appProps.setProperty(key, value);
	}
}
