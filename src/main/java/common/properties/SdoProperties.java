package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class SdoProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final SdoProperties instance = new SdoProperties();

	public SdoProperties() {
		super(PROPERTIES_FILE);
	}

	public static SdoProperties getInstance() {
		return instance;
	}
	public static boolean loadProperties() {

		return getInstance().loadProperties(false);
	}

	public static boolean loadProperties(boolean refresh) {

		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static  Long getEXPIRY_TIME() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "sdo.expired.time");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

}
