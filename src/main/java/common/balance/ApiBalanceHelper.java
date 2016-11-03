package common.balance;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import jsmarty.core.common.balance.model.ApiKeyVO;
import jsmarty.core.common.balance.model.ApiSettings;
import jsmarty.core.common.balance.tasks.BalanceTask;
import jsmarty.core.common.logging.StatsLogger;
import jsmarty.core.common.logging.core.Logger;
import jsmarty.core.common.queuer.QueuerPoolHandler;
import jsmarty.core.common.queuer.QueuerPoolHandlerImpl;
import jsmarty.core.common.scheduling.quartz.TaskScheduleHandler;
import jsmarty.core.common.utils.DateUtils;
import jsmarty.core.common.utils.NumberUtils;

/**
 * 
 * This helper is used to handle all the balance and limits
 * things -
 * initializing keys - constant resetting - incrementing - decrementing
 * 
 * 
 * @author rud
 *
 */
public class ApiBalanceHelper extends BalanceLimitsHelper implements Serializable
{
	private static Logger logger = StatsLogger.getInstance();

	private BalanceLimitsProperties properties;
	private Set<ApiSettings> apisSettingsSet;

	private String queueHost;
	private int queuePort;
	private QueuerPoolHandler queuerPoolHandler;

	private TaskScheduleHandler taskScheduleHandler;

	public ApiBalanceHelper(String queueHost, int queuePort, Set<ApiSettings> apisSettingsSet, BalanceLimitsProperties properties) {
		super(queueHost, queuePort);
		this.queueHost = queueHost;
		this.queuePort = queuePort;
		this.queuerPoolHandler = new QueuerPoolHandlerImpl(queueHost, queuePort);

		this.apisSettingsSet = apisSettingsSet;
		this.properties = properties;

		this.taskScheduleHandler = new TaskScheduleHandler();
	}

	public void initBalanceCounts()
	{
		logger.debug("initBalanceCounts: Launch Init Balance counts");
		// interval, daily, monthly
		scheduleBalanceTasks();
		// concurrent
		initializeAllApisConcurrentBalance();
	}

	private void initializeAllApisConcurrentBalance()
	{
		logger.debug("scheduleBalanceTasks: initializeAllApisConcurrentBalance apisSettingsSet=[" + apisSettingsSet + "]");
		//
		for (ApiSettings apiSettings : apisSettingsSet)
		{
			// case concurrent:
			if (apiSettings.isConcurrentRestrictionEnabled())
			{
				initializeConcurrentBalance(apiSettings);
			}
		}
	}

	private void initializeConcurrentBalance(ApiSettings apiSettings)
	{
		String apiName = apiSettings.getApiName();
		//
		logger.debug("initializeConcurrentBalance: apiName=[" + apiName + "]");
		//
		logger.debug("initializeConcurrentBalance: apiSettings.isConcurrentPerIp()=[" + apiSettings.isConcurrentPerIp() + "]");
		if (apiSettings.isConcurrentPerIp())
		{
			logger.debug("initializeConcurrentBalance: apiSettings.isConcurrentPerIp()=[" + apiSettings.isConcurrentPerIp() + "]");
			for (String ip : apiSettings.getHostsList())
			{
				String key = properties.getKeyNameByIp(apiName, ip, CALL_LIMIT_RESTRICTION_TYPE.concurrent);
				//
				logger.trace("initializeConcurrentBalance: Host ip=[" + ip + "]");
				//
				long value = apiSettings.getMaxConcurrentConnectionsNumber();
				if (apiSettings.isConcurrentRandomized())
				{
					logger.debug("initializeConcurrentBalance: apiSettings.isConcurrentRandomized()=[" + apiSettings.isConcurrentRandomized() + "]");
					//
					value = NumberUtils.getRandomInt(apiSettings.getMinConcurrentRandom(), apiSettings.getMaxConcurrentRandom());
					//
					logger.trace("initializeConcurrentBalance: Randomized Value=[" + value + "]");
				}
				logger.trace("initializeConcurrentBalance: queue=[" + key + "=" + value + "]");
				//
				queuerPoolHandler.set(key, value + "");
			}
		} else if (apiSettings.isConcurrentPerKey())
		{
			logger.debug("initializeConcurrentBalance: apiSettings.isConcurrentPerKey()=[" + apiSettings.isConcurrentPerKey() + "]");
			for (ApiKeyVO apiKey : apiSettings.getApiKeysList())
			{
				logger.debug("initializeConcurrentBalance: apiKey.isCommonForAllIps()=[" + apiKey.isCommonForAllIps() + "]");
				if (apiKey.isCommonForAllIps())
				{
					//
					// apiKeyIps_keysNames.put(BalanceConstants.API_KEY_COMMON_FOR_ALL_HOSTS,
					// properties.getConcurrentKeyNameByApiKey(apiName,
					// apiKey.getApiKey()));
					String key = properties.getKeyNameByApiKey(apiName, apiKey.getApiKey(), CALL_LIMIT_RESTRICTION_TYPE.concurrent);
					long value = apiSettings.getMaxConcurrentConnectionsNumber();
					//
					logger.trace("initializeConcurrentBalance: key=[" + key + "]...value=[" + value + "]");
					//
					logger.debug("initializeConcurrentBalance: apiSettings.isConcurrentRandomized()=[" + apiSettings.isConcurrentRandomized() + "]");
					//
					if (apiSettings.isConcurrentRandomized())
					{
						value = NumberUtils.getRandomInt(apiSettings.getMinConcurrentRandom(), apiSettings.getMaxConcurrentRandom());
					}
					//
					logger.trace("initializeConcurrentBalance: before queuing key=[" + key + "]...value=[" + value + "]");
					//
					queuerPoolHandler.set(key, value + "");
				} else
				{
					for (String ip : apiKey.getHostsList())
					{
						String key = properties.getKeyNameByApiKeyAndIp(apiName, apiKey.getApiKey(), ip,
								CALL_LIMIT_RESTRICTION_TYPE.concurrent);
						long value = apiSettings.getMaxConcurrentConnectionsNumber();
						//
						logger.trace("initializeConcurrentBalance: key=[" + key + "]...value=[" + value + "]");
						logger.debug(
								"initializeConcurrentBalance: apiSettings.isConcurrentRandomized()=[" + apiSettings.isConcurrentRandomized() + "]");
						//
						if (apiSettings.isConcurrentRandomized())
						{
							value = NumberUtils.getRandomInt(apiSettings.getMinConcurrentRandom(), apiSettings.getMaxConcurrentRandom());
						}
						//
						logger.trace("initializeConcurrentBalance: before queuing key=[" + key + "]...value=[" + value + "]");
						//
						queuerPoolHandler.set(key, value + "");
					}
				}
			}
		}
	}

	private void scheduleBalanceTasks()
	{
		logger.debug("scheduleBalanceTasks: Start schedule balance tasks");
		logger.trace("scheduleBalanceTasks: apisSettingsSet=[" + apisSettingsSet + "]");
		for (ApiSettings apiSettings : apisSettingsSet)
		{
			logger.trace("scheduleBalanceTasks: apiSettings[" + apiSettings.getApiName() + "]=[" + apiSettings + "]");
			logger.trace("scheduleBalanceTasks: apiSettings.isIntervalRestrictionEnabled()=[" + apiSettings.isIntervalRestrictionEnabled() + "]");
			logger.trace("scheduleBalanceTasks: queueHost=[" + queueHost + "]...queuePort=[" + queuePort + "]");
			if (apiSettings.isIntervalRestrictionEnabled())
			{
				// interval
				scheduleIntervalReset(queueHost, queuePort, properties, apiSettings.getApiName(), CALL_LIMIT_RESTRICTION_TYPE.interval,
						apiSettings.getIntervalTimeInSeconds());
			}
			logger.trace("scheduleBalanceTasks: apiSettings.isDailyRestrictionEnabled()=[" + apiSettings.isDailyRestrictionEnabled() + "]");
			if (apiSettings.isDailyRestrictionEnabled())
			{
				// daily
				scheduleDailyReset(queueHost, queuePort, properties, apiSettings.getApiName(), CALL_LIMIT_RESTRICTION_TYPE.daily);
			}
			logger.trace("scheduleBalanceTasks: apiSettings.isDailyRestrictionEnabled()=[" + apiSettings.isDailyRestrictionEnabled() + "]");
			if (apiSettings.isMonthlyRestrictionEnabled())
			{
				// monthly
				scheduleMonthlyReset(queueHost, queuePort, properties, apiSettings.getApiName(), CALL_LIMIT_RESTRICTION_TYPE.monthly);
			}
		}
		//
		logger.debug("scheduleBalanceTasks: exiting schedule balance tasks");
	}

	private void scheduleIntervalReset(String queueHost, int queuePort, BalanceLimitsProperties balanceLimitsProperties, String apiName,
			CALL_LIMIT_RESTRICTION_TYPE callLimitType, long timeIntervalInSeconds)
	{
		logger.debug("scheduleIntervalReset: starting schedule interval reset timeIntervalInSeconds=[" + timeIntervalInSeconds + "]["
				+ TimeUnit.SECONDS + "]");
		//
		final BalanceTask intervalBalanceTask = new BalanceTask(queueHost, queuePort, balanceLimitsProperties, apiName, callLimitType);
		intervalBalanceTask.resetInitializeBalance();
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
						logger.debug("scheduleIntervalReset: scheduleRunnableAtFixedRate -> running interval reset");
						intervalBalanceTask.resetInitializeBalance();
			}
		};
		Thread thread = new Thread(runnable);
		thread.setDaemon(true);
		
		this.taskScheduleHandler.scheduleThreadAtFixedRate(thread, timeIntervalInSeconds, timeIntervalInSeconds, TimeUnit.SECONDS);
		//
		logger.debug("scheduleIntervalReset: exiting schedule interval reset");
	}

	private void scheduleDailyReset(String queueHost, int queuePort, BalanceLimitsProperties balanceLimitsProperties, String apiName,
			CALL_LIMIT_RESTRICTION_TYPE callLimitType)
	{
		logger.debug("scheduleDailyReset: apiName=[" + apiName + "] queueHost=[" + queueHost + "]queuePort=[" + queuePort + "]");
		//
		final BalanceTask dailyBalanceTask = new BalanceTask(queueHost, queuePort, balanceLimitsProperties, apiName, callLimitType);
		//
		logger.debug("scheduleDailyReset: dailyBalanceTask has been created");
		/*
		 * initializing the balance keys at launching!
		 */
		dailyBalanceTask.resetInitializeBalance();
		this.taskScheduleHandler.scheduleDailyTask(dailyBalanceTask, null, DateUtils.getTomorrowMidnight(),
				apiName+"DailyBalanceTask_" + System.currentTimeMillis());
		//
		logger.debug("scheduleDailyReset: taskScheduleHandler.scheduleDailyTask has been instantiated");
	}

	private void scheduleMonthlyReset(String queueHost, int queuePort, BalanceLimitsProperties balanceLimitsProperties, String apiName,
			CALL_LIMIT_RESTRICTION_TYPE callLimitType)
	{
		logger.debug("scheduleMonthlyReset: apiName=[" + apiName + "] queueHost=[" + queueHost + "]queuePort=[" + queuePort + "]");
		//
		final BalanceTask monthlyBalanceTask = new BalanceTask(queueHost, queuePort, balanceLimitsProperties, apiName, callLimitType);
		/*
		 * initializing the balance keys at launching!
		 */
		monthlyBalanceTask.resetInitializeBalance();
		this.taskScheduleHandler.scheduleMonthlyTask(monthlyBalanceTask, null, DateUtils.getFirstDayOfMonthMidnight(),
				apiName+"MonthlyBalanceTask_" + System.currentTimeMillis());
		//
		logger.debug("scheduleMonthlyReset: taskScheduleHandler.scheduleMonthlyTask has been instantiated");
	}

	public void incrementKey(String keyName)
	{
		logger.debug("incrementKey: Incrementing keyName=[" + keyName + "]");
		this.queuerPoolHandler.incr(keyName);
	}

	public void decrementKey(String keyName)
	{
		logger.debug("decrementKey: Decrementing keyName=[" + keyName + "]");
		this.queuerPoolHandler.decr(keyName);
	}

	public void incrementKeyBy(String keyName, int value)
	{
		logger.debug("incrementKeyBy: Incrementing keyName=[" + keyName + "] - value : "+ value);
		this.queuerPoolHandler.incrBy(keyName, value);
	}

	public void decrementKeyBy(String keyName, int value)
	{
		logger.debug("decrementKeyBy: Decrementing keyName=[" + keyName + "] - value : "+ value);
		this.queuerPoolHandler.decrBy(keyName, value);
	}

}