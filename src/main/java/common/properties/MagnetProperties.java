package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class MagnetProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final MagnetProperties instance = new MagnetProperties();

	public MagnetProperties() {
		super(PROPERTIES_FILE);
	}

	public static MagnetProperties getInstance() {
		return instance;
	}
	public static boolean loadProperties() {

		return getInstance().loadProperties(false);
	}

	public static boolean loadProperties(boolean refresh) {

		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static String getMAGNET_API_KEY() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "magnet.api_key");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getMAGNET_API_SECRET_KEY() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "magnet.api_secret_key");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Long getMAGNET_CALLS_LIMIT() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "magnet.calls.limit");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Long getMAGNET_CALLS_PERIOD() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "magnet.calls.period");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getMAGNET_CALLS_COOLDOWN() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "magnet.calls.cooldown");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getMAGNET_CALLS_PERIOD_RESET_DATE() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "magnet.calls.period.reset.date");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Boolean isMAGNET_PRINT_RATE() {
		Boolean prop = null;
		try {
			prop = getInstance().getBoolean(PROPERTIES_FILE, "magnet.print.rate");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Boolean isMAGNET_PRINT_CALLSLEFT() {
		Boolean prop = null;
		try {
			prop = getInstance().getBoolean(PROPERTIES_FILE, "magnet.print.callsLeft");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Boolean isPRINT_RESPONSE() {
		Boolean prop = null;
		try {
			prop = getInstance().getBoolean(PROPERTIES_FILE, "magnet.print.response");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

}
