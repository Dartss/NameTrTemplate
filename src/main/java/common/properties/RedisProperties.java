package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class RedisProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final RedisProperties instance = new RedisProperties();

	public RedisProperties() {
		super(PROPERTIES_FILE);
	}

	public static RedisProperties getInstance() {
		return instance;
	}
	public static  boolean loadProperties() {

		return getInstance().loadProperties(false);
	}

	public static boolean loadProperties(boolean refresh) {

		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static String getREDIS_TIMEOUT_TIME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "redis.properties.timeout.time");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

}
