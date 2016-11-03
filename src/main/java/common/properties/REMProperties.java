package common.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class REMProperties extends DefaultProperties
{
    private final static String PROPERTIES_FILE = "JSmarty.properties";
    private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final REMProperties instance = new REMProperties();

    public REMProperties() {
	super(PROPERTIES_FILE);
    }

    public static REMProperties getInstance()
    {
	return instance;
    }

    public static boolean loadProperties()
    {

	return getInstance().loadProperties(false);
    }

    public static boolean loadProperties(boolean refresh)
    {

	return getInstance().loadProperties(PROPERTIES_FILE, refresh);
    }

    public static String getREDIS_HOST()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "rem.redis.host");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static Integer getREDIS_PORT()
    {
	Integer prop = null;
	try
	{
	    prop = getInstance().getInt(PROPERTIES_FILE, "rem.redis.port");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getREDIS_QUEUE_FORM_EVENTS()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "eventmanager.redis.queue.form_events");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static Integer getNUMBER_OF_WORKERS()
    {
	Integer prop = null;
	try
	{
	    prop = getInstance().getInt(PROPERTIES_FILE, "rem.workers.number");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getDB_PATH()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "rem.embedded.db.path");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getGENERIC_REAL_TIME_QUEUE_NAME()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "generic.rem.sdo.jobs.realtime.queue.name");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getGENERIC_SHORT_TERM_QUEUE_NAME()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "generic.rem.sdo.jobs.shortterm.queue.name");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getGENERIC_MID_TERM_QUEUE_NAME()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "generic.rem.sdo.jobs.midterm.queue.name");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getGENERIC_LONG_TERM_QUEUE_NAME()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "generic.rem.sdo.jobs.longterm.queue.name");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getPRECISE_REAL_TIME_QUEUE_NAME()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "precise.rem.sdo.jobs.realtime.queue.name");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getPRECISE_SHORT_TERM_QUEUE_NAME()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "precise.rem.sdo.jobs.shortterm.queue.name");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getPRECISE_MID_TERM_QUEUE_NAME()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "precise.rem.sdo.jobs.midterm.queue.name");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getPRECISE_LONG_TERM_QUEUE_NAME()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "precise.rem.sdo.jobs.longterm.queue.name");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static Long getQUERIES_UPDATE_TASK_FREQUENCY()
    {
	Long prop = null;
	try
	{
	    prop = getInstance().getLong(PROPERTIES_FILE, "rem.queries.update.task.frequency");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static Long getGENERIC_SHORT_TERM_QUEUE_FREQUENCY()
    {
	Long prop = null;
	try
	{
	    prop = getInstance().getLong(PROPERTIES_FILE, "generic.rem.sdo.jobs.shortterm.frequency");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static Long getGENERIC_MID_TERM_QUEUE_FREQUENCY()
    {
	Long prop = null;
	try
	{
	    prop = getInstance().getLong(PROPERTIES_FILE, "generic.rem.sdo.jobs.midterm.frequency");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static Long getGENERIC_LONG_TERM_QUEUE_FREQUENCY()
    {
	Long prop = null;
	try
	{
	    prop = getInstance().getLong(PROPERTIES_FILE, "generic.rem.sdo.jobs.longterm.frequency");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static Long getGENERIC_MAX_SDO_LIFETIME()
    {
	Long prop = null;
	try
	{
	    prop = getInstance().getLong(PROPERTIES_FILE, "generic.rem.sdo.jobs.maximum.sdo.lifetime");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static Long getPRECISE_SHORT_TERM_QUEUE_FREQUENCY()
    {
	Long prop = null;
	try
	{
	    prop = getInstance().getLong(PROPERTIES_FILE, "generic.rem.sdo.jobs.shortterm.frequency");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static Long getPRECISE_MID_TERM_QUEUE_FREQUENCY()
    {
	Long prop = null;
	try
	{
	    prop = getInstance().getLong(PROPERTIES_FILE, "generic.rem.sdo.jobs.midterm.frequency");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static Long getPRECISE_LONG_TERM_QUEUE_FREQUENCY()
    {
	Long prop = null;
	try
	{
	    prop = getInstance().getLong(PROPERTIES_FILE, "generic.rem.sdo.jobs.longterm.frequency");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getDB_DRIVER()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "rem.mysql.server.persistence.jdbc.driver");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getDB_URL()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "rem.mysql.server.persistence.jdbc.url");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getDB_USER()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "rem.mysql.server.persistence.jdbc.user");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getDB_PASSWORD()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "rem.mysql.server.persistence.jdbc.password");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getDB_TRANSACTION_TYPE()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "rem.mysql.server.persistence.transactionType");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static Long getPATHS_UPDATE_TASK_FREQUENCY()
    {
	Long prop = null;
	try
	{
	    prop = getInstance().getLong(PROPERTIES_FILE, "generic.rem.db.update.frequency");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getNEO_HOST()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "rem.neo.host");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getNEO_USER()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "rem.neo.user");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getNEO_PASSWORD()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "rem.neo.password");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getKLANGOO_API_KEY()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "ekp.klangoo.api.key");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static int getKLANGOO_API_DAILY_LIMIT()
    {
	Integer prop = null;
	try
	{
	    prop = getInstance().getInt(PROPERTIES_FILE, "ekp.klangoo.maximum.daily.limits");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

    public static String getKLANGOO_REAL_TIME_QUEUE_NAME()
    {
	String prop = null;
	try
	{
	    prop = getInstance().getString(PROPERTIES_FILE, "klangoo.rem.sdo.jobs.realtime.queue.name");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, "not found properties file: " + PROPERTIES_FILE);
	}
	return prop;
    }

}
