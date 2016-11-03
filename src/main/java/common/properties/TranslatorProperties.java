package common.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jsmarty.core.common.balance.BalanceLimitsProperties;
import jsmarty.core.common.balance.CALL_LIMIT_RESTRICTION_TYPE;
import jsmarty.core.common.balance.KeyNamePattern;
import jsmarty.core.common.balance.model.ApiKeyVO;
import jsmarty.core.common.balance.model.ApiSettings;
import jsmarty.core.common.logging.TranslatorLogger;
import jsmarty.core.common.logging.core.Logger;
import jsmarty.core.common.properties.core.DefaultProperties;
import jsmarty.core.common.template.manageradaptor.vo.AdaptorSettingsVO;
import jsmarty.core.common.utils.Constants.CALL_TYPE;
import jsmarty.core.common.utils.StringsUtils;
import jsmarty.core.dataParser.translator.model.BalanceCountMode;

/**
 * 
 * @author vit
 *
 */
public class TranslatorProperties extends DefaultProperties  implements BalanceLimitsProperties{

	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static final TranslatorProperties instance = new TranslatorProperties();

	private final static Logger LOGGER = TranslatorLogger.getInstance();

	public TranslatorProperties() {
		super(PROPERTIES_FILE);
	}

	public static TranslatorProperties getInstance() {
		return instance;
	}

	public static boolean loadProperties(boolean refresh) {
		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	/*
	 * apis settings
	 */
	private Set<ApiSettings> apiSettingsSet;
	private final String KEYNAME_APINAME_IP_CALLTYPE = "sm:tr:$apiName$:$ip$.$callType$";
	private final String KEYNAME_APINAME_APIKEY_CALLTYPE = "sm:tr:$apiName$:$apiKey$.$callType$";
	private final String KEYNAME_APINAME_IP_APIKEY_CALLTYPE = "sm:tr:$apiName$:$ip$.$apiKey$.$callType$";

	private final String PROPERTY_KEY_apisNamesList = "sm.translator.apis.list";

	private final String PROPERTY_KEY_apiName = "sm.translator.$apiName$.apiname";
	private final String PROPERTY_KEY_supportedJobType =	"sm.translator.$apiName$.supported.job.type";

	private final String PROPERTY_KEY_maxCallsNumber = "sm.translator.$apiName$.maximum.calls.number";
	private final String PROPERTY_KEY_hostsList = "sm.translator.$apiName$.ips.list";

	private final String PROPERTY_KEY_apiKeysList = "sm.translator.$apiName$.keys.names.list";
	private final String KEYNAME_APINAME_KEYNAME_key = "sm.translator.$apiName$.$keyname$.key";
	private final String KEYNAME_APINAME_KEYNAME_IP = "sm.translator.$apiName$.$keyname$.ip.list";
	private final String KEYNAME_APINAME_KEYNAME_IS_COMMON = "sm.translator.$apiName$.$keyname$.is.common.for.all.ips";

	private final String PROPERTY_KEY_fetcherHandlerClass = "sm.translator.$apiName$.fetcher.handler.class";

	private final String PROPERTY_KEY_isConcurrentRestrictionEnabled = "sm.translator.$apiName$.is.concurrent.restriction.enabled";
	private final String PROPERTY_KEY_maxConcurrentConnectionsNumber = "sm.translator.$apiName$.max.concurrent.connections.number";
	private final String PROPERTY_KEY_isConcurrentPerIp = "sm.translator.$apiName$.is.concurrent.per.ip";
	private final String PROPERTY_KEY_isConcurrentPerKey = "sm.translator.$apiName$.is.concurrent.per.key";
	private final String PROPERTY_KEY_isConcurrentRandomized = "sm.translator.$apiName$.is.concurrent.randomized";
	private final String PROPERTY_KEY_minConcurrentRandom = "sm.translator.$apiName$.min.concurrent.random";
	private final String PROPERTY_KEY_maxConcurrentRandom = "sm.translator.$apiName$.max.concurrent.random";

	private final String PROPERTY_KEY_isIntervalRestrictionEnabled = "sm.translator.$apiName$.is.interval.restriction.enabled";
	private final String PROPERTY_KEY_maxIntervalCallsNumber = "sm.translator.$apiName$.max.interval.calls.number";
	private final String PROPERTY_KEY_isIntervalPerIp = "sm.translator.$apiName$.is.interval.per.ip";
	private final String PROPERTY_KEY_isIntervalPerKey = "sm.translator.$apiName$.is.interval.per.key";
	private final String PROPERTY_KEY_intervalTimeInSeconds = "sm.translator.$apiName$.interval.time.in.seconds";
	private final String PROPERTY_KEY_isIntervalPartitioned = "sm.translator.$apiName$.is.interval.partitioned";
	private final String PROPERTY_KEY_partitionTimeRangeInSeconds = "sm.translator.$apiName$.partition.time.range.in.seconds";
	private final String PROPERTY_KEY_isIntervalRandomized = "sm.translator.$apiName$.is.interval.randomized";
	private final String PROPERTY_KEY_minIntervalRandom = "sm.translator.$apiName$.min.interval.random";
	private final String PROPERTY_KEY_maxIntervalRandom = "sm.translator.$apiName$.max.interval.random";

	private final String PROPERTY_KEY_maxDailyCallsNumber = "sm.translator.$apiName$.max.daily.calls.number";
	private final String PROPERTY_KEY_isDailyRestrictionEnabled = "sm.translator.$apiName$.is.daily.restriction.enabled";
	private final String PROPERTY_KEY_isDailyPerIp = "sm.translator.$apiName$.is.daily.per.ip";
	private final String PROPERTY_KEY_isDailyPerKey = "sm.translator.$apiName$.is.daily.per.key";
	private final String PROPERTY_KEY_isDailyRandomized = "sm.translator.$apiName$.is.daily.randomized";
	private final String PROPERTY_KEY_minDailyRandom = "sm.translator.$apiName$.min.daily.random";
	private final String PROPERTY_KEY_maxDailyRandom = "sm.translator.$apiName$.max.daily.random";

	private final String PROPERTY_KEY_maxMonthlyCallsNumber = "sm.translator.$apiName$.max.monthly.calls.number";
	private final String PROPERTY_KEY_isMonthlyRestrictionEnabled = "sm.translator.$apiName$.is.monthly.restriction.enabled";
	private final String PROPERTY_KEY_isMonthlyPerIp = "sm.translator.$apiName$.is.monthly.per.ip";
	private final String PROPERTY_KEY_isMonthlyPerKey = "sm.translator.$apiName$.is.monthly.per.key";
	private final String PROPERTY_KEY_isMonthlyRandomized = "sm.translator.$apiName$.is.monthly.randomized";
	private final String PROPERTY_KEY_minMonthlyRandom = "sm.translator.$apiName$.min.monthly.random";
	private final String PROPERTY_KEY_maxMonthlyRandom = "sm.translator.$apiName$.max.monthly.random";

	private static Map<String, String> handlersClasses;
	///

	private static final String queueHost = "sm.tr.redis.host";
	private static final String queuePort = "sm.tr.redis.port";
	private static final String inputQueueName = "sm.tr.input.queue.name";
	private static final String outputQueueName = "sm.tr.output.queue.name";

	private static final String managerHost = "sm.tr.manager.host";
	private static final String managerPort = "sm.tr.manager.port";
	private static final String managerBindingName = "sm.tr.manager.binding.name";

	
	/* adaptors list settings */
	private static final String ADAPTORS_NAMES_LIST = "sm.tr.remote.adaptors.names.list";
	private static final String ADAPTOR_HOST = "sm.tr.$adaptor$.host";
	private static final String ADAPTOR_PORT = "sm.tr.$adaptor$.port";
	private static final String ADAPTOR_BINDING_NAME = "sm.tr.$adaptor$.binding.name";
	private static final String ADAPTOR_NAME = "sm.tr.$adaptor$.name";
	private static final String ADAPTOR_MAX_WORKERS_COUNT = "sm.tr.$adaptor$.maximumWorkersCount";
	
	private static final String callType = "sm.tr.call.type";

	/*
	 * SPECIFIC
	 */
	private static final String TRANSLATOR_BASIC_LANGUAGE = "sm.tr.basic.language";
	private static final String TRANSLATOR_BALANCE_COUNT_MODE = "sm.tr.balanceCountMode";
	private static final String CACHE_PREFIX = "sm.tr.cacheprefix";
	private static final String CACHE_JOBS_QUEUE_NAME = "sm.tr.cachejobsqueuename";

	private static final String NLP_TYPES = "sm.tr.nlptypes";
	private static final String nlp_type_api = "sm.tr.$keyname$.apiname";
	private static HashMap<String, String> nlpTypesAPIs;

	/*
	 * momentary
	 */
	private static final String EXCLUDED_APIS ="sm.tr.excludedapis";
	private static final String EXCLUDING_LANGUAGES ="sm.tr.excludedlanguages";
	private static final String ALTERNATIVE_API ="sm.tr.alternativeapi";

	private static List<String> excludedApis;
	private static List<String> excludedLanguages;

	public static String getAlternativeApi() {
		return getInstance().getString(PROPERTIES_FILE, ALTERNATIVE_API);
	}

	public static List<String> getExcludedapis() {
		if(excludedApis == null){
			excludedApis = getInstance().getListString(PROPERTIES_FILE, EXCLUDED_APIS);
		}
		return excludedApis;
	}

	public static List<String> getExcludedlanguages() {
		if(excludedLanguages == null){
			excludedLanguages = getInstance().getListString(PROPERTIES_FILE, EXCLUDING_LANGUAGES);
		}
		return excludedLanguages;
	}

	public static String getCachePrefix() {
		return getInstance().getString(PROPERTIES_FILE, CACHE_PREFIX);
	}

	public static String getTranslatorBasicLanguage() {
		return getInstance().getString(PROPERTIES_FILE, TRANSLATOR_BASIC_LANGUAGE);
	}

	public static BalanceCountMode getTranslatorbalancecountmode() {
		String mode = getInstance().getString(PROPERTIES_FILE, TRANSLATOR_BALANCE_COUNT_MODE);
		if(mode.equals(BalanceCountMode.allInOneCall.name())){
			return BalanceCountMode.allInOneCall;
		}else if(mode.equals(BalanceCountMode.groupedByTypeCall.name())){
			return BalanceCountMode.groupedByTypeCall;
		}else if(mode.equals(BalanceCountMode.oneWordOneCall.name())){
			return BalanceCountMode.oneWordOneCall;
		}
		return null;
	}

	public static Set<AdaptorSettingsVO>  getMOMENTARY_remoteAdaptorsSettings() {
		Set<AdaptorSettingsVO> adaptorSettingsVOs = new HashSet<AdaptorSettingsVO>();
		
		List<String> adaptorsNames = getAdaptorsNamesList();
		
		for(String adaptor : adaptorsNames){
		   
		    AdaptorSettingsVO adaptorSettings = getAdaptorSettings(adaptor);
		    
		    adaptorSettingsVOs.add(adaptorSettings);
		}

		return adaptorSettingsVOs;
	}
	
	private static AdaptorSettingsVO getAdaptorSettings(String adaptorName){
	    AdaptorSettingsVO adaptorSettings = new AdaptorSettingsVO();
	    
	    String name = getInstance().getString(StringsUtils.replace(ADAPTOR_NAME, new KeyNamePattern().setAdaptorName(adaptorName).getMap()));
	    adaptorSettings.setName(name);

	    String host = getInstance().getString(StringsUtils.replace(ADAPTOR_HOST, new KeyNamePattern().setAdaptorName(adaptorName).getMap()));
	    adaptorSettings.setHost(host);

	    int port = getInstance().getInt(StringsUtils.replace(ADAPTOR_PORT, new KeyNamePattern().setAdaptorName(adaptorName).getMap()));
	    adaptorSettings.setPort(port);

	    String bindingName = getInstance().getString(StringsUtils.replace(ADAPTOR_BINDING_NAME, new KeyNamePattern().setAdaptorName(adaptorName).getMap()));
	    adaptorSettings.setBindingName(bindingName);

	    int maxThreads = getInstance().getInt(StringsUtils.replace(ADAPTOR_MAX_WORKERS_COUNT, new KeyNamePattern().setAdaptorName(adaptorName).getMap()));
	    adaptorSettings.setMaximumWorkersNumber(maxThreads);
	    
	    return adaptorSettings;
	}
		
	private static List<String> getAdaptorsNamesList(){
	    return getInstance().getStringArrayList(ADAPTORS_NAMES_LIST);
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

	public static String getQueuehost() {
		return getInstance().getString(PROPERTIES_FILE, queueHost);
	}

	public static int getQueueport() {
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
	@Override
	public Set<ApiSettings> getApisSettingsSet(boolean reload) {
		if(reload || this.apiSettingsSet == null){
			loadApisSettings();
		}
		return this.apiSettingsSet;
	}

	public List<String> getApisNamesList(){
		return getInstance().getStringArrayList(PROPERTY_KEY_apisNamesList);
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

		this.handlersClasses = new HashMap(TEMP_fetchersHandlersClasses);
		this.apiSettingsSet = new HashSet<>(TEMP_apiSettingsSet);
	}

	public static Map<String, String> getHandlersClasses() {
		return handlersClasses;
	}

	public static List<String> getNlpTypes() {
		return getInstance().getListString(PROPERTIES_FILE, NLP_TYPES);
	}

	public static HashMap<String, String> getNlpTypesAPIs(boolean reload) {
		if(reload || nlpTypesAPIs == null){
			loadNlpTypesAPIs();
		}		
		return nlpTypesAPIs;
	}

	private static void loadNlpTypesAPIs() {
		HashMap<String, String> TEMP_nlpTypesAPIs = new HashMap<>();

		for (String type : getNlpTypes()){

			TEMP_nlpTypesAPIs.put(type, getInstance().getString(PROPERTIES_FILE, getPropertyName(type)));
		}
		nlpTypesAPIs = new HashMap<>(TEMP_nlpTypesAPIs);
	}

	private static String getPropertyName(String typename){
		return StringsUtils.replace(nlp_type_api, new KeyNamePattern().setKeyName(typename).getMap());

	}

	public Map<String, ApiSettings> getApisSettingsMap(boolean reload) {
		Map<String, ApiSettings> apisSettingsMap = new HashMap<>();
		Set<ApiSettings> apisSettings = new HashSet<>(getApisSettingsSet(reload));

		for(ApiSettings apiSettings : apisSettings){
			apisSettingsMap.put(apiSettings.getApiName(), apiSettings);
		}
		return apisSettingsMap;
	}

	public static String getCacheJobsQueueName() {
		return getInstance().getString(PROPERTIES_FILE, CACHE_JOBS_QUEUE_NAME);
	}

}