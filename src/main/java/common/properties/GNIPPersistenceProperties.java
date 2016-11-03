package common.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class GNIPPersistenceProperties extends DefaultProperties {
	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final GNIPPersistenceProperties instance = new GNIPPersistenceProperties();

	public GNIPPersistenceProperties() {
		super(PROPERTIES_FILE);
	}

	public static GNIPPersistenceProperties getInstance() {
		return instance;
	}

	public  static boolean loadProperties() {

		return getInstance().loadProperties(false);
	}

	public static boolean loadProperties(boolean refresh) {

		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static Map<String, String> getDbProperties() {
		Map<String, String> dbProperties = null;
		try {
			dbProperties = new HashMap<String, String>();
			dbProperties.put("hibernate.show_sql", getHIBERNATE_SHOW_SQL());
			dbProperties.put("javax.persistence.transactionType", getDB_TRANSACTION_TYPE());
			dbProperties.put("javax.persistence.jdbc.driver", getDB_DRIVER());
			dbProperties.put("javax.persistence.jdbc.url", getDB_URL());
			dbProperties.put("javax.persistence.jdbc.user", getDB_USER());
			dbProperties.put("javax.persistence.jdbc.password", getDB_PASSWORD());
			dbProperties.put("hibernate.dialect", getHIBERNATE_DIALECT());
			dbProperties.put("hibernate.hbm2ddl.auto", getHIBERNATE_HBM2DDL_AUTO());
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return dbProperties;
	}

	public static String getDB_DRIVER() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "persistence.jdbc.driver");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDB_URL() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "persistence.jdbc.url");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDB_USER() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "persistence.jdbc.user");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDB_PASSWORD() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "persistence.jdbc.password");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getDB_TRANSACTION_TYPE() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "persistence.transactionType");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getHIBERNATE_DIALECT() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "hibernate.dialect");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getHIBERNATE_SHOW_SQL() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "hibernate.show_sql");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getHIBERNATE_HBM2DDL_AUTO() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "hibernate.hbm2ddl.auto");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}

}
