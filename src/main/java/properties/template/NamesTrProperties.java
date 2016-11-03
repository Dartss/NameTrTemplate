package properties.template;

import common.template.manageradaptor.vo.AdaptorSettingsVO;
import common.utils.Constants;
import properties.core.DefaultProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * @author vit
 */
public class NamesTrProperties extends DefaultProperties
{

    private final static String PROPERTIES_FILE = "nameTranslator.properties";
    private static final NamesTrProperties instance = new NamesTrProperties();

    public NamesTrProperties()
    {
	super(PROPERTIES_FILE);
    }

    public static NamesTrProperties getInstance()
    {
	return instance;
    }

    public static boolean loadProperties(boolean refresh)
    {
	return getInstance().loadProperties(PROPERTIES_FILE, refresh);
    }

    private static final String queueHost = "redis.host";
    private static final String queuePort = "redis.port";
    private static final String inputQueueName = "input.queue.name";

    private static final String jdbcUrl = "output.queue.name";
    private static final String jdbcPort = "output.queue.name";
    private static final String jdbcDriver = "output.queue.name";
    private static final String jdbcUser = "output.queue.name";
    private static final String jdbcPassword = "output.queue.name";

    private static final String managerHost = "manager.host";
    private static final String managerPort = "manager.port";
    private static final String managerBindingName = "manager.binding.name";

    /* remote adaptor settings */
    private static final String ADAPTOR_HOST = "remote.adaptor.host";
    private static final String ADAPTOR_PORT = "remote.adaptor.port";
    private static final String ADAPTOR_BINDING_NAME = "remote.adaptor.binding.name";
    private static final String ADAPTOR_NAME = "remote.adaptor.name";
    private static final String ADAPTOR_MAX_WORKERS_COUNT = "maximum.workers.count.per.adaptor";

    private static final String callType = "call.type";

    public static Set<AdaptorSettingsVO> getMOMENTARY_remoteAdaptorsSettings()
    {
	Set<AdaptorSettingsVO> adaptorSettingsVOs = new HashSet<AdaptorSettingsVO>();

	AdaptorSettingsVO adaptorSettingsVO = new AdaptorSettingsVO();
	adaptorSettingsVO.setHost(getAdaptorHost());
	adaptorSettingsVO.setPort(getAdaptorPort());
	adaptorSettingsVO.setBindingName(getAdaptorBindingName());
	adaptorSettingsVO.setName(getAdaptorName());
	adaptorSettingsVO.setMaximumWorkersNumber(getAdaptorMaxWorkersCount());

	adaptorSettingsVOs.add(adaptorSettingsVO);

	return adaptorSettingsVOs;
    }

    public static String getAdaptorHost()
    {
	return getInstance().getString(PROPERTIES_FILE, ADAPTOR_HOST);
    }

    public static int getAdaptorPort()
    {
	return getInstance().getInt(PROPERTIES_FILE, ADAPTOR_PORT);
    }

    public static String getAdaptorName()
    {
	return getInstance().getString(PROPERTIES_FILE, ADAPTOR_NAME);
    }

    public static String getAdaptorBindingName()
    {
	return getInstance().getString(PROPERTIES_FILE, ADAPTOR_BINDING_NAME);
    }

    public static int getAdaptorMaxWorkersCount()
    {
	return getInstance().getInt(PROPERTIES_FILE, ADAPTOR_MAX_WORKERS_COUNT);
    }

    public static String getManagerBindingName()
    {
	return getInstance().getString(PROPERTIES_FILE, managerBindingName);
    }

    public static String getManagerHost()
    {
	return getInstance().getString(PROPERTIES_FILE, managerHost);
    }

    public static int getManagerPort()
    {
	return getInstance().getInt(PROPERTIES_FILE, managerPort);
    }

    public static String getInputQueueName()
    {
	return getInstance().getString(PROPERTIES_FILE, inputQueueName);
    }

    public static String getRedisHost()
    {
	return getInstance().getString(PROPERTIES_FILE, queueHost);
    }

    public static int getRedisPort()
    {
	return getInstance().getInt(PROPERTIES_FILE, queuePort);
    }

    public static String getJdbcUrl() {return getInstance().getString(PROPERTIES_FILE, jdbcUrl); }

    public static String getJdbcPort() {return getInstance().getString(PROPERTIES_FILE, jdbcPort); }

    public static String getJdbcUser() {return getInstance().getString(PROPERTIES_FILE, jdbcUser); }

    public static String getJdbcPassword() {return getInstance().getString(PROPERTIES_FILE, jdbcPassword); }

    public static String getJdbcDriver() {return getInstance().getString(PROPERTIES_FILE, jdbcDriver); }

    public static Constants.CALL_TYPE getCallType()
    {
	String callTypeString = getInstance().getString(PROPERTIES_FILE, callType);
	if (Constants.CALL_TYPE.RMI.name().equalsIgnoreCase(callTypeString))
	{
	    return Constants.CALL_TYPE.RMI;
	} else if (Constants.CALL_TYPE.LOCAL.name().equalsIgnoreCase(callTypeString))
	{
	    return Constants.CALL_TYPE.LOCAL;
	} else
	{
	    return null;
	}
    }

}