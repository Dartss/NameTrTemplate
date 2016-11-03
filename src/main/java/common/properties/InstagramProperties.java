package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class InstagramProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final InstagramProperties instance = new InstagramProperties();

	public InstagramProperties() {
		super(PROPERTIES_FILE);
	}

	public static InstagramProperties getInstance() {
		return instance;
	}

	public static boolean loadProperties() {

		return getInstance().loadProperties(false);
	}

	public static boolean loadProperties(boolean refresh) {

		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static String getJOBS_QUEUE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"instagram.fetcher.jobs.queue.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getOUTPUT_QUEUE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"instagram.fetcher.output.queue.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getWRONG_JOB_QUEUE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"instagram.fetcher.wrong.job.queue.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getREDIS_QUEUE_HOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"instagram.fetcher.jobs.queue.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getREDIS_QUEUE_PORT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "instagram.fetcher.jobs.queue.port");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getMAXIMUM_JOBS_NUMBER_PER_MINUTE() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE,
					"instagram.fetcher.maximum.jobs.number");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getINSTAGRAM_API_URL() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "instagram.url");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getINSTAGRAM_VALID_TOKENS_QUEUE() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "instagram.valid.tokens.queue");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getINSTAGRAM_RECHARGING_TOKENS_QUEUE() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"instagram.recharging.tokens.queue");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getUSER_VALIDITY_TEST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "instagram.user.validity.test");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

}
