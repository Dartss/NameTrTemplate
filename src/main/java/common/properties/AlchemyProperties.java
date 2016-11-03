package common.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;
import jsmarty.core.common.properties.core.PropertiesMap;

public class AlchemyProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final AlchemyProperties instance = new AlchemyProperties();

	public AlchemyProperties() {
		super(PROPERTIES_FILE);
	}

	public static AlchemyProperties getInstance() {
		return instance;
	}

	public static  boolean loadProperties() {
		return getInstance().loadProperties(false);
	}

	public  boolean loadProperties(boolean refresh) {
		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static PropertiesMap getMAP() {
		List<String> sufix = new ArrayList<String>();
		sufix.add("map.host");
		sufix.add("map.port");
		sufix.add("map.api_key");
		String[] keyForMap = { "127.0.0.1", "port", "api_key" };
		PropertiesMap map = null;
		try {
			map = getInstance().loadMap(PROPERTIES_FILE, "alchemy");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return map;
	}

	public static String getALCHEMY_API_KEY() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "alchemy.api_key");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Long getALCHEMY_CALLS_LIMIT() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "alchemy.calls.limit");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;

	}

	public static Long getALCHEMY_CALLS_PERIOD() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "alchemy.calls.period");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;

	}

	public static Boolean isPRINT_RESPONSE() {
		Boolean prop = null;
		try {
			prop = getInstance().getBoolean(PROPERTIES_FILE, "alchemy.print.response");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;

	}

	public static Integer getALCHEMY_CALLS_COOLDOWN() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "alchemy.calls.cooldown");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;

	}

	public static Long getALCHEMY_CALLS_PERSECOND() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "alchemy.calls.limit.perSecond");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;

	}

	public static String getALCHEMY_CALLS_PERIOD_RESET_DATE() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "alchemy.calls.period.reset.date");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;

	}

	public static Boolean isALCHEMY_PRINT_RATE() {
		Boolean prop = null;
		try {
			prop = getInstance().getBoolean(PROPERTIES_FILE, "alchemy.print.rate");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;

	}

	public static Boolean isALCHEMY_PRINT_CALLSLEFT() {
		Boolean prop = null;
		try {
			prop = getInstance().getBoolean(PROPERTIES_FILE, "alchemy.print.callsLeft");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;

	}
}
