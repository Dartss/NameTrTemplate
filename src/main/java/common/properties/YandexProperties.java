package common.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.properties.core.DefaultProperties;
import common.properties.core.PropertiesMap;

/**
 * 
 * @author rud
 *
 *	used only in old components should be dropped later on 
 *
 */
@Deprecated
public class YandexProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final YandexProperties instance = new YandexProperties();

	public YandexProperties() {
		super(PROPERTIES_FILE);
	}

	public static YandexProperties getInstance() {
		return instance;
	}

	public static boolean loadProperties() {
		return getInstance().loadProperties(false);
	}

	public static boolean loadProperties(boolean refresh) {
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
			map = getInstance().loadMap(PROPERTIES_FILE, "tr.yandex");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return map;
	}

	public static String getAPI_KEY() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "tr.yandex.key");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getSERVICE_URL() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "tr.yandex.service_url");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getENCODING() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "tr.yandex.encoding");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Long getWORKER_CHAR_LIMIT_DAY() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "tr.yandex.limit.perDay");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Long getWORKER_CHAR_LIMIT_MONTH() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "tr.yandex.limit.perMonth");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Boolean isPRINT_STATS() {
		Boolean prop = null;
		try {
			prop = getInstance().getBoolean(PROPERTIES_FILE, "tr.print.stats");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Long getSEARCH_LIMIT_PER_DAY() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "sr.yandex.limit.perDay");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static  Integer getSEARCH_COOLDOWN() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "sr.yandex.cooldown");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static  Boolean isPRINT_RESPONSE() {
		Boolean prop = null;
		try {
			prop = getInstance().getBoolean(PROPERTIES_FILE, "yandex.print.response");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getSEPARATOR() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "tr.yandex.separator");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

}
