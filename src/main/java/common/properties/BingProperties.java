package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class BingProperties extends DefaultProperties {

	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final BingProperties instance = new BingProperties();

	public BingProperties() {
		super(PROPERTIES_FILE);
	}

	public static BingProperties getInstance() {
		return instance;
	}

	public static boolean loadProperties() {

		return getInstance().loadProperties(false);
	}

	public  static boolean loadProperties(boolean refresh) {

		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static Boolean getPRINT_RESPONSE() {
		Boolean prop = null;
		try {
			prop = getInstance().getBoolean(PROPERTIES_FILE, "bing.print.response");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Long getSEARCH_LIMIT_PER_DAY() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "sr.bing.limit.perDay");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getSEARCH_COOLDOWN() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "sr.bing.cooldown");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}
}
