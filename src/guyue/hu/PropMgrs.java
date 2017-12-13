package guyue.hu;

import java.io.IOException;
import java.util.*;

public class PropMgrs {
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(PropMgrs.class.getResourceAsStream("/configs/tank.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * µ¥ÀýÄ£Ê½
	 */
	private PropMgrs() {}
	
	public static String getProps(String key) {
		return prop.getProperty(key);
	}
}
