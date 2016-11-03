package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class EventManagerProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final EventManagerProperties instance = new EventManagerProperties();

	public EventManagerProperties() {
		super(PROPERTIES_FILE);
	}

	public static EventManagerProperties getInstance() {
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
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.redis.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getREDIS_PORT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "eventmanager.redis.port");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getREDIS_JOB_QUEUE_FORM_EVENTS() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.redis.queue.form_events");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getREDIS_JOB_QUEUE_REDUCE_EVENTS() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.redis.queue.reduce_events");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getREDIS_JOB_QUEUE_UPDATE_STATS() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.redis.queue.update_stats");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_HOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.elastic.server.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found froperties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getELASTIC_PORT() {
		Integer prop = null;
		try {
			return getInstance().getInt(PROPERTIES_FILE, "eventmanager.elastic.server.port");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found froperties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_INDEX_SMARTYEVENTS() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.elastic.index.smartyevents");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found froperties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_INDEX_SMARTYSTATS() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.elastic.index.smartystats");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_TYPE_RELATIONS() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.elastic.type.relations");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_TYPE_STATS() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.elastic.type.stats");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getNEO_HOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.neo.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found froperties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getNEO_USERNAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.neo.username");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found froperties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getNEO_PASSWORD() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.neo.password");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found froperties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDB_TABLE_SETTINGS_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.db.tablename.settings");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getJDBC_DRIVER() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.jdbc.driver");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found froperties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getJDBC_PASSWORD() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.jdbc.password");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found froperties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getJDBC_URL() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.jdbc.url");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found froperties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getJDBC_USER() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.jdbc.user");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found froperties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Long getTHREAD_GET_WEIGHTS_CHECKOUT_PERIOD() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "eventmanager.thread.get_weights_task.checkout.period");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found froperties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDB_COLUMNNAME_COMMENTS_REPLIES() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.db.weights.columnname.wcr");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found froperties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDB_COLUMNNAME_LIKES_FAVORITES() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.db.weights.columnname.wlf");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDB_COLUMNNAME_SHARES_RETWEETS() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.db.weights.columnname.wsr");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Long getTHREAD_FORM_EVENTS_CHECKOUT_PERIOD() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "eventmanager.thread.form_events.checkout.period");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getTHREAD_FORM_EVENTS_WORKERS_COUNT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "eventmanager.thread.form_events.workers.count");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Long getTHREAD_REDUCE_EVENTS_CHECKOUT_PERIOD() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "eventmanager.thread.reduce_events.checkout.period");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getTHREAD_REDUCE_EVENTS_WORKERS_COUNT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "eventmanager.thread.reduce_events.workers.count");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Long getTHREAD_UPDATE_STATS_CHECKOUT_PERIOD() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "eventmanager.thread.update_stats.checkout.period");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getTHREAD_UPDATE_STATS_WORKERS_COUNT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "eventmanager.thread.update_stats.workers.count");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getNEO_URL() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.neo.url");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

}
