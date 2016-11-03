package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class RouterProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final RouterProperties instance = new RouterProperties();

	public RouterProperties() {
		super(PROPERTIES_FILE);
	}

	public static RouterProperties getInstance() {
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
			prop = getInstance().getString(PROPERTIES_FILE, "router.redis.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static  Integer getREDIS_PORT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "router.redis.port");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getINCOMING_SDOS_QUEUE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"router.sdo.incoming.jobs.queue.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getPROCESS_SDOS_QUEUE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"router.sdo.process.jobs.queue.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_HOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "router.sdo.garbage.elastic.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getELASTIC_PORT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "router.sdo.garbage.elastic.port");
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
					"router.sdo.garbage.elastic.index.smarty");
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
					"router.sdo.garbage.elastic.type.garbage");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getNUMBER_OF_WORKERS() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "router.workers.number");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Long getPATHS_UPDATE_TASK_FREQUENCY() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE,
					"router.paths.update.task.frequency");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDB_PATH() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "router.embedded.db.path");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDB_DRIVER() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"router.mysql.server.persistence.jdbc.driver");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDB_URL() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"router.mysql.server.persistence.jdbc.url");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDB_USER() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"router.mysql.server.persistence.jdbc.user");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDB_PASSWORD() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"router.mysql.server.persistence.jdbc.password");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDB_TRANSACTION_TYPE() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"router.mysql.server.persistence.transactionType");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}
}
