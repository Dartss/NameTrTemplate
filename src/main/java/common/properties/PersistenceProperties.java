package common.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class PersistenceProperties extends DefaultProperties
{
    private final static String PROPERTIES_FILE = "JSmarty.properties";
    private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final PersistenceProperties instance = new PersistenceProperties();

    public PersistenceProperties() {
	super(PROPERTIES_FILE);
    }

    public PersistenceProperties(String propertyFileName) {
	super(propertyFileName);
	super.loadProperties(PROPERTIES_FILE);
    }

    public static PersistenceProperties getInstance()
    {
	return instance;
    }

    //
    //
    public static Map<String, String> getDbProperties()
    {
	Map<String, String> dbProperties = null;
	try
	{
	    dbProperties = new HashMap<String, String>();
	    dbProperties.put("hibernate.show_sql", getHIBERNATE_SHOW_SQL());
	    dbProperties.put("hibernate.dialect", getHIBERNATE_DIALECT());
	    dbProperties.put("hibernate.hbm2ddl.auto", getHIBERNATE_HBM2DDL_AUTO());
	} catch (Exception e)
	{
	    LOGGER.log(Level.SEVERE, "properties file not found : " + PROPERTIES_FILE, e);
	}
	return dbProperties;
    }

    public static String getHIBERNATE_DIALECT()
    {
	return getInstance().getString("sm.default.db.orm.hibernate.dialect");
    }

    public static String getHIBERNATE_SHOW_SQL()
    {
	return getInstance().getString("sm.default.db.orm.hibernate.showSql");
    }

    public static String getHIBERNATE_HBM2DDL_AUTO()
    {
	return getInstance().getString("sm.default.db.orm.hibernate.hbm2ddlAuto");
    }

}
