package common.balance.tasks;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import jsmarty.core.common.balance.BalanceConstants;
import jsmarty.core.common.balance.BalanceLimitsProperties;
import jsmarty.core.common.balance.BalancePartitionHelper;
import jsmarty.core.common.balance.CALL_LIMIT_RESTRICTION_TYPE;
import jsmarty.core.common.balance.model.ApiKeyVO;
import jsmarty.core.common.balance.model.ApiSettings;
import jsmarty.core.common.queuer.QueuerPoolHandler;
import jsmarty.core.common.queuer.QueuerPoolHandlerImpl;
import jsmarty.core.common.scheduling.quartz.Task;
import jsmarty.core.common.scheduling.quartz.TaskScheduleHandler;
import jsmarty.core.common.utils.NumberUtils;

public class BalanceTask implements Task{

	private Set<ApiSettings> apisSettingsSet;
	private String apiName;
	private CALL_LIMIT_RESTRICTION_TYPE restrictionType;
	private QueuerPoolHandler queuerPoolHandler;
	private BalanceLimitsProperties balanceLimitsProperties;
	private Random random;
	private TaskScheduleHandler taskScheduleHandler;


	public BalanceTask(String queueHost, int queuePort, BalanceLimitsProperties balanceLimitsProperties, String apiName, CALL_LIMIT_RESTRICTION_TYPE callLimitType){
		this.apiName = apiName;
		this.restrictionType = callLimitType;
		this.queuerPoolHandler = new QueuerPoolHandlerImpl(queueHost, queuePort);
		this.balanceLimitsProperties = balanceLimitsProperties;
		this.random = new Random();
	}

	public void resetInitializeBalance() {
		this.apisSettingsSet = balanceLimitsProperties.getApisSettingsSet(false);


		for (ApiSettings apiSettings : apisSettingsSet){

			if(this.apiName.equals(apiSettings.getApiName())){

				if(apiSettings.isIntervalPartitioned()){
					if(this.taskScheduleHandler == null){
						this.taskScheduleHandler = new TaskScheduleHandler();
					}else{
						this.taskScheduleHandler.shutDownScheduler();
						this.taskScheduleHandler = new TaskScheduleHandler();
					}					
				}

				resetBalance(apiName, restrictionType, apiSettings);
			}
		}
	}

	private void resetBalance(String apiName, CALL_LIMIT_RESTRICTION_TYPE restrictionType, ApiSettings apiSettings){

		if(CALL_LIMIT_RESTRICTION_TYPE.interval.equals(restrictionType)){
			if(apiSettings.isIntervalPerIp()){
				for(String ip : apiSettings.getHostsList()){
					final String key = balanceLimitsProperties.getKeyNameByIp(apiName, ip, restrictionType);
					long value = apiSettings.getMaxIntervalCallsNumber();
					if(apiSettings.isIntervalRandomized()){
						value = NumberUtils.getRandomInt(apiSettings.getMinIntervalRandom(), apiSettings.getMaxIntervalRandom());
					}
					queuerPoolHandler.set(key, value+"");	
					if (apiSettings.isIntervalPartitioned()){
						final long partition = BalancePartitionHelper.getBalancePartitionInTimeRange(value, apiSettings.getIntervalTimeInSeconds(), apiSettings.getPartitionTimeRangeInSeconds());

						Runnable runnable = new Runnable() {
							
							@Override
							public void run() {
								queuerPoolHandler.set(key+BalanceConstants.PARTITION_SUFFIX, partition+"");
							}
						};
						Thread thread = new Thread(runnable);
						thread.setDaemon(true);
						
						this.taskScheduleHandler.scheduleThreadAtFixedRate(thread, 0, apiSettings.getPartitionTimeRangeInSeconds(), TimeUnit.SECONDS);						
					}
				}
			}else if(apiSettings.isIntervalPerKey()){
				for(ApiKeyVO apiKey : apiSettings.getApiKeysList()){
					if (apiKey.isCommonForAllIps()){
						//							apiKeyIps_keysNames.put(COMMON_FOR_ALL_HOSTS, propertiesClass.getIntervalKeyNameByApiKey(apiKey.getApiKeyName()));
						final String key = balanceLimitsProperties.getKeyNameByApiKey(apiName, apiKey.getApiKeyName(), restrictionType);
						long value = apiSettings.getMaxIntervalCallsNumber();
						if(apiSettings.isIntervalRandomized()){
							value = NumberUtils.getRandomInt(apiSettings.getMinIntervalRandom(), apiSettings.getMaxIntervalRandom());
						}
						queuerPoolHandler.set(key, value+"");
						if (apiSettings.isIntervalPartitioned()){
							final long partition = BalancePartitionHelper.getBalancePartitionInTimeRange(value, apiSettings.getIntervalTimeInSeconds(), apiSettings.getPartitionTimeRangeInSeconds());
							
							Runnable runnable = new Runnable() {
								
								@Override
								public void run() {
									queuerPoolHandler.set(key+BalanceConstants.PARTITION_SUFFIX, partition+"");
								}
							};
							Thread thread = new Thread(runnable);
							thread.setDaemon(true);
							
							this.taskScheduleHandler.scheduleThreadAtFixedRate(thread, 0, apiSettings.getPartitionTimeRangeInSeconds(), TimeUnit.SECONDS);						
						}
					}else{
						for(String ip : apiKey.getHostsList()){
							String key = balanceLimitsProperties.getKeyNameByApiKeyAndIp(apiName, apiKey.getApiKeyName(), ip, restrictionType);
							long value = apiSettings.getMaxIntervalCallsNumber();
							if(apiSettings.isIntervalRandomized()){
								value = NumberUtils.getRandomInt(apiSettings.getMinIntervalRandom(), apiSettings.getMaxIntervalRandom());
							}
							queuerPoolHandler.set(key, value+"");	
						}
					}
				}
			}

		}

		if(CALL_LIMIT_RESTRICTION_TYPE.daily.equals(restrictionType)){

			if(apiSettings.isDailyPerIp()){
				for(String ip : apiSettings.getHostsList()){
					String key = balanceLimitsProperties.getKeyNameByIp(apiName, ip, restrictionType);
					if(queuerPoolHandler.get(key) == null){
						long value = apiSettings.getMaxDailyCallsNumber();
						if(apiSettings.isDailyRandomized()){
							value = NumberUtils.getRandomInt(apiSettings.getMinDailyRandom(), apiSettings.getMaxDailyRandom());
						}
						this.queuerPoolHandler.set(key, value+"");
					}
				}
			}else if(apiSettings.isDailyPerKey()){
				for(ApiKeyVO apiKey : apiSettings.getApiKeysList()){
					if (apiKey.isCommonForAllIps()){
						//							apiKeyIps_keysNames.put(COMMON_FOR_ALL_HOSTS, propertiesClass.getDailyKeyNameByApiKey(apiKey.getApiKeyName()));
						String key = balanceLimitsProperties.getKeyNameByApiKey(apiName, apiKey.getApiKeyName(), restrictionType);
						if(queuerPoolHandler.get(key) == null){
							long value = apiSettings.getMaxDailyCallsNumber();
							if(apiSettings.isDailyRandomized()){
								value = NumberUtils.getRandomInt(apiSettings.getMinDailyRandom(), apiSettings.getMaxDailyRandom());
							}
							this.queuerPoolHandler.set(key, value+"");
						}
					}else{
						for(String ip : apiKey.getHostsList()){
							String key = balanceLimitsProperties.getKeyNameByApiKeyAndIp(apiName, apiKey.getApiKeyName(), ip, restrictionType);
							if(queuerPoolHandler.get(key) == null){
								long value = apiSettings.getMaxDailyCallsNumber();
								if(apiSettings.isDailyRandomized()){
									value = NumberUtils.getRandomInt(apiSettings.getMinDailyRandom(), apiSettings.getMaxDailyRandom());
								}
								this.queuerPoolHandler.set(key, value+"");
							}
						}
					}
				}
			}	
		}

		if(CALL_LIMIT_RESTRICTION_TYPE.monthly.equals(restrictionType)){

			if(apiSettings.isMonthlyPerIp()){
				for(String ip : apiSettings.getHostsList()){
					String key = balanceLimitsProperties.getKeyNameByIp(apiName, ip, restrictionType);
					if(queuerPoolHandler.get(key) == null){
						long value = apiSettings.getMaxMonthlyCallsNumber();
						if(apiSettings.isMonthlyRandomized()){
							value = NumberUtils.getRandomInt(apiSettings.getMinMonthlyRandom(), apiSettings.getMaxMonthlyRandom());
						}
						this.queuerPoolHandler.set(key, value+"");
					}
				}
			}else if(apiSettings.isMonthlyPerKey()){
				for(ApiKeyVO apiKey : apiSettings.getApiKeysList()){
					if (apiKey.isCommonForAllIps()){
						//							apiKeyIps_keysNames.put(COMMON_FOR_ALL_HOSTS, propertiesClass.getMonthlyKeyNameByApiKey(apiKey.getApiKeyName()));
						String key = balanceLimitsProperties.getKeyNameByApiKey(apiName, apiKey.getApiKeyName(), restrictionType);
						if(queuerPoolHandler.get(key) == null){
							long value = apiSettings.getMaxMonthlyCallsNumber();
							if(apiSettings.isMonthlyRandomized()){
								value = NumberUtils.getRandomInt(apiSettings.getMinMonthlyRandom(), apiSettings.getMaxMonthlyRandom());
							}
							this.queuerPoolHandler.set(key, value+"");
						}
					}else{
						for(String ip : apiKey.getHostsList()){
							String key = balanceLimitsProperties.getKeyNameByApiKeyAndIp(apiName, apiKey.getApiKeyName(), ip, restrictionType);
							if(queuerPoolHandler.get(key) == null){
								long value = apiSettings.getMaxMonthlyCallsNumber();
								if(apiSettings.isMonthlyRandomized()){
									value = NumberUtils.getRandomInt(apiSettings.getMinMonthlyRandom(), apiSettings.getMaxMonthlyRandom());
								}
								this.queuerPoolHandler.set(key, value+"");
							}
						}
					}
				}
			}
		}	

	}

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		resetInitializeBalance();
	}

}