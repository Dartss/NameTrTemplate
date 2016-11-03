package common.properties;

import java.util.HashSet;
import java.util.Set;

import jsmarty.core.common.properties.core.DefaultProperties;
import jsmarty.core.common.template.manageradaptor.vo.AdaptorSettingsVO;
import jsmarty.core.common.utils.Constants.CALL_TYPE;

public class SaveSdoProperties extends DefaultProperties
{

    private final static String PROPERTIES_FILE = "JSmarty.properties";
    private static final SaveSdoProperties instance = new SaveSdoProperties();

    private static final String MANAGER_HOST = "sm.savesdo.manager.host";
    private static final String MANAGER_PORT = "sm.savesdo.manager.port";
    private static final String MANAGER_CALL_TYPE = "sm.savesdo.manager.call_type";
    private static final String MANAGER_BINDING_NAME = "sm.savesdo.manager_binding_name";

    private static final String IS_STATS_QUEUER_ENABLED = "sm.savesdo.enable_stats_queuer";

    private static final String QUEUER_HOST = "sm.savesdo.queuer.host";
    private static final String QUEUER_PORT = "sm.savesdo.queuer.port";
    private static final String QUEUER_SAVE_SDO_JOBS_QUEUE_NAME = "sm.savesdo.queuer.input.queue.name";
    private static final String QUEUER_ERROR_JOBS_QUEUE_NAME = "sm.savesdo.queuer.error_jobs.queue.name";
    private static final String QUEUER_NLP_UUIDS_QUEUE_NAME = "sm.savesdo.queuer.nlp_uuids.queue.name";
    private static final String QUEUER_NLP_ACTIONS_JOBS_QUEUE_NAME = "sm.savesdo.queuer.nlp_actions_jobs.queue.name";
    private static final String QUEUER_NLP_CATEGORIES_JOBS_QUEUE_NAME = "sm.savesdo.queuer.nlp_categories_jobs.queue.name";
    private static final String QUEUER_NLP_ENTITIES_JOBS_QUEUE_NAME = "sm.savesdo.queuer.nlp_entities_jobs.queue.name";
    private static final String QUEUER_NLP_KEYWORDS_JOBS_QUEUE_NAME = "sm.savesdo.queuer.nlp_keywords_jobs.queue.name";
    private static final String QUEUER_NLP_LOCATIONS_JOBS_QUEUE_NAME = "sm.savesdo.queuer.nlp_locations_jobs.queue.name";
    private static final String QUEUER_REM_JOBS_QUEUE_NAME = "sm.savesdo.queuer.nextJobsQueue.queue.name";

    private static final String ELASTIC_HOST = "sm.savesdo.elastic.host";
    private static final String ELASTIC_PORT = "sm.savesdo.elastic.port";
    private static final String ELASTIC_SMARTY_INDEX_NAME = "sm.savesdo.elastic.index.smarty.name";
    private static final String ELASTIC_SMARTYJOBS_INDEX_NAME = "sm.savesdo.elastic.index.smartyjobs.name";
    private static final String ELASTIC_SDO_TYPE_NAME = "sm.savesdo.elastic.type.sdo.name";
    private static final String ELASTIC_GARBAGE_TYPE_NAME = "sm.savesdo.elastic.type.garbage.name";

    private static final String GRAPH_URL = "sm.savesdo.graph.url";
    private static final String GRAPH_USERNAME = "sm.savesdo.graph.user";
    private static final String GRAPH_PASSWORD = "sm.savesdo.graph.password";

    private static final String THREAD_NODE_TYPE_ACTION = "sm.savesdo.thread.node.type.action";
    private static final String THREAD_NODE_TYPE_ENTITY = "sm.savesdo.thread.node.type.entity";
    private static final String THREAD_NODE_TYPE_KEYWORD = "sm.savesdo.thread.node.type.keyword";
    private static final String THREAD_NODE_TYPE_LOCATION = "sm.savesdo.thread.node.type.location";
    private static final String THREAD_NODE_TYPE_CATEGORY = "sm.savesdo.thread.node.type.category";

    private static final String SDO_PROVIDER_TYPE_TWEET = "sm.savesdo.sdo.provider.tweet";
    private static final String SDO_PROVIDER_TYPE_FACEBOOK_POST = "sm.savesdo.sdo.provider.facebook_post";
    private static final String SDO_PROVIDER_TYPE_WEB_CONTENT = "sm.savesdo.sdo.provider.web_content";

    /* SDO SAVER ADAPTOR */
    private static final String SDO_SAVER_ADAPTOR_HOST = "sm.savesdo.sdo_saver_adaptor.host";
    private static final String SDO_SAVER_ADAPTOR_PORT = "sm.savesdo.sdo_saver_adaptor.port";
    private static final String SDO_SAVER_ADAPTOR_BINDING_NAME = "sm_savesdo.sdo_saver_adaptor.binding_name";
    private static final String SDO_SAVER_ADAPTOR_NAME = "sm_savesdo.sdo_saver_adaptor.name";
    private static final String SDO_SAVER_ADAPTOR_CALL_TYPE = "sm.savesdo.sdo_saver_adaptor.call_type";
    private static final String SDO_SAVER_ADAPTOR_MAX_WORKERS_COUNT = "sm.savesdo.sdo_saver_adaptor.max_workers_count";

    /* NLP SAVER ADAPTOR */
    private static final String NLP_SAVER_ADAPTOR_HOST = "sm.savesdo.nlp_saver_adaptor.host";
    private static final String NLP_SAVER_ADAPTOR_PORT = "sm.savesdo.nlp_saver_adaptor.port";
    private static final String NLP_SAVER_ADAPTOR_BINDING_NAME = "sm_savesdo.nlp_saver_adaptor.binding_name";
    private static final String NLP_SAVER_ADAPTOR_NAME = "sm_savesdo.nlp_saver_adaptor.name";
    private static final String NLP_SAVER_ADAPTOR_CALL_TYPE = "sm.savesdo.nlp_saver_adaptor.call_type";
    private static final String NLP_SAVER_ADAPTOR_MAX_WORKERS_COUNT = "sm.savesdo.nlp_saver_adaptor.max_workers_count";

    public SaveSdoProperties() {
	super(PROPERTIES_FILE);
    }

    public static SaveSdoProperties getInstance()
    {
	return instance;
    }

    public static boolean loadProperties(boolean refresh)
    {
	return getInstance().loadProperties(PROPERTIES_FILE, refresh);
    }

    public static String getManagerHost()
    {
	return getInstance().getString(PROPERTIES_FILE, MANAGER_HOST);
    }

    public static int getManagerPort()
    {
	return getInstance().getInt(PROPERTIES_FILE, MANAGER_PORT);
    }

    public static CALL_TYPE getManagerCallType()
    {
	String callTypeStr = getInstance().getString(PROPERTIES_FILE, MANAGER_CALL_TYPE);
	if (callTypeStr.equalsIgnoreCase(CALL_TYPE.RMI.name()))
	{
	    return CALL_TYPE.RMI;
	} else if (callTypeStr.equalsIgnoreCase(CALL_TYPE.LOCAL.name()))
	{
	    return CALL_TYPE.LOCAL;
	}
	return null;
    }

    public static String getManagerBindingName()
    {
	return getInstance().getString(PROPERTIES_FILE, MANAGER_BINDING_NAME);
    }

    public static String getQueuerHost()
    {
	return getInstance().getString(PROPERTIES_FILE, QUEUER_HOST);
    }

    public static int getQueuerPort()
    {
	return getInstance().getInt(PROPERTIES_FILE, QUEUER_PORT);
    }

    public static String getQueuerSaveSdoJobsQueueName()
    {
	return getInstance().getString(PROPERTIES_FILE, QUEUER_SAVE_SDO_JOBS_QUEUE_NAME);
    }

    public static String getQueuerErrorJobsQueueName()
    {
	return getInstance().getString(PROPERTIES_FILE, QUEUER_ERROR_JOBS_QUEUE_NAME);
    }

    public static String getQueuerNlpActionsJobsQueueName()
    {
	return getInstance().getString(PROPERTIES_FILE, QUEUER_NLP_ACTIONS_JOBS_QUEUE_NAME);
    }

    public static String getQueuerNlpCategoriesJobsQueueName()
    {
	return getInstance().getString(PROPERTIES_FILE, QUEUER_NLP_CATEGORIES_JOBS_QUEUE_NAME);
    }

    public static String getQueuerNlpEntitiesJobsQueueName()
    {
	return getInstance().getString(PROPERTIES_FILE, QUEUER_NLP_ENTITIES_JOBS_QUEUE_NAME);
    }

    public static String getQueuerNlpKeywordsJobsQueueName()
    {
	return getInstance().getString(PROPERTIES_FILE, QUEUER_NLP_KEYWORDS_JOBS_QUEUE_NAME);
    }

    public static String getQueuerNlpLocationsJobsQueueName()
    {
	return getInstance().getString(PROPERTIES_FILE, QUEUER_NLP_LOCATIONS_JOBS_QUEUE_NAME);
    }

    public static String getQueuerRemJobsQueueName()
    {
	return getInstance().getString(PROPERTIES_FILE, QUEUER_REM_JOBS_QUEUE_NAME);
    }

    public static String getElasticHost()
    {
	return getInstance().getString(PROPERTIES_FILE, ELASTIC_HOST);
    }

    public static int getElasticPort()
    {
	return getInstance().getInt(PROPERTIES_FILE, ELASTIC_PORT);
    }

    public static String getElasticSmartyIndexName()
    {
	return getInstance().getString(PROPERTIES_FILE, ELASTIC_SMARTY_INDEX_NAME);
    }

    public static String getElasticSdoTypeName()
    {
	return getInstance().getString(PROPERTIES_FILE, ELASTIC_SDO_TYPE_NAME);
    }

    public static String getElasticGarbageTypeName()
    {
	return getInstance().getString(PROPERTIES_FILE, ELASTIC_GARBAGE_TYPE_NAME);
    }

    public static String getGraphUrl()
    {
	return getInstance().getString(PROPERTIES_FILE, GRAPH_URL);
    }

    public static String getGraphUsername()
    {
	return getInstance().getString(PROPERTIES_FILE, GRAPH_USERNAME);
    }

    public static String getGraphPassword()
    {
	return getInstance().getString(PROPERTIES_FILE, GRAPH_PASSWORD);
    }

    public static String getThreadNodeTypeAction()
    {
	return getInstance().getString(PROPERTIES_FILE, THREAD_NODE_TYPE_ACTION);
    }

    public static String getThreadNodeTypeEntity()
    {
	return getInstance().getString(PROPERTIES_FILE, THREAD_NODE_TYPE_ENTITY);
    }

    public static String getThreadNodeTypeKeyword()
    {
	return getInstance().getString(PROPERTIES_FILE, THREAD_NODE_TYPE_KEYWORD);
    }

    public static String getThreadNodeTypeLocation()
    {
	return getInstance().getString(PROPERTIES_FILE, THREAD_NODE_TYPE_LOCATION);
    }

    public static String getThreadNodeTypeCategory()
    {
	return getInstance().getString(PROPERTIES_FILE, THREAD_NODE_TYPE_CATEGORY);
    }

    public static String getSdoProviderTypeTweet()
    {
	return getInstance().getString(PROPERTIES_FILE, SDO_PROVIDER_TYPE_TWEET);
    }

    public static String getSdoProviderTypeFacebookPost()
    {
	return getInstance().getString(PROPERTIES_FILE, SDO_PROVIDER_TYPE_FACEBOOK_POST);
    }

    public static String getSdoProviderTypeWebContent()
    {
	return getInstance().getString(PROPERTIES_FILE, SDO_PROVIDER_TYPE_WEB_CONTENT);
    }

    public static String getSdoSaverAdaptorHost()
    {
	return getInstance().getString(PROPERTIES_FILE, SDO_SAVER_ADAPTOR_HOST);
    }

    public static int getSdoSaverAdaptorPort()
    {
	return getInstance().getInt(PROPERTIES_FILE, SDO_SAVER_ADAPTOR_PORT);
    }

    public static String getSdoSaverAdaptorName()
    {
	return getInstance().getString(PROPERTIES_FILE, SDO_SAVER_ADAPTOR_NAME);
    }

    public static CALL_TYPE getSdoSaverAdaptorCallType()
    {
	String callTypeStr = getInstance().getString(PROPERTIES_FILE, SDO_SAVER_ADAPTOR_CALL_TYPE);

	if (callTypeStr.equalsIgnoreCase(CALL_TYPE.RMI.name()))
	{
	    return CALL_TYPE.RMI;
	} else if (callTypeStr.equalsIgnoreCase(CALL_TYPE.LOCAL.name()))
	{
	    return CALL_TYPE.LOCAL;
	}
	return null;
    }

    public static String getSdoSaverAdaptorBindingName()
    {
	return getInstance().getString(PROPERTIES_FILE, SDO_SAVER_ADAPTOR_BINDING_NAME);
    }

    public static String getNlpSaverAdaptorBindingName()
    {
	return getInstance().getString(PROPERTIES_FILE, NLP_SAVER_ADAPTOR_BINDING_NAME);
    }

    public static int getSdoSaverAdaptorMaxWorkersCount()
    {
	return getInstance().getInt(PROPERTIES_FILE, SDO_SAVER_ADAPTOR_MAX_WORKERS_COUNT);
    }

    public static String getNlpSaverAdaptorHost()
    {
	return getInstance().getString(PROPERTIES_FILE, NLP_SAVER_ADAPTOR_HOST);
    }

    public static int getNlpSaverAdaptorPort()
    {
	return getInstance().getInt(PROPERTIES_FILE, NLP_SAVER_ADAPTOR_PORT);
    }

    public static String getNlpSaverAdaptorName()
    {
	return getInstance().getString(PROPERTIES_FILE, NLP_SAVER_ADAPTOR_NAME);
    }

    public static CALL_TYPE getNlpSaverAdaptorCallType()
    {
	String callTypeStr = getInstance().getString(PROPERTIES_FILE, NLP_SAVER_ADAPTOR_CALL_TYPE);

	if (callTypeStr.equalsIgnoreCase(CALL_TYPE.RMI.name()))
	{
	    return CALL_TYPE.RMI;
	} else if (callTypeStr.equalsIgnoreCase(CALL_TYPE.LOCAL.name()))
	{
	    return CALL_TYPE.LOCAL;
	}
	return null;
    }

    public static int getNlpSaverAdaptorMaxWorkersCount()
    {
	return getInstance().getInt(PROPERTIES_FILE, NLP_SAVER_ADAPTOR_MAX_WORKERS_COUNT);
    }

    public static String getQueuerNlpUuidsQueueName()
    {
	return getInstance().getString(PROPERTIES_FILE, QUEUER_NLP_UUIDS_QUEUE_NAME);
    }

    public static boolean getIsStatsQueuerEnabled()
    {
	return getInstance().getBoolean(PROPERTIES_FILE, IS_STATS_QUEUER_ENABLED);
    }

    public static String getElasticSmartystatsIndexName()
    {
	return getInstance().getString(PROPERTIES_FILE, ELASTIC_SMARTYJOBS_INDEX_NAME);
    }

    public static Set<AdaptorSettingsVO> getSdoSaverAdaptorsSettings()
    {
	Set<AdaptorSettingsVO> adaptorSettingsVOs = new HashSet<AdaptorSettingsVO>();

	AdaptorSettingsVO adaptorSettingsVO = new AdaptorSettingsVO();
	adaptorSettingsVO.setHost(getSdoSaverAdaptorHost());
	adaptorSettingsVO.setPort(getSdoSaverAdaptorPort());
	adaptorSettingsVO.setBindingName(getSdoSaverAdaptorBindingName());
	adaptorSettingsVO.setName(getSdoSaverAdaptorName());
	adaptorSettingsVO.setMaximumWorkersNumber(getSdoSaverAdaptorMaxWorkersCount());

	adaptorSettingsVOs.add(adaptorSettingsVO);

	return adaptorSettingsVOs;
    }

    public static Set<AdaptorSettingsVO> getNlpSaverAdaptorsSettings()
    {
	Set<AdaptorSettingsVO> adaptorSettingsVOs = new HashSet<AdaptorSettingsVO>();

	AdaptorSettingsVO adaptorSettingsVO = new AdaptorSettingsVO();
	adaptorSettingsVO.setMaximumWorkersNumber(getNlpSaverAdaptorMaxWorkersCount());
	adaptorSettingsVO.setBindingName(getNlpSaverAdaptorBindingName());
	adaptorSettingsVO.setHost(getNlpSaverAdaptorHost());
	adaptorSettingsVO.setPort(getNlpSaverAdaptorPort());
	adaptorSettingsVO.setName(getNlpSaverAdaptorName());

	adaptorSettingsVOs.add(adaptorSettingsVO);

	return adaptorSettingsVOs;
    }
}
