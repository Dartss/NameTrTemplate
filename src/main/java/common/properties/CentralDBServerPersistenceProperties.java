package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class CentralDBServerPersistenceProperties extends DefaultProperties {

	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final CentralDBServerPersistenceProperties instance = new CentralDBServerPersistenceProperties();

	public CentralDBServerPersistenceProperties() {
		super(PROPERTIES_FILE);
	}

	public static CentralDBServerPersistenceProperties getInstance() {
		return instance;
	}
	public static  boolean loadProperties() {

		return getInstance().loadProperties(false);
	}

	public static boolean loadProperties(boolean refresh) {

		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static String getDB_DRIVER() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"mysql.server.persistence.jdbc.driver");
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
					"mysql.server.persistence.jdbc.url");
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
					"mysql.server.persistence.jdbc.user");
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
					"mysql.server.persistence.jdbc.password");
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
					"mysql.server.persistence.transactionType");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getHIBERNATE_DIALECT() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "mysql.server.hibernate.dialect");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getHIBERNATE_SHOW_SQL() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "mysql.server.hibernate.show_sql");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getHIBERNATE_HBM2DDL_AUTO() {
		String prop = null;
		try {
			prop =getInstance().getString(PROPERTIES_FILE,
					"mysql.server.hibernate.hbm2ddl.auto");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getENTITIES_STOP_WORDS_TABLE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"entities.filtering.mysql.table.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getKEYWORDS_STOP_WORDS_TABLE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"keywords.filtering.mysql.table.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getACTIONS_STOP_WORDS_TABLE_NAME() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE,
					"actions.filtering.mysql.table.name");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

	public static String getSTOP_WORDS_EXCEL_DOCUMENT_PATH() {
		String prop = null;
		try {
			prop = getInstance().getString(PROPERTIES_FILE, "stop_words_excel_document_path");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "not found properties file: "
					+ PROPERTIES_FILE);
		}
		return prop;
	}

}
