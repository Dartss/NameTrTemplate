package common.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import jsmarty.core.common.balance.BalanceLimitsProperties;
import jsmarty.core.common.balance.CALL_LIMIT_RESTRICTION_TYPE;
import jsmarty.core.common.balance.KeyNamePattern;
import jsmarty.core.common.balance.model.ApiKeyVO;
import jsmarty.core.common.balance.model.ApiSettings;
import jsmarty.core.common.properties.core.DefaultProperties;
import jsmarty.core.common.utils.StringsUtils;
import jsmarty.core.socialstats.model.StatsAdaptorSettings;

public class StatsParserProperties extends DefaultProperties implements BalanceLimitsProperties{

	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final ServerProperties instance = new ServerProperties();

	public StatsParserProperties() {
		super(PROPERTIES_FILE);
	}

	public static ServerProperties getInstance() {
		return instance;
	}

	public static boolean loadProperties(boolean refresh) {
		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	/*
	 * manager
	 */
	private static String STATS_CALL_TYPE = "sm.statsparser.adaptor.call.type";
	private static String MANAGER_RMI_HOST = "sm.statsparser.manager.rmi.host";
	private static String MANAGER_RMI_PORT = "sm.statsparser.manager.rmi.port";
	private static String MANAGER_RMI_BINDING_NAME = "sm.statsparser.manager.rmi.bindingname";

	private static String TASK_QUEUR_FREQUENCY_MILLIS = "sm.statsparser.queuer.frequency.millis";
	private static String TASK_QUEUR_LAST_TIME_CHECK_KEY_NAME = "sm.statsparser.queuer.last.time.check.key.name";

	private static String TASK_FETCHER_FREQUENCY_MILLIS = "sm.statsparser.fetcher.frequency.millis";
	private static String TASK_QUEUER_ENABLED = "sm.statsparser.queuer.enable";

	private static Map<String, String> fetchersHandlersClasses;
	/*
	 * redis
	 */
	private static String REDIS_HOST = "sm.statsparser.manager.redis.host";
	private static String REDIS_PORT = "sm.statsparser.manager.redis.port";

	/*
	 * elastic
	 */
	private static String ELASTIC_HOST = "sm.statsparser.elastic.host";
	private static String ELASTIC_PORT = "sm.statsparser.elastic.port";

	private static String ELASTIC_INDEX_SMARTY = "sm.statsparser.elastic.index.smarty";
	private static String ELASTIC_INDEX_SMARTYSTATS = "sm.statsparser.elastic.index.smartystats";
	private static String ELASTIC_INDEX_SMARTYJOBS = "sm.statsparser.elastic.index.smartyjobs";

	private static String ELASTIC_TYPE_SDO = "sm.statsparser.elastic.type.sdo";
	private static String ELASTIC_TYPE_TWEET = "sm.statsparser.elastic.type.tweet";
	private static String ELASTIC_TYPE_FACEBOOK_POST = "sm.statsparser.elastic.type.facebook.post";
	private static String ELASTIC_TYPE_URL_FACEBOOK = "sm.statsparser.elastic.type.url_facebook";
	private static String ELASTIC_TYPE_URL_TWITTER = "sm.statsparser.elastic.type.url_twitter";
	private static String ELASTIC_TYPE_URL_REDDIT = "sm.statsparser.elastic.type.url_reddit";

	/*
	 * sdo provider types
	 */
	private static String SDO_PROVIDER_TYPE_FACEBOOK_POST = "sm.statsparser.sdo.provider.type.facebook_post";
	private static String SDO_PROVIDER_TYPE_TWEET = "sm.statsparser.sdo.provider.type.tweet";
	private static String SDO_PROVIDER_TYPE_WEB_CONTENT = "sm.statsparser.sdo.provider.type.web_content";

	/* SHARED COUNT API */
	private static String API_SHAREDCOUNT_URL = "sm.statsparser.sharedcountAPI.url";
	private static String API_SHAREDCOUNT_PARAM_URL_NAME = "sm.statsparser.sharedcountAPI.param.url.name";
	private static String API_SHAREDCOUNT_PARAM_APIKEY_NAME = "sm.statsparser.sharedcountAPI.param.apikey.name";

	/* FACEBOOK GRAPH URL API */
	private static String API_GRAPHURL_URL = "sm.statsparser.grapUrlAPI.url";
	private static String API_GRAPHURL_PARAM_ID_NAME = "sm.statsparser.grapUrlAPI.param.id.name";

	/* GNIP ENGAGEMENT API */
	private static String API_GNIP_ENGAGEMENT_URL = "sm.statsparser.gnipEngagementAPI.url";
	private static String API_GNIP_ENGAGEMENT_METHOD_TOTALS = "sm.statsparser.gnipEngagementAPI.method.totals";
	private static String API_GNIP_ENGAGEMENT_METHOD_HISTORICAL = "sm.statsparser.gnipEngagementAPI.method.historical";
	private static String API_GNIP_ENGAGEMENT_METHOD_28HR = "sm.statsparser.gnipEngagementAPI.method.28hr";
	private static String API_GNIP_ENGAGEMENT_TYPE_FAVOTIRES = "sm.statsparser.gnipEngagementAPI.type.favorites";
	private static String API_GNIP_ENGAGEMENT_TYPE_REPLIES = "sm.statsparser.gnipEngagementAPI.type.replies";
	private static String API_GNIP_ENGAGEMENT_TYPE_RETWEETS = "sm.statsparser.gnipEngagementAPI.type.retweets";
	private static String API_GNIP_ENGAGEMENT_GROUP_BY_TWEET_ID = "sm.statsparser.gnipEngagementAPI.group.by.tweet.id";
	private static String API_GNIP_ENGAGEMENT_GROUP_BY_ENGAGEMENT_TYPE = "sm.statsparser.gnipEngagementAPI.group.by.engagement.type";

	/*
	 * apis settings
	 */
	private Set<ApiSettings> apiSettingsSet;
	private final String KEYNAME_APINAME_IP_CALLTYPE = "sm:soc:stats:$apiName$:$ip$.$callType$";
	private final String KEYNAME_APINAME_APIKEY_CALLTYPE = "sm:soc:stats:$apiName$:$apiKey$.$callType$";
	private final String KEYNAME_APINAME_IP_APIKEY_CALLTYPE = "sm:soc:stats:$apiName$:$ip$.$apiKey$.$callType$";

	private final String PROPERTY_KEY_apisNamesList = "sm.statsparser.apis.list";

	private final String PROPERTY_KEY_apiName = "sm.statsparser.$apiName$.apiname";
	private final String PROPERTY_KEY_supportedJobType =	"sm.statsparser.$apiName$.supported.job.type";

	private final String PROPERTY_KEY_maxCallsNumber = "sm.statsparser.$apiName$.maximum.calls.number";
	private final String PROPERTY_KEY_hostsList = "sm.statsparser.$apiName$.ips.list";

	private final String PROPERTY_KEY_apiKeysList = "sm.statsparser.$apiName$.keys.names.list";
	private final String KEYNAME_APINAME_KEYNAME_key = "sm.statsparser.$apiName$.$keyname$.key";
	private final String KEYNAME_APINAME_KEYNAME_IP = "sm.statsparser.$apiName$.$keyname$.ip.list";
	private final String KEYNAME_APINAME_KEYNAME_IS_COMMON = "sm.statsparser.$apiName$.$keyname$.is.common.for.all.ips";

	private final String PROPERTY_KEY_apiResetSchedulerClass = "sm.statsparser.$apiName$.reset.scheduler.class";
	private final String PROPERTY_KEY_fetcherHandlerClass = "sm.statsparser.$apiName$.fetcher.handler.class";

	private final String PROPERTY_KEY_isConcurrentRestrictionEnabled = "sm.statsparser.$apiName$.is.concurrent.restriction.enabled";
	private final String PROPERTY_KEY_maxConcurrentConnectionsNumber = "sm.statsparser.$apiName$.max.concurrent.connections.number";
	private final String PROPERTY_KEY_isConcurrentPerIp = "sm.statsparser.$apiName$.is.concurrent.per.ip";
	private final String PROPERTY_KEY_isConcurrentPerKey = "sm.statsparser.$apiName$.is.concurrent.per.key";
	private final String PROPERTY_KEY_isConcurrentRandomized = "sm.statsparser.$apiName$.is.concurrent.randomized";
	private final String PROPERTY_KEY_minConcurrentRandom = "sm.statsparser.$apiName$.min.concurrent.random";
	private final String PROPERTY_KEY_maxConcurrentRandom = "sm.statsparser.$apiName$.max.concurrent.random";

	private final String PROPERTY_KEY_isIntervalRestrictionEnabled = "sm.statsparser.$apiName$.is.interval.restriction.enabled";
	private final String PROPERTY_KEY_maxIntervalCallsNumber = "sm.statsparser.$apiName$.max.interval.calls.number";
	private final String PROPERTY_KEY_isIntervalPerIp = "sm.statsparser.$apiName$.is.interval.per.ip";
	private final String PROPERTY_KEY_isIntervalPerKey = "sm.statsparser.$apiName$.is.interval.per.key";
	private final String PROPERTY_KEY_intervalTimeInSeconds = "sm.statsparser.$apiName$.interval.time.in.seconds";
	private final String PROPERTY_KEY_isIntervalPartitioned = "sm.statsparser.$apiName$.is.interval.partitioned";
	private final String PROPERTY_KEY_partitionTimeRangeInSeconds = "sm.statsparser.$apiName$.partition.time.range.in.seconds";
	private final String PROPERTY_KEY_isIntervalRandomized = "sm.statsparser.$apiName$.is.interval.randomized";
	private final String PROPERTY_KEY_minIntervalRandom = "sm.statsparser.$apiName$.min.interval.random";
	private final String PROPERTY_KEY_maxIntervalRandom = "sm.statsparser.$apiName$.max.interval.random";

	private final String PROPERTY_KEY_maxDailyCallsNumber = "sm.statsparser.$apiName$.max.daily.calls.number";
	private final String PROPERTY_KEY_isDailyRestrictionEnabled = "sm.statsparser.$apiName$.is.daily.restriction.enabled";
	private final String PROPERTY_KEY_isDailyPerIp = "sm.statsparser.$apiName$.is.daily.per.ip";
	private final String PROPERTY_KEY_isDailyPerKey = "sm.statsparser.$apiName$.is.daily.per.key";
	private final String PROPERTY_KEY_isDailyRandomized = "sm.statsparser.$apiName$.is.daily.randomized";
	private final String PROPERTY_KEY_minDailyRandom = "sm.statsparser.$apiName$.min.daily.random";
	private final String PROPERTY_KEY_maxDailyRandom = "sm.statsparser.$apiName$.max.daily.random";

	private final String PROPERTY_KEY_maxMonthlyCallsNumber = "sm.statsparser.$apiName$.max.monthly.calls.number";
	private final String PROPERTY_KEY_isMonthlyRestrictionEnabled = "sm.statsparser.$apiName$.is.monthly.restriction.enabled";
	private final String PROPERTY_KEY_isMonthlyPerIp = "sm.statsparser.$apiName$.is.monthly.per.ip";
	private final String PROPERTY_KEY_isMonthlyPerKey = "sm.statsparser.$apiName$.is.monthly.per.key";
	private final String PROPERTY_KEY_isMonthlyRandomized = "sm.statsparser.$apiName$.is.monthly.randomized";
	private final String PROPERTY_KEY_minMonthlyRandom = "sm.statsparser.$apiName$.min.monthly.random";
	private final String PROPERTY_KEY_maxMonthlyRandom = "sm.statsparser.$apiName$.max.monthly.random";

	/*
	 * adaptors settings
	 */
	private Set<StatsAdaptorSettings> adaptorsSettingsSet;	
	private final String PROPERTY_KEY_adaptorsNamesList = "sm.statsparser.adaptor.names.list";

	private final String PROPERTY_KEY_adaptorName = "sm.statsparser.$adaptor$.name";
	private final String PROPERTY_KEY_adaptorHost = "sm.statsparser.$adaptor$.host";
	private final String PROPERTY_KEY_adaptorPort = "sm.statsparser.$adaptor$.port";
	private final String PROPERTY_KEY_adaptorBindingName = "sm.statsparser.$adaptor$.binding.name";
	private final String PROPERTY_KEY_adaptorMaxThreadNumber = "sm.statsparser.$adaptor$.max.workers.threads.number";

	/*
	 * job reset settings
	 */
	private final String PROPERTY_KEY_jobsTypesNamesList = "sm.statsparser.jobs.types.names.list";
	private final String PROPERTY_KEY_jobResetFrequencies = "sm.statsparser.job.$jobType$.update.frequencies.minutes";
	private Map<String, int[]> frequenciesMap;

	public List<String> getApisNamesList(){
		return getInstance().getStringArrayList(PROPERTY_KEY_apisNamesList);
	}

	@Override
	public Set<ApiSettings> getApisSettingsSet(boolean reload){
		if(reload || this.apiSettingsSet == null){
			loadApisSettings();
		}
		return this.apiSettingsSet;
	}

	public Set<StatsAdaptorSettings> getAdaptorsSettingsSet(boolean reload){
		if(reload || this.adaptorsSettingsSet == null){
			loadAdaptorsSettings();
		}
		return adaptorsSettingsSet;
	}

	public void loadJobsResetFrequencies(){
		Map<String, int[]> TEMP_frequenciesMap = new HashMap<>();
		List<String> jobsTypesNames = getJobsTypesNamesList();
		for(String job : jobsTypesNames){

			int[] frequencies = getInstance().getIntegerArray(StringsUtils.replace(PROPERTY_KEY_jobResetFrequencies, new KeyNamePattern().setJobType(job).getMap()));
			TEMP_frequenciesMap.put(job, frequencies);
		}
		this.frequenciesMap = TEMP_frequenciesMap;
	}

	private List<String> getJobsTypesNamesList() {
		return getInstance().getStringArrayList(PROPERTY_KEY_jobsTypesNamesList);
	}

	public void loadAdaptorsSettings(){
		Set<StatsAdaptorSettings> TEMP_adaptorsSettingsSet = new HashSet<>();
		List<String> adaptorsNamesList = getAdaptorsNamesList();

		StatsAdaptorSettings adaptorSettings;
		for (String adaptor : adaptorsNamesList){
			adaptorSettings = new StatsAdaptorSettings();

			String name = getInstance().getString(StringsUtils.replace(PROPERTY_KEY_adaptorName, new KeyNamePattern().setAdaptorName(adaptor).getMap()));
			adaptorSettings.setName(name);

			String host = getInstance().getString(StringsUtils.replace(PROPERTY_KEY_adaptorHost, new KeyNamePattern().setAdaptorName(adaptor).getMap()));
			adaptorSettings.setHost(host);

			int port = getInstance().getInt(StringsUtils.replace(PROPERTY_KEY_adaptorPort, new KeyNamePattern().setAdaptorName(adaptor).getMap()));
			adaptorSettings.setPort(port);

			String bindingName = getInstance().getString(StringsUtils.replace(PROPERTY_KEY_adaptorBindingName, new KeyNamePattern().setAdaptorName(adaptor).getMap()));
			adaptorSettings.setBindingName(bindingName);

			int maxThreads = getInstance().getInt(StringsUtils.replace(PROPERTY_KEY_adaptorMaxThreadNumber, new KeyNamePattern().setAdaptorName(adaptor).getMap()));
			adaptorSettings.setMaximumWorkersNumber(maxThreads);

			TEMP_adaptorsSettingsSet.add(adaptorSettings);
		}

		this.adaptorsSettingsSet = new HashSet<>(TEMP_adaptorsSettingsSet);
	}

	private List<String> getAdaptorsNamesList() {
		return getInstance().getStringArrayList(PROPERTY_KEY_adaptorsNamesList);
	}

	public void loadApisSettings(){
		Set<ApiSettings> TEMP_apiSettingsSet = new HashSet<>();
		Map<String, String> TEMP_fetchersHandlersClasses = new HashMap<>();

		List<String> apisNamesList = getApisNamesList();

		ApiSettings apiSettings;
		for(String api : apisNamesList){
			apiSettings = new ApiSettings();

			String apiName = getInstance().getString(StringsUtils.replace(PROPERTY_KEY_apiName, new KeyNamePattern().setApiName(api).getMap()));
			apiSettings.setApiName(apiName);

			String supportedJobType = getInstance().getString(StringsUtils.replace(PROPERTY_KEY_supportedJobType, new KeyNamePattern().setApiName(api).getMap()));
			apiSettings.setSupportedJobType(supportedJobType);

			int maxCallsNumber = getInstance().getInt(StringsUtils.replace(PROPERTY_KEY_maxCallsNumber, new KeyNamePattern().setApiName(api).getMap()));
			apiSettings.setMaxCallsNumber(maxCallsNumber);

			List<String> hostsList = getInstance().getStringArrayList(StringsUtils.replace(PROPERTY_KEY_hostsList, new KeyNamePattern().setApiName(api).getMap()));
			apiSettings.setHostsList(hostsList);

			List<String> apiKeysList = getInstance().getStringArrayList(StringsUtils.replace(PROPERTY_KEY_apiKeysList, new KeyNamePattern().setApiName(api).getMap()));
			List<ApiKeyVO> apiKeys = new ArrayList<>();
			ApiKeyVO apiKeyVo;
			for (String keyname : apiKeysList){
				String apiKey = getInstance().getString(StringsUtils.replace(KEYNAME_APINAME_KEYNAME_key, new KeyNamePattern().setApiName(api).setKeyName(keyname).getMap()));
				List<String> ips = getInstance().getStringArrayList(StringsUtils.replace(KEYNAME_APINAME_KEYNAME_IP, new KeyNamePattern().setApiName(api).setKeyName(keyname).getMap()));
				boolean isCommon = getInstance().getBoolean(StringsUtils.replace(KEYNAME_APINAME_KEYNAME_IS_COMMON, new KeyNamePattern().setApiName(api).setKeyName(keyname).getMap()));

				apiKeyVo = new ApiKeyVO(apiName, apiKey, keyname, ips, isCommon);
				apiKeys.add(apiKeyVo);
			}
			apiSettings.setApiKeysList(apiKeys);

			String apiResetSchedulerClass = getInstance().getString(StringsUtils.replace(PROPERTY_KEY_apiResetSchedulerClass, new KeyNamePattern().setApiName(api).getMap()));
			apiSettings.setApiResetSchedulerClass(apiResetSchedulerClass);

			String fetcherHandlerClass = getInstance().getString(StringsUtils.replace(PROPERTY_KEY_fetcherHandlerClass, new KeyNamePattern().setApiName(api).getMap()));
			apiSettings.setFetcherHandlerClass(fetcherHandlerClass);
			TEMP_fetchersHandlersClasses.put(api, fetcherHandlerClass);

			boolean isConcurrentRestrictionEnabled = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isConcurrentRestrictionEnabled, new KeyNamePattern().setApiName(api).getMap()));
			apiSettings.setConcurrentRestrictionEnabled(isConcurrentRestrictionEnabled);
			if(isConcurrentRestrictionEnabled){
				int maxConcurrentConnectionsNumber = getInstance().getInt(StringsUtils.replace(PROPERTY_KEY_maxConcurrentConnectionsNumber, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setMaxConcurrentConnectionsNumber(maxConcurrentConnectionsNumber);

				boolean isConcurrentPerIp = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isConcurrentPerIp, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setConcurrentPerIp(isConcurrentPerIp);

				boolean isConcurrentPerKey = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isConcurrentPerKey, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setConcurrentPerKey(isConcurrentPerKey);

				boolean isConcurrentRandomized = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isConcurrentRandomized, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setConcurrentRandomized(isConcurrentRandomized);

				int minConcurrentRandom = getInstance().getInt(StringsUtils.replace(PROPERTY_KEY_minConcurrentRandom, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setMinConcurrentRandom(minConcurrentRandom);

				int maxConcurrentRandom = getInstance().getInt(StringsUtils.replace(PROPERTY_KEY_maxConcurrentRandom, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setMaxConcurrentRandom(maxConcurrentRandom);
			}

			boolean isIntervalRestrictionEnabled = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isIntervalRestrictionEnabled, new KeyNamePattern().setApiName(api).getMap()));
			apiSettings.setIntervalRestrictionEnabled(isIntervalRestrictionEnabled);
			if(isIntervalRestrictionEnabled){
				int maxIntervalCallsNumber = getInstance().getInt(StringsUtils.replace(PROPERTY_KEY_maxIntervalCallsNumber, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setMaxIntervalCallsNumber(maxIntervalCallsNumber);

				boolean isIntervalPerIp = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isIntervalPerIp, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setIntervalPerIp(isIntervalPerIp);

				boolean isIntervalPerKey = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isIntervalPerKey, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setIntervalPerKey(isIntervalPerKey);

				long intervalTimeInSeconds = getInstance().getLong(StringsUtils.replace(PROPERTY_KEY_intervalTimeInSeconds, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setIntervalTimeInSeconds(intervalTimeInSeconds);

				boolean isIntervalPartitioned = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isIntervalPartitioned, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setIntervalPartitioned(isIntervalPartitioned);
				if(isIntervalPartitioned){
					long partitionTimeRangeInSeconds = getInstance().getLong(StringsUtils.replace(PROPERTY_KEY_partitionTimeRangeInSeconds, new KeyNamePattern().setApiName(api).getMap()));
					apiSettings.setPartitionTimeRangeInSeconds(partitionTimeRangeInSeconds);
				}
				boolean isIntervalRandomized = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isIntervalRandomized, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setIntervalRandomized(isIntervalRandomized);
				if(isIntervalRandomized){
					int minIntervalRandom = getInstance().getInt(StringsUtils.replace(PROPERTY_KEY_minIntervalRandom, new KeyNamePattern().setApiName(api).getMap()));
					apiSettings.setMinIntervalRandom(minIntervalRandom);

					int maxIntervalRandom = getInstance().getInt(StringsUtils.replace(PROPERTY_KEY_maxIntervalRandom, new KeyNamePattern().setApiName(api).getMap()));
					apiSettings.setMaxIntervalRandom(maxIntervalRandom);

				}}

			boolean isDailyRestrictionEnabled = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isDailyRestrictionEnabled, new KeyNamePattern().setApiName(api).getMap()));
			apiSettings.setDailyRestrictionEnabled(isDailyRestrictionEnabled);
			if(isDailyRestrictionEnabled){
				int maxDailyConnectionsNumber = getInstance().getInt(StringsUtils.replace(PROPERTY_KEY_maxDailyCallsNumber, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setMaxDailyCallsNumber(maxDailyConnectionsNumber);

				boolean isDailyPerIp = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isDailyPerIp, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setDailyPerIp(isDailyPerIp);

				boolean isDailyPerKey = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isDailyPerKey, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setDailyPerKey(isDailyPerKey);

				boolean isDailyRandomized = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isDailyRandomized, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setDailyRandomized(isDailyRandomized);
				if(isDailyRandomized){
					int minDailyRandom = getInstance().getInt(StringsUtils.replace(PROPERTY_KEY_minDailyRandom, new KeyNamePattern().setApiName(api).getMap()));
					apiSettings.setMinDailyRandom(minDailyRandom);

					int maxDailyRandom = getInstance().getInt(StringsUtils.replace(PROPERTY_KEY_maxDailyRandom, new KeyNamePattern().setApiName(api).getMap()));
					apiSettings.setMaxDailyRandom(maxDailyRandom);
				}
			}

			boolean isMonthlyRestrictionEnabled = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isMonthlyRestrictionEnabled, new KeyNamePattern().setApiName(api).getMap()));
			apiSettings.setMonthlyRestrictionEnabled(isMonthlyRestrictionEnabled);
			if(isMonthlyRestrictionEnabled){
				int maxMonthlyConnectionsNumber = getInstance().getInt(StringsUtils.replace(PROPERTY_KEY_maxMonthlyCallsNumber, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setMaxMonthlyCallsNumber(maxMonthlyConnectionsNumber);

				boolean isMonthlyPerIp = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isMonthlyPerIp, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setMonthlyPerIp(isMonthlyPerIp);

				boolean isMonthlyPerKey = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isMonthlyPerKey, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setMonthlyPerKey(isMonthlyPerKey);

				boolean isMonthlyRandomized = getInstance().getBoolean(StringsUtils.replace(PROPERTY_KEY_isMonthlyRandomized, new KeyNamePattern().setApiName(api).getMap()));
				apiSettings.setMonthlyRandomized(isMonthlyRandomized);
				if(isMonthlyRandomized){
					int minMonthlyRandom = getInstance().getInt(StringsUtils.replace(PROPERTY_KEY_minMonthlyRandom, new KeyNamePattern().setApiName(api).getMap()));
					apiSettings.setMinMonthlyRandom(minMonthlyRandom);

					int maxMonthlyRandom = getInstance().getInt(StringsUtils.replace(PROPERTY_KEY_maxMonthlyRandom, new KeyNamePattern().setApiName(api).getMap()));
					apiSettings.setMaxMonthlyRandom(maxMonthlyRandom);
				}
			}
			TEMP_apiSettingsSet.add(apiSettings);
		}

		this.fetchersHandlersClasses = new HashMap(TEMP_fetchersHandlersClasses);
		this.apiSettingsSet = new HashSet<>(TEMP_apiSettingsSet);
	}

	@Override
	public String getKeyNameByIp(String apiName, String ip, CALL_LIMIT_RESTRICTION_TYPE callLimitType) {
		return StringsUtils.replace(KEYNAME_APINAME_IP_CALLTYPE, new KeyNamePattern().setApiName(apiName).setIp(ip).setCallType(callLimitType.name()).getMap());
	}

	@Override
	public String getKeyNameByApiKey(String apiName, String apiKey, CALL_LIMIT_RESTRICTION_TYPE callLimitType) {
		return StringsUtils.replace(KEYNAME_APINAME_APIKEY_CALLTYPE, new KeyNamePattern().setApiName(apiName).setApiKey(apiKey).setCallType(callLimitType.name()).getMap());
	}

	@Override
	public String getKeyNameByApiKeyAndIp(String apiName, String apiKey, String ip, CALL_LIMIT_RESTRICTION_TYPE callLimitType) {
		return StringsUtils.replace(KEYNAME_APINAME_IP_APIKEY_CALLTYPE, new KeyNamePattern().setApiName(apiName).setIp(ip).setApiKey(apiKey).setCallType(callLimitType.name()).getMap());

	}

	public static String getELASTIC_HOST() {
		return getInstance().getString(PROPERTIES_FILE, ELASTIC_HOST);
	}

	public static int getELASTIC_PORT() {
		return getInstance().getInt(PROPERTIES_FILE, ELASTIC_PORT);
	}

	public static String getELASTIC_INDEX_SMARTY() {
		return getInstance().getString(PROPERTIES_FILE, ELASTIC_INDEX_SMARTY);
	}

	public static String getELASTIC_INDEX_SMARTYJOBS() {
		return getInstance().getString(PROPERTIES_FILE, ELASTIC_INDEX_SMARTYJOBS);
	}

	public static String getELASTIC_INDEX_SMARTYSTATS() {
		return getInstance().getString(PROPERTIES_FILE, ELASTIC_INDEX_SMARTYSTATS);
	}

	public static String getELASTIC_TYPE_SDO() {
		return getInstance().getString(PROPERTIES_FILE, ELASTIC_TYPE_SDO);
	}

	public static String getELASTIC_TYPE_FACEBOOK_POST() {
		return getInstance().getString(PROPERTIES_FILE, ELASTIC_TYPE_FACEBOOK_POST);
	}

	public static String getELASTIC_TYPE_TWEET() {
		return getInstance().getString(PROPERTIES_FILE, ELASTIC_TYPE_TWEET);
	}

	public static String getELASTIC_TYPE_URL_FACEBOOK() {
		return getInstance().getString(PROPERTIES_FILE, ELASTIC_TYPE_URL_FACEBOOK);
	}

	public static String getELASTIC_TYPE_URL_TWITTER() {
		return getInstance().getString(PROPERTIES_FILE, ELASTIC_TYPE_URL_TWITTER);
	}

	public static String getELASTIC_TYPE_URL_REDDIT() {
		return getInstance().getString(PROPERTIES_FILE, ELASTIC_TYPE_URL_REDDIT);
	}

	public static String getCALL_TYPE() {
		return getInstance().getString(PROPERTIES_FILE, STATS_CALL_TYPE);
	}

	public static String getMANAGER_RMI_HOST() {
		return getInstance().getString(PROPERTIES_FILE,MANAGER_RMI_HOST);	
	}

	public static int getMANAGER_RMI_PORT() {
		return getInstance().getInt(PROPERTIES_FILE,MANAGER_RMI_PORT);
	}

	public static String getMANAGER_RMI_BINDING_NAME() {
		return getInstance().getString(PROPERTIES_FILE,MANAGER_RMI_BINDING_NAME);
	}

	public static long getTASK_FETCHER_FREQUENCY_MILLIS() {
		return getInstance().getLong(PROPERTIES_FILE,TASK_FETCHER_FREQUENCY_MILLIS);	
	}

	public static long getTASK_QUEUR_FREQUENCY_MILLIS() {
		return getInstance().getLong(PROPERTIES_FILE,TASK_QUEUR_FREQUENCY_MILLIS);	
	}

	public static boolean isTASK_QUEUER_ENABLED() {
		return getInstance().getBoolean(PROPERTIES_FILE,TASK_QUEUER_ENABLED);	
	}

	public static String getREDIS_HOST() {
		return getInstance().getString(PROPERTIES_FILE,REDIS_HOST);	
	}

	public static int getREDIS_PORT() {
		return getInstance().getInt(PROPERTIES_FILE,REDIS_PORT);	
	}

	public static String getSDO_PROVIDER_TYPE_FACEBOOK_POST() {
		return getInstance().getString(PROPERTIES_FILE,SDO_PROVIDER_TYPE_FACEBOOK_POST);
	}

	public static String getSDO_PROVIDER_TYPE_TWEET() {
		return getInstance().getString(PROPERTIES_FILE,SDO_PROVIDER_TYPE_TWEET);
	}

	public static String getSDO_PROVIDER_TYPE_WEB_CONTENT() {
		return getInstance().getString(PROPERTIES_FILE,SDO_PROVIDER_TYPE_WEB_CONTENT);
	}

	public static String getAPI_SHAREDCOUNT_PARAM_APIKEY_NAME() {
		return getInstance().getString(PROPERTIES_FILE,API_SHAREDCOUNT_PARAM_APIKEY_NAME);
	}

	public static String getAPI_SHAREDCOUNT_PARAM_URL_NAME() {
		return getInstance().getString(PROPERTIES_FILE,API_SHAREDCOUNT_PARAM_URL_NAME);
	}

	public static String getAPI_SHAREDCOUNT_URL() {
		return getInstance().getString(PROPERTIES_FILE,API_SHAREDCOUNT_URL);
	}

	public static String getAPI_GRAPHURL_PARAM_ID_NAME() {
		return getInstance().getString(PROPERTIES_FILE,API_GRAPHURL_PARAM_ID_NAME);
	}

	public static String getAPI_GRAPHURL_URL() {
		return getInstance().getString(PROPERTIES_FILE,API_GRAPHURL_URL);
	}

	public static String getAPI_GNIP_ENGAGEMENT_GROUP_BY_ENGAGEMENT_TYPE() {
		return getInstance().getString(PROPERTIES_FILE,API_GNIP_ENGAGEMENT_GROUP_BY_ENGAGEMENT_TYPE);
	}

	public static String getAPI_GNIP_ENGAGEMENT_GROUP_BY_TWEET_ID() {
		return getInstance().getString(PROPERTIES_FILE,API_GNIP_ENGAGEMENT_GROUP_BY_TWEET_ID);
	}

	public static String getAPI_GNIP_ENGAGEMENT_METHOD_28HR() {
		return getInstance().getString(PROPERTIES_FILE,API_GNIP_ENGAGEMENT_METHOD_28HR);
	}

	public static String getAPI_GNIP_ENGAGEMENT_METHOD_HISTORICAL() {
		return getInstance().getString(PROPERTIES_FILE,API_GNIP_ENGAGEMENT_METHOD_HISTORICAL);
	}

	public static String getAPI_GNIP_ENGAGEMENT_METHOD_TOTALS() {
		return getInstance().getString(PROPERTIES_FILE,API_GNIP_ENGAGEMENT_METHOD_TOTALS);
	}

	public static String getAPI_GNIP_ENGAGEMENT_TYPE_FAVOTIRES() {
		return getInstance().getString(PROPERTIES_FILE,API_GNIP_ENGAGEMENT_TYPE_FAVOTIRES);
	}

	public static String getAPI_GNIP_ENGAGEMENT_TYPE_REPLIES() {
		return getInstance().getString(PROPERTIES_FILE,API_GNIP_ENGAGEMENT_TYPE_REPLIES);
	}

	public static String getAPI_GNIP_ENGAGEMENT_TYPE_RETWEETS() {
		return getInstance().getString(PROPERTIES_FILE,API_GNIP_ENGAGEMENT_TYPE_RETWEETS);
	}

	public static String getAPI_GNIP_ENGAGEMENT_URL() {
		return getInstance().getString(PROPERTIES_FILE,API_GNIP_ENGAGEMENT_URL);
	}

	public static String getTASK_QUEUR_LAST_TIME_CHECK_KEY_NAME() {
		return getInstance().getString(PROPERTIES_FILE,TASK_QUEUR_LAST_TIME_CHECK_KEY_NAME);
	}

	public Map<String, int[]> getFrequenciesMap(boolean reload) {
		if(reload || this.frequenciesMap == null){
			loadJobsResetFrequencies();
		}
		return frequenciesMap;
	}

	public static Map<String, String> getFetchersHandlersClasses() {
		return fetchersHandlersClasses;
	}

}