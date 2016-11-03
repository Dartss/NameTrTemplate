package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class GnipRulesRatingProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final GnipRulesRatingProperties instance = new GnipRulesRatingProperties();

	public GnipRulesRatingProperties() {
		super(PROPERTIES_FILE);
	}

	public static GnipRulesRatingProperties getInstance() {
		return instance;
	}
	public static boolean loadProperties() {

		return getInstance().loadProperties(false);
	}

	public static boolean loadProperties(boolean refresh) {

		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static String getGNIP_RULES_FOLDER_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "gnip.rules.folder.namer");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getGNIP_RULES_QUEUE_HOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "gnip.rules.queue.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getGNIP_RULES_QUEUE_PORT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "gnip.rules.queue.port");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getGNIP_RULE_EXPIRE_TIME() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "gnip.rule.expire.time");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

}
