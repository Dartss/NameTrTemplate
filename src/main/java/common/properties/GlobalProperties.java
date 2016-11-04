package common.properties;

import common.properties.core.DefaultProperties;

import java.util.logging.Logger;

public class GlobalProperties extends DefaultProperties
{
    private final static String PROPERTIES_FILE = "nameTranslator.properties";
    //
    private static final GlobalProperties instance = new GlobalProperties();
    private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static String GNIP_STREAM_NAME = "gnip.stream.name";
    private static String GNIP_ACCOUNT = "gnip.account";
    private static String GNIP_USERNAME = "gnip.username";
    private static String GNIP_PASSWORD = "gnip.password";

    /* REDIS */
    private static String REDIS_POOL_MIN_IDLE = "redis.pool.min.idle";
    private static String REDIS_POOL_MAX_IDLE = "redis.pool.max.idle";
    private static String REDIS_POOL_MAX_TOTAL = "redis.pool.max.total";
    private static String REDIS_POOL_MAX_WAIT_MILLIS = "redis.pool.max.wait.millis";

    private static String MIN_EVICTABLE_IDLE_TIME_MILLIS = "redis.pool.min.evictable.idle.time.millis";
    private static String TIME_BETWEEN_EVICTION_RUNS_MILLIS = "redis.pool.min.time.between.eviction.runs.millis";

    /* SDO */
    private static String SDO_EXPIRY_TIME = "sm.sdo.expirytime";

    //
    //
    public GlobalProperties() {
	super(PROPERTIES_FILE);
    }

    public static GlobalProperties getInstance()
    {
	return instance;
    }

    public static boolean loadProperties(boolean refresh)
    {
	return getInstance().loadProperties(PROPERTIES_FILE, refresh);
    }

    public static String getGNIP_STREAM_NAME()
    {
	return getInstance().getString(PROPERTIES_FILE, GNIP_STREAM_NAME);
    }

    public static String getGNIP_ACCOUNT()
    {
	return getInstance().getString(PROPERTIES_FILE, GNIP_ACCOUNT);
    }

    public static String getGNIP_USERNAME()
    {
	return getInstance().getString(PROPERTIES_FILE, GNIP_USERNAME);
    }

    public static String getGNIP_PASSWORD()
    {
	return getInstance().getString(PROPERTIES_FILE, GNIP_PASSWORD);
    }

    public static int getREDIS_POOL_MAX_IDLE()
    {
	return getInstance().getInt(PROPERTIES_FILE, REDIS_POOL_MAX_IDLE);
    }

    public static int getREDIS_POOL_MAX_TOTAL()
    {
	return getInstance().getInt(PROPERTIES_FILE, REDIS_POOL_MAX_TOTAL);
    }

    public static long getREDIS_POOL_MAX_WAIT_MILLIS()
    {
	return getInstance().getLong(PROPERTIES_FILE, REDIS_POOL_MAX_WAIT_MILLIS);
    }

    public static int getREDIS_POOL_MIN_IDLE()
    {
	return getInstance().getInt(PROPERTIES_FILE, REDIS_POOL_MIN_IDLE);
    }

    public static long getMIN_EVICTABLE_IDLE_TIME_MILLIS()
    {
	return getInstance().getLong(PROPERTIES_FILE, MIN_EVICTABLE_IDLE_TIME_MILLIS);
    }

    public static long getTIME_BETWEEN_EVICTION_RUNS_MILLIS()
    {
	return getInstance().getLong(PROPERTIES_FILE, TIME_BETWEEN_EVICTION_RUNS_MILLIS);
    }

    /* SDO */
    public static long getSDO_EXPIRY_TIME()
    {
	return getInstance().getLong(PROPERTIES_FILE, SDO_EXPIRY_TIME, 120000L); //2minutes
    }

}