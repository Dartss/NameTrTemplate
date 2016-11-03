package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class IparserProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final IparserProperties instance = new IparserProperties();

	public IparserProperties() {
		super(PROPERTIES_FILE);
	}

	public static IparserProperties getInstance() {
		return instance;
	}

	public static boolean loadProperties() {
		return getInstance().loadProperties(false);
	}

	public static boolean loadProperties(boolean refresh) {

		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static String getINPUT_QUEUE_SERVER_HOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "iparser.input.queue.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getINPUT_QUEUE_SERVER_PORT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "iparser.input.queue.port");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getINPUT_QUEUE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "iparser.input.queue.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getOUTPUT_QUEUE_SERVER_HOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "iparser.output.stream.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getOUTPUT_QUEUE_SERVER_PORT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "iparser.output.stream.port");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getOUTPUT_QUEUE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "iparser.output.stream.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getCHECKOUT_PERIOD() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "iparser.checkout.period");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getNUMBER_OF_WORKERS() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "iparser.max.work.count");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getWORKING_THREADS_COUNT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "iparser.working.job.threads");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

}
