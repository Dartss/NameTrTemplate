package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class BitProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final BitProperties instance = new BitProperties();

	public BitProperties() {
		super(PROPERTIES_FILE);
	}

	public static BitProperties getInstance() {
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

	public static Integer getLOCATION_SELECTION_LIMIT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "location.selection.limit");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_HOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "bit.service.elastic.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_INDEX_JSGEO() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "bit.service.elastic.index.jsgeo");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getELASTIC_PORT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "bit.service.elastic.port");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_TYPE_GEO() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "bit.service.elastic.type.geo");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_TYPE_LOCATION() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"bit.service.elastic.type.location");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}
}
