package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class GnipProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final GnipProperties instance = new GnipProperties();

	public GnipProperties() {
		super(PROPERTIES_FILE);
	}

	public static GnipProperties getInstance() {
		return instance;
	}
	public static boolean loadProperties() {

		return getInstance().loadProperties(false);
	}

	public static boolean loadProperties(boolean refresh) {

		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static String getGNIP_URL() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "gnip.stream.url");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getGNIP_STREAM_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "gnip.stream.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getGNIP_ACCOUNT() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "gnip.account");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getGNIP_USERNAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "gnip.username");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getGNIP_PASSWORD() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "gnip.password");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

}
