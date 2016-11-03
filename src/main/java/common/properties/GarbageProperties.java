package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class GarbageProperties extends DefaultProperties {
	
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final GarbageProperties instance = new GarbageProperties();

	public GarbageProperties() {
		super(PROPERTIES_FILE);
	}

	public static GarbageProperties getInstance() {
		return instance;
	}

	public static boolean loadProperties() {

		return getInstance().loadProperties(false);
	}

	public static boolean loadProperties(boolean refresh) {

		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static String getREDIS_HOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "garbage.redis.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getREDIS_PORT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "garbage.redis.port");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getREDIS_QUEUE_REDUCE_EVENTS() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.redis.queue.reduce_events");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found froperties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getSHORT_TERM_QUEUE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "garbage.sdo.jobs.shortterm.queue.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getMID_TERM_QUEUE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "garbage.sdo.jobs.midterm.queue.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getLONG_TERM_QUEUE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "garbage.sdo.jobs.longterm.queue.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getGRAPH_HOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "garbage.graph.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getGRAPH_USERNAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "garbage.graph.username");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getGRAPH_PASSWORD() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "garbage.graph.password");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_HOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "garbage.elastic.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getELASTIC_PORT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "garbage.elastic.port");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_INDEX_SMARTY() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "garbage.elastic.index.smarty");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_INDEX_SMARTYSTATS() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "garbage.elastic.index.smartystats");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_INDEX_SMARTYJOBS() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "garbage.elastic.index.smartyjobs");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_TYPE_SDO() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "garbage.elastic.type.sdo");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_TYPE_TWEET() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "garbage.elastic.type.tweet");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_TYPE_FACEBOOK_POST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "garbage.elastic.type.facebook_post");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_TYPE_URL_TWITTER() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "garbage.elastic.type.url_twitter");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_TYPE_URL_FACEBOOK() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "garbage.elastic.type.url_facebook");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getGARBAGE_COLLECTION_FREQUENCY() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "garbage.collection.frequency");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getPAST_DAYS() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "garbage.collection.clean.since.days");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}
}
