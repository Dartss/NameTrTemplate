package common.properties.template;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;
import jsmarty.core.common.template.manageradaptor.vo.AdaptorSettingsVO;
import jsmarty.core.common.utils.Constants.CALL_TYPE;

/**
 * 
 * @author vit
 *
 */
public class XXX_Properties extends DefaultProperties {

	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static final XXX_Properties instance = new XXX_Properties();

	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public XXX_Properties() {
		super(PROPERTIES_FILE);
	}

	public static XXX_Properties getInstance() {
		return instance;
	}

	public static boolean loadProperties(boolean refresh) {
		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	private static final String queueHost = "redis.host";
	private static final String queuePort = "redis.port";
	private static final String inputQueueName = "input.queue.name";
	private static final String outputQueueName = "output.queue.name";

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

	public static Set<AdaptorSettingsVO>  getMOMENTARY_remoteAdaptorsSettings() {
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

	public static String getManagerBindingName() {
		return getInstance().getString(PROPERTIES_FILE, managerBindingName);
	}

	public static String getManagerHost() {
		return getInstance().getString(PROPERTIES_FILE, managerHost);
	}

	public static int getManagerPort() {
		return getInstance().getInt(PROPERTIES_FILE, managerPort);
	}

	public static String getInputQueueName() {
		return getInstance().getString(PROPERTIES_FILE, inputQueueName);
	}

	public static String getOutputQueueName() {
		return getInstance().getString(PROPERTIES_FILE, outputQueueName);
	}

	public static String getRedisHost() {
		return getInstance().getString(PROPERTIES_FILE, queueHost);
	}

	public static int getRedisPort() {
		return getInstance().getInt(PROPERTIES_FILE, queuePort);
	}

	public static CALL_TYPE getCallType() {
		String callTypeString = getInstance().getString(PROPERTIES_FILE, callType);
		if (CALL_TYPE.RMI.name().equalsIgnoreCase(callTypeString)) {
			return CALL_TYPE.RMI;
		} else if (CALL_TYPE.LOCAL.name().equalsIgnoreCase(callTypeString)) {
			return CALL_TYPE.LOCAL;
		} else {
			return null;
		}
	}

}