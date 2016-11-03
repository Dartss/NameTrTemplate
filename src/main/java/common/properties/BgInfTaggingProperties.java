package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class BgInfTaggingProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final BgInfTaggingProperties instance = new BgInfTaggingProperties();

	public BgInfTaggingProperties() {
		super(PROPERTIES_FILE);
	}

	public static BgInfTaggingProperties getInstance() {
		return instance;
	}

	public static boolean loadProperties() {

		return getInstance().loadProperties(false);
	}

	public static boolean loadProperties(boolean refresh) {

		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static Integer getKEYWORD_SELECTION_LIMIT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "keyword.selection.limit");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

}
