package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class ServerProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final ServerProperties instance = new ServerProperties();

	public ServerProperties() {
		super(PROPERTIES_FILE);
	}

	public static ServerProperties getInstance() {
		return instance;
	}

	public static  boolean loadProperties() {

		return getInstance().loadProperties(false);
	}

	public static boolean loadProperties(boolean refresh) {

		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static String getHOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "server.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}
}
