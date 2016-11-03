package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class LanguageDetectorProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final LanguageDetectorProperties instance = new LanguageDetectorProperties();

	public LanguageDetectorProperties() {
		super(PROPERTIES_FILE);
	}

	public static LanguageDetectorProperties getInstance() {
		return instance;
	}

	public static boolean loadProperties() {

		return getInstance().loadProperties(false);
	}

	public static  boolean loadProperties(boolean refresh) {

		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static  String getIN_HOUSE_NB_THREADS() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "inhouse.nbThreads");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static  String getJOBS_QUEUE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"languages.detector.jobs.queue.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getREDIS_HOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "languages.detector.redis.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getREDIS_PORT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "languages.detector.redis.port");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Long getCHECK_PERIOD() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE,
					"languages.detector.manager.check.period");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getNUMBER_OF_WORKERS() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "languages.detector.workers.number");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Long getUPDATE_SUPPORTED_LANGUAGES() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE,
					"languages.detector.update.supported.languages");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDATA_PARSER_QUEUE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"languages.detector.data.parser.queue.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getTRANSLATION_QUEUE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"languages.detector.translation.queue.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getGARBAGE_ELASTIC_INDEX_URL() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"router.sdo.garbage.elastic.index.url");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getSHUYO_PROFILES_PATH() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"languages.detector.shuyo.resources.profiles.path");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public  static String getELASTIC_HOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "languages.detector.elastic.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getELASTIC_PORT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "languages.detector.elastic.port");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_INDEX_SMARTY() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"languages.detector.elastic.index.smarty");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_TYPE_GARBAGE() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"languages.detector.elastic.type.garbage");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

}
