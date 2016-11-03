package common.properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class EKPProperties extends DefaultProperties
{

    private final static String PROPERTIES_FILE = "JSmarty.properties";
    private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final EKPProperties instance = new EKPProperties();

    // rmi
    private static String MANAGER_HOST = "ekp.rmi.manager.host";
    private static String MANAGER_PORT = "ekp.rmi.manager.port";
    private static String MANAGER_BINDING_NAME = "ekp.manager.binding.name";

    private static String RMI_WORKER_ADAPTOR_HOST = "ekp.rmi.worker.adaptor.host";
    private static String RMI_WORKER_ADAPTOR_PORT = "ekp.rmi.worker.adaptor.port";
    private static String RMI_NAMING_LOOKUP = "ekp.rmi.naming.lookup";
    private static String WORKER_ADAPTOR_BINDING_NAME = "ekp.worker.adaptor.binding.name";
    private static String MAXIMUM_WORKER_THREADS = "ekp.worker.adaptor.maximum.worker.threads";

    // redis
    private static String REDIS_HOST = "ekp.redis.host";
    private static String REDIS_PORT = "ekp.redis.port";
    private static String INPUT_QUEUE = "ekp.redis.input.queue";
    private static String PULL_SDO_SLEEP_TIME = "ekp.pull.sdo.sleep.time";
    private static String OUTPUT_QUEUE = "ekp.redis.output.queue";

    private static String EKP_CALL_TYPE_OPTION = "ekp.call.type.option";
    private static String PARSER_PRIORITY_OPTION = "ekp.manager.priority.option";
    private static String PARSERS = "ekp.parsers";
    private static String BALANCE_RESET_CRON_MODE = "ekp.balance.reset.cron.mode";

    public EKPProperties() {
	super(PROPERTIES_FILE);
    }

    public static EKPProperties getInstance()
    {
	return instance;
    }

    public static boolean loadProperties(boolean refresh)
    {
	return getInstance().loadProperties(PROPERTIES_FILE, refresh);
    }

    public static List<String> getPARSERS()
    {
	return getInstance().getStringArrayList(PARSERS);
    }

    public static Map<String, String> getPARSERS_CLASSES_NAMES()
    {
	Map<String, String> PARSERS_CLASSES_NAMES = new HashMap<String, String>();
	for (String parser : getPARSERS())
	{
	    PARSERS_CLASSES_NAMES.put(parser, getPARSERS_CLASSES_FULL_NAMES(parser));
	}
	return PARSERS_CLASSES_NAMES;
    }

    public static Map<String, String> getPARSERS_API_KEYS()
    {
	Map<String, String> PARSERS_API_KEYS = new HashMap<String, String>();
	for (String parser : getPARSERS())
	{
	    PARSERS_API_KEYS.put(parser, getPARSERS_API_KEYS(parser));
	}
	return PARSERS_API_KEYS;
    }

    public static List<String> getPARSER_SUPPORTED_LANGUAGES(String parserName)
    {
	return getInstance().getStringArrayList("ekp." + parserName + ".parser.supported.languages");
    }

    public static String getPARSERS_CLASSES_FULL_NAMES(String parserName)
    {
	return getInstance().getString("ekp." + parserName + ".parser.class.path");
    }

    public static int getPARSERS_AVERAGE_CALLS_COUNTS(String parserName)
    {
	return getInstance().getInt("ekp." + parserName + ".normal.average.call.count");
    }

    public static String getPARSERS_API_KEYS(String parserName)
    {
	return getInstance().getString("ekp." + parserName + ".api.key");
    }

    public static String getPARSER_PRIORITY_OPTION()
    {
	return getInstance().getString(PARSER_PRIORITY_OPTION);
    }

    public static Long getPULL_SDO_SLEEP_TIME()
    {
	return getInstance().getLong(PULL_SDO_SLEEP_TIME);
    }

    public static String getMAXIMUM_ALLOWED_CONCURRENT_CONNECTIONS(String parser)
    {
	return getInstance().getString("ekp." + parser + ".maximum.allowed.concurrent.connections.count");
    }

    public static String getMAXIMUM_DAILY_LIMITS(String parser)
    {
	return getInstance().getString("ekp." + parser + ".maximum.daily.limits");
    }

    public static String getMAXIMUM_MONTHLY_LIMITS(String parser)
    {
	return getInstance().getString("ekp." + parser + ".maximum.monthly.limits");
    }

    public static String getEKP_CALL_TYPE_OPTION()
    {
	return getInstance().getString(EKP_CALL_TYPE_OPTION);
    }

    public static String getRMI_NAMING_LOOKUP()
    {
	return getInstance().getString(RMI_NAMING_LOOKUP);
    }

    public static String getREDIS_HOST()
    {
	return getInstance().getString(REDIS_HOST);
    }

    public static int getREDIS_PORT()
    {
	return getInstance().getInt(REDIS_PORT);
    }

    public static String getINPUT_QUEUE()
    {
	return getInstance().getString(INPUT_QUEUE);
    }

    public static String getRMI_WORKER_ADAPTOR_HOST()
    {
	return getInstance().getString(RMI_WORKER_ADAPTOR_HOST);
    }

    public static int getRMI_WORKER_ADAPTOR_PORT()
    {
	return getInstance().getInt(RMI_WORKER_ADAPTOR_PORT);
    }

    public static String getOUTPUT_QUEUE()
    {
	return getInstance().getString(OUTPUT_QUEUE);
    }

    public static String getWORKER_ADAPTOR_BINDING_NAME()
    {
	return getInstance().getString(WORKER_ADAPTOR_BINDING_NAME);
    }

    public static int getMAXIMUM_WORKER_THREADS()
    {
	return getInstance().getInt(MAXIMUM_WORKER_THREADS);
    }

    public static String getMANAGER_HOST()
    {
	return getInstance().getString(MANAGER_HOST);
    }

    public static int getMANAGER_PORT()
    {
	return getInstance().getInt(MANAGER_PORT);
    }

    public static String getMANAGER_BINDING_NAME()
    {
	return getInstance().getString(MANAGER_BINDING_NAME);
    }

    public static boolean isBALANCE_RESET_CRON_MODE()
    {
	return getInstance().getBoolean(BALANCE_RESET_CRON_MODE);
    }

}