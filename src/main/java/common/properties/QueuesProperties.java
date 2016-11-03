package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class QueuesProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final QueuesProperties instance = new QueuesProperties();

	public QueuesProperties() {
		super(PROPERTIES_FILE);
	}

	public static QueuesProperties getInstance() {
		return instance;
	}

	public static boolean loadProperties() {

		return getInstance().loadProperties(false);
	}

	public static  boolean loadProperties(boolean refresh) {

		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static String getCACHE_SERVER_HOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "cache.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getCACHE_SERVER_PORT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "cache.port");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getCACHE_EXPIRY_SECONDS() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "cache.expiry.seconds");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getCACHE_PREFIX() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "smg.cache.twt");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getROUTER_INCOMING_QUEUE() {
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

	public static String getEXTERNAl_OUTPUT() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.php.map");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getGNIP_STREAM() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.gnip.stream");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getGNIP_STREAM_HOST() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.gnip.stream.host");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static Integer getGNIP_STREAM_PORT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "queues.gnip.stream.port");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getSMDN_OUTPUT() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.smdn.output");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getNLP() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.nlp");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static  String getTRANSLATE_IN() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.translate.in");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getTRANSLATE_WORKER_SUFFIX() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.translate.worker.suffix");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDP_INPUT() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.smdp.input");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDP_OUTPUT() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.smdp.output");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getSP_INPUT() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.sp.input");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getSAVE() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.save");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getFAILED_JOBS() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.failed.jobs");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getREALTIME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.realtime");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getBACKGROUND_INFORMATION_TAGGING() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.bit");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getBIT_SEARCH_MANAGER() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.bit.sr.mgr");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getBIT_GEOCODE_IN() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.bit.geocode.in");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getBIT_GEOCODE_OUT() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "queues.bit.geocode.out");
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

	public  static Integer getELASTIC_PORT() {
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

	public static Integer getGNIP_WORKERS_THREAD_COUNT() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "queues.gnip.threads.worker.count");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}
	
	public static int getINHOUSE_SENTIMENT_PARSER_WORKERS_NUMBER() {
		Integer prop = null;
		try {
			prop = getInstance().getInt(PROPERTIES_FILE, "inhouse.sentiment.parser.workers.number");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}
	
}