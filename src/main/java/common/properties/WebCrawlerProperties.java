package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class WebCrawlerProperties extends DefaultProperties {

	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final WebCrawlerProperties instance = new WebCrawlerProperties();
	
	private static final String DEV_MODE = "crawler.faucet.dev.mode";
	private static final String DEV_REDIS_HOST = "crawler.faucet.dev.redis.queue.host";
	private static final String DEV_REDIS_PORT = "crawler.faucet.dev.redis.queue.port";

	public WebCrawlerProperties() {
		super(PROPERTIES_FILE);
	}

	public static WebCrawlerProperties getInstance() {
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
			prop = getInstance().getString(PROPERTIES_FILE, "crawler.faucet.redis.queue.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getREDIS_PORT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "crawler.faucet.redis.queue.port");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getREDIS_INPUT_QUEUE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"crawler.faucet.redis.input.queue.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getREDIS_OUTPUT_QUEUE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"crawler.faucet.redis.output.queue.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getJOBS_NUMBER() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "crawler.faucet.flow");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static  Long getFREQUENCY() {
		Long prop = null;
		try {
			prop = getInstance().getLong(PROPERTIES_FILE, "crawler.faucet.frequency");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getELASTIC_HOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "crawler.faucet.elastic.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getELASTIC_PORT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "crawler.faucet.elastic.port");
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
					"crawler.faucet.elastic.index.smarty");
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
					"crawler.faucet.elastic.type.garbage");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static boolean isDEV_MODE() {
		return getInstance().getBoolean(DEV_MODE);
	}

	public static String getDEV_REDIS_HOST() {
		return getInstance().getString(DEV_REDIS_HOST);
	}

	public static int getDEV_REDIS_PORT() {
		return getInstance().getInt(DEV_REDIS_PORT);
	}

}
