package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class GoogleProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final GoogleProperties instance = new GoogleProperties();

	
	public GoogleProperties() {
		super(PROPERTIES_FILE);
	}

	public static GoogleProperties getInstance() {
		return instance;
	}

	public static boolean loadProperties() {

		return getInstance().loadProperties(false);
	}

	public static boolean loadProperties(boolean refresh) {

		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static Boolean getPRINT_RESPONSE() {
		Boolean prop = null;
		try {
			prop = getInstance().getBoolean(PROPERTIES_FILE, "google.print.response");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Long getSEARCH_AJAX_LIMIT_PER_DAY() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "sr.google.ajax.limit.perDay");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getSEARCH_AJAX_COOLDOWN() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "sr.google.ajax.cooldown");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Long getGEOCODE_LIMIT_PER_DAY() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "geocode.google.limit.perDay");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getAPI_KEY() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "google.api.key");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

}
