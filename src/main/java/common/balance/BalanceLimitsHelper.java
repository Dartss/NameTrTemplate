package common.balance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jsmarty.core.common.balance.model.ApiKeyVO;
import jsmarty.core.common.balance.model.ApiKeys;
import jsmarty.core.common.balance.model.ApiSettings;
import jsmarty.core.common.balance.model.AvailableApisCalls;
import jsmarty.core.common.queuer.QueuerPoolHandlerImpl;
import jsmarty.core.dataParser.translator.model.TranslationVO;

public class BalanceLimitsHelper {

	public QueuerPoolHandlerImpl queuerPoolHandler; 
	private String apiName;

	public BalanceLimitsHelper(String queueHost, int queuePort){
		queuerPoolHandler = new QueuerPoolHandlerImpl(queueHost, queuePort);
	}

	public AvailableApisCalls mergeAndCombineAllAvailableApisCalls (Map<String, AvailableApisCalls> availableApisCallsPerCallLimitType){
		AvailableApisCalls availableApisCallsCurrent = null;
		AvailableApisCalls availableApisCalls = null;
		int i=0;
		for (Entry entry : availableApisCallsPerCallLimitType.entrySet()){
			availableApisCallsCurrent = (AvailableApisCalls) entry.getValue();
			if(i==0){
				availableApisCalls = availableApisCallsCurrent;
			}else{
				availableApisCalls = mergeAndCombineTwoAvailableApisCalls(availableApisCallsCurrent, availableApisCalls);
			}
			i++;
		}

		return availableApisCalls;
	}

	public AvailableApisCalls mergeAndCombineTwoAvailableApisCalls (AvailableApisCalls availableApisCallsA, AvailableApisCalls availableApisCallsB){
		AvailableApisCalls mergedAvailableApisCalls = new AvailableApisCalls();
		Map<String, ApiKeys> mergedAvailableApisKeysPerIp = new HashMap<>();
		ApiKeys mergedApiKeys = null;
		Map<String, ApiKeyVO> mergedApiKeysMap;
		int globalTotalAvailableCallsNumber = 0;

		for(Entry ipApisKeysA : availableApisCallsA.getAvailableApisKeysPerIp().entrySet()){
			mergedApiKeysMap = new HashMap<>();
			int ipTotalAvailableCallsNumber = 0;
			/*
			 * for each ip in A - get the list of apikeys
			 */
			String ipA = (String) ipApisKeysA.getKey();
			ApiKeys apiKeysA = (ApiKeys) ipApisKeysA.getValue();
			//			ApiKeys apiKeysB = (ApiKeys) entryA;

			/*
			 * if B does not contain this ip - we automatically add the ip to the merged object
			 */

			if(!availableApisCallsB.getAvailableApisKeysPerIp().containsKey(ipA)){
				mergedAvailableApisKeysPerIp.put(ipA, apiKeysA);
				//				availableApisCalls.getAvailableApisKeysPerIp().put(ipA, apiKeys);
				ipTotalAvailableCallsNumber=apiKeysA.getTotalAvailableCallsNumber();
			}else{
				/*
				 * if the ip is found in both A & B - then we should loop over all the api keys of A and check if they are found in B 
				 */
				for (Entry entry : apiKeysA.getApiKeysMap().entrySet()){
					int apiKeyAvailableCallsNumber = 0;
					String apiKey = (String) entry.getKey();
					ApiKeyVO apiKeyVo = (ApiKeyVO) entry.getValue();

					/*
					 * if B does not contain apikey of A - we automatically add it to the merged map under the respective ip key 
					 */
					if(!availableApisCallsB.getAvailableApisKeysPerIp().get(ipA).getApiKeysMap().containsKey(apiKey)){
						mergedApiKeysMap.put(apiKey, apiKeyVo);
						apiKeyAvailableCallsNumber=apiKeyVo.getAvailableCalls();
					}else{
						/*
						 * if the apikey is found in both A & B - we should consider the minimum calls number - we should group both keys queues names
						 * 
						 */

						apiKeyAvailableCallsNumber = Math.min(apiKeyVo.getAvailableCalls(), availableApisCallsB.getAvailableApisKeysPerIp().get(ipA).getApiKeysMap().get(apiKey).getAvailableCalls());
						apiKeyVo.setAvailableCalls(apiKeyAvailableCallsNumber);
						apiKeyVo.getKeysQueuesNames().add(availableApisCallsB.getAvailableApisKeysPerIp().get(ipA).getApiKeysMap().get(apiKey).getKeysQueuesNames().get(0));
						mergedApiKeysMap.put(apiKey, apiKeyVo);
					}
					ipTotalAvailableCallsNumber+=apiKeyAvailableCallsNumber;
				}


				// checking the apiKeys that are found under same ip in B but not found in A - adding them to the respective map - and incrementing the ip total available calls number
				for (Entry entry : availableApisCallsB.getAvailableApisKeysPerIp().get(ipA).getApiKeysMap().entrySet()){
					String apiKeyB = (String) entry.getKey();
					ApiKeyVO apiKeyVo = (ApiKeyVO) entry.getValue();

					if (!apiKeysA.getApiKeysMap().containsKey(apiKeyB)){
						mergedApiKeysMap.put(apiKeyB, apiKeyVo);
						ipTotalAvailableCallsNumber+=apiKeyVo.getAvailableCalls();
					}
				}
				//

				mergedApiKeys = new ApiKeys();
				mergedApiKeys.setApiKeysMap(mergedApiKeysMap);
				mergedApiKeys.setTotalAvailableCallsNumber(ipTotalAvailableCallsNumber);
				//				mergedAvailableApisKeysPerIp.put(ipA, mergedApiKeys);
			}

			globalTotalAvailableCallsNumber+=ipTotalAvailableCallsNumber;
			mergedAvailableApisCalls.getAvailableApisKeysPerIp().put(ipA,mergedApiKeys);
		}

		/// checking the ips available in B but not available in A - adding them to merged map and incrementing the global total available calls.
		for(Entry entryB : availableApisCallsB.getAvailableApisKeysPerIp().entrySet()){
			String ipB = (String) entryB.getKey();
			ApiKeys apiKeysB = (ApiKeys) entryB.getValue();

			if(!availableApisCallsA.getAvailableApisKeysPerIp().containsKey(ipB)){
				mergedAvailableApisCalls.getAvailableApisKeysPerIp().put(ipB, apiKeysB);
				globalTotalAvailableCallsNumber+=apiKeysB.getTotalAvailableCallsNumber();
			}
		}
		///
		mergedAvailableApisCalls.setTotalAvailableCalls(globalTotalAvailableCallsNumber);

		return mergedAvailableApisCalls;
	}	

	/**
	 * 
	 * @param apiSettings
	 * @param callLimitType
	 * @param propertiesClass MUST HAVE IMPLEMENTED "CallsLimitsProperties" Interface
	 * @param activeAdaptors
	 * @return
	 */
	public AvailableApisCalls getAvailableApisCalls(ApiSettings apiSettings, CALL_LIMIT_RESTRICTION_TYPE callLimitType, BalanceLimitsProperties propertiesClass, Set<String> activeAdaptors){
		apiName = apiSettings.getApiName();
		/*
		 * queue (redis) keys names
		 * per ip/apikey
		 */
		Map<String, String> ips_keysNames = new HashMap<>();
		Map<String, List<Map<String, String>>> apiKeys_keysNames = new HashMap<>();
		Map<String, String> keyname_key = new HashMap<>();
		Map<String, String> partitionKeys = new HashMap<>();

		switch(callLimitType){
		case concurrent:
			if(apiSettings.isConcurrentRestrictionEnabled()){
				if(apiSettings.isConcurrentPerIp()){
					for(String ip : apiSettings.getHostsList()){
						ips_keysNames.put(ip, propertiesClass.getKeyNameByIp(apiName, ip, callLimitType));
					}
				}else if(apiSettings.isConcurrentPerKey()){
					List list;
					Map<String, String> apiKeyIps_keysNames;
					for(ApiKeyVO apiKey : apiSettings.getApiKeysList()){
						list = new ArrayList<>();
						if (apiKey.isCommonForAllIps()){
							list = new ArrayList<>();
							apiKeyIps_keysNames = new HashMap<>();
							apiKeyIps_keysNames.put(BalanceConstants.API_KEY_COMMON_FOR_ALL_HOSTS, propertiesClass.getKeyNameByApiKey(apiName, apiKey.getApiKeyName(), callLimitType));
							list.add(apiKeyIps_keysNames);
							apiKeys_keysNames.put(apiKey.getApiKeyName(), list);
							keyname_key.put(apiKey.getApiKeyName(), apiKey.getApiKey());
						}else{
							apiKeyIps_keysNames = new HashMap<>();
							for(String ip : apiKey.getHostsList()){
								apiKeyIps_keysNames.put(ip, propertiesClass.getKeyNameByApiKeyAndIp(apiName, apiKey.getApiKeyName(), ip, callLimitType));
								list.add(apiKeyIps_keysNames);
							}
							apiKeys_keysNames.put(apiKey.getApiKeyName(), list);
							keyname_key.put(apiKey.getApiKeyName(), apiKey.getApiKey());
						}
					}
				}
			}
			break;
		case interval:
			if(apiSettings.isIntervalRestrictionEnabled()){
				if(apiSettings.isIntervalPerIp()){
					for(String ip : apiSettings.getHostsList()){
						String keyName = propertiesClass.getKeyNameByIp(apiName, ip, callLimitType);
						if(apiSettings.isIntervalPartitioned()){
							String originalKeyName = keyName;
							keyName = keyName+BalanceConstants.PARTITION_SUFFIX;
							partitionKeys.put(keyName, originalKeyName);
						}
						ips_keysNames.put(ip, keyName);
					}
				}else if(apiSettings.isIntervalPerKey()){
					List list;
					Map<String, String> apiKeyIps_keysNames;
					for(ApiKeyVO apiKey : apiSettings.getApiKeysList()){
						list = new ArrayList<>();
						if (apiKey.isCommonForAllIps()){
							list = new ArrayList<>();
							apiKeyIps_keysNames = new HashMap<>();
							String keyName = propertiesClass.getKeyNameByApiKey(apiName, apiKey.getApiKeyName(), callLimitType);
							if(apiSettings.isIntervalPartitioned()){
								String originalKeyName = keyName;
								keyName = keyName+BalanceConstants.PARTITION_SUFFIX;
								partitionKeys.put(keyName, originalKeyName);
							}
							apiKeyIps_keysNames.put(BalanceConstants.API_KEY_COMMON_FOR_ALL_HOSTS, keyName);
							list.add(apiKeyIps_keysNames);
							apiKeys_keysNames.put(apiKey.getApiKeyName(), list);
							keyname_key.put(apiKey.getApiKeyName(), apiKey.getApiKey());
						}else{
							apiKeyIps_keysNames = new HashMap<>();
							for(String ip : apiKey.getHostsList()){
								String keyName = propertiesClass.getKeyNameByApiKeyAndIp(apiName, apiKey.getApiKeyName(), ip, callLimitType);
								if(apiSettings.isIntervalPartitioned()){
									String originalKeyName = keyName;
									keyName = keyName+BalanceConstants.PARTITION_SUFFIX;
									partitionKeys.put(keyName, originalKeyName);
								}
								apiKeyIps_keysNames.put(ip, keyName);
								list.add(apiKeyIps_keysNames);
							}
							apiKeys_keysNames.put(apiKey.getApiKeyName(), list);
							keyname_key.put(apiKey.getApiKeyName(), apiKey.getApiKey());
						}
					}
				}
			}
			break;
		case daily:
			if(apiSettings.isDailyRestrictionEnabled()){
				if(apiSettings.isDailyPerIp()){
					for(String ip : apiSettings.getHostsList()){
						ips_keysNames.put(ip, propertiesClass.getKeyNameByIp(apiName, ip, callLimitType));
					}
				}else if(apiSettings.isDailyPerKey()){
					List list;
					Map<String, String> apiKeyIps_keysNames;
					for(ApiKeyVO apiKey : apiSettings.getApiKeysList()){
						list = new ArrayList<>();
						if (apiKey.isCommonForAllIps()){
							list = new ArrayList<>();
							apiKeyIps_keysNames = new HashMap<>();
							apiKeyIps_keysNames.put(BalanceConstants.API_KEY_COMMON_FOR_ALL_HOSTS, propertiesClass.getKeyNameByApiKey(apiName, apiKey.getApiKeyName(), callLimitType));
							list.add(apiKeyIps_keysNames);
							apiKeys_keysNames.put(apiKey.getApiKeyName(), list);
							keyname_key.put(apiKey.getApiKeyName(), apiKey.getApiKey());
						}else{
							apiKeyIps_keysNames = new HashMap<>();
							for(String ip : apiKey.getHostsList()){
								apiKeyIps_keysNames.put(ip, propertiesClass.getKeyNameByApiKeyAndIp(apiName, apiKey.getApiKeyName(), ip, callLimitType));
								list.add(apiKeyIps_keysNames);
							}
							apiKeys_keysNames.put(apiKey.getApiKeyName(), list);
							keyname_key.put(apiKey.getApiKeyName(), apiKey.getApiKey());
						} 
					}
				}				
			}
			break;
		case monthly:
			if(apiSettings.isMonthlyRestrictionEnabled()){
				if(apiSettings.isMonthlyPerIp()){
					for(String ip : apiSettings.getHostsList()){
						ips_keysNames.put(ip, propertiesClass.getKeyNameByIp(apiName, ip, callLimitType));
					}
				}else if(apiSettings.isMonthlyPerKey()){
					List list;
					Map<String, String> apiKeyIps_keysNames;
					for(ApiKeyVO apiKey : apiSettings.getApiKeysList()){
						list = new ArrayList<>();
						if (apiKey.isCommonForAllIps()){
							list = new ArrayList<>();
							apiKeyIps_keysNames = new HashMap<>();
							apiKeyIps_keysNames.put(BalanceConstants.API_KEY_COMMON_FOR_ALL_HOSTS, propertiesClass.getKeyNameByApiKey(apiName, apiKey.getApiKeyName(), callLimitType));
							list.add(apiKeyIps_keysNames);
							apiKeys_keysNames.put(apiKey.getApiKeyName(), list);
							keyname_key.put(apiKey.getApiKeyName(), apiKey.getApiKey());
						}else{
							apiKeyIps_keysNames = new HashMap<>();
							for(String ip : apiKey.getHostsList()){
								apiKeyIps_keysNames.put(ip, propertiesClass.getKeyNameByApiKeyAndIp(apiName, apiKey.getApiKeyName(), ip, callLimitType));
								list.add(apiKeyIps_keysNames);
							}
							apiKeys_keysNames.put(apiKey.getApiKeyName(), list);
							keyname_key.put(apiKey.getApiKeyName(), apiKey.getApiKey());
						}
					}
				}
			}
		}

		/*
		 * CHECKING ACTUAL BALANCE COUNTS AND RETRIEVING AVAILABLE WORKERS - ORDERING AND SORTING THEM BY IPS AND KEYS
		 * WE SHOULD ALSO CHECK IF THE ADAPTORS OF IPS ARE ACTIVE
		 */
		String stringAvailableCallsNumber = "";
		int availableCallsNumber = 0;
		int totalAvailableCallsNumber = 0;
		AvailableApisCalls availableApiCalls = new AvailableApisCalls();
		ApiKeys apiKeys;
		Map<String, ApiKeyVO> apiKeysMap;
		String apiKey = "";
		String apiKeyName = "";
		String ip = "";
		String keyName = "";
		ApiKeyVO apiKeyVo;

		if(apiKeys_keysNames != null && !apiKeys_keysNames.isEmpty()){
			for(Entry entry : apiKeys_keysNames.entrySet()){
				apiKeyName = (String) entry.getKey();
				apiKey = keyname_key.get(apiKeyName);
				for(Map<String, String> ipKeyNameMap : apiKeys_keysNames.get(apiKeyName)){
					for(Entry entry2 : ipKeyNameMap.entrySet()){	
						ip = (String) entry2.getKey();
						keyName = (String) entry2.getValue();

						// if adaptor's ip is active...
						if(BalanceConstants.API_KEY_COMMON_FOR_ALL_HOSTS.equals(ip) || activeAdaptors.contains(ip)){

							/*
							 * Get actual balance from queue (redis)
							 */
							stringAvailableCallsNumber = this.queuerPoolHandler.get(keyName);
							availableCallsNumber = Integer.parseInt(stringAvailableCallsNumber);

							// if partition is the case, make sure the original interval count equal or greater than partition count
							if(partitionKeys.containsKey(keyName)){
								String originalIntervalKeyName = partitionKeys.get(keyName);
								// check that the interval still contain available calls
								int originalIntervalCallsCount = Integer.parseInt(this.queuerPoolHandler.get(originalIntervalKeyName));
								if(originalIntervalCallsCount < availableCallsNumber){
									availableCallsNumber = originalIntervalCallsCount;
								}
							}

							// get correspondent list of hosts for this apikey;
							// TODO This list retrieval was added later,,, we could have directly retrieve the same apikeyvo object and set the available calls number... 
							apiKeyVo = new ApiKeyVO(apiKey, apiName, ip, availableCallsNumber);
							for(ApiKeyVO apiKeyVo2 : apiSettings.getApiKeysList()){
								if(apiKeyVo2.getApiKey().equals(apiKey)){
									List<String> hostsList = new ArrayList<>();
									for(String host : apiKeyVo2.getHostsList()){
										// keeping only the ips whose adaptors are active!
										if(activeAdaptors.contains(host)){
											hostsList.add(host);
										}
									}
									apiKeyVo.setHostsList(hostsList);
									break;
								}
							}

							apiKeyVo.getKeysQueuesNames().add(keyName);
							// if partition; add the original interval key name, so the manager would be able to inc/decrement it.
							if(partitionKeys.containsKey(keyName)){
								apiKeyVo.getKeysQueuesNames().add(partitionKeys.get(keyName));
							}

							if(availableApiCalls.getAvailableApisKeysPerIp() == null){
								availableApiCalls.setAvailableApisKeysPerIp(new HashMap<String, ApiKeys>());
							}
							if(availableApiCalls.getAvailableApisKeysPerIp().containsKey(ip)){
								availableApiCalls.getAvailableApisKeysPerIp().get(ip).getApiKeysMap().put(apiKeyName, apiKeyVo);
							}else{
								apiKeys = new ApiKeys();
								apiKeysMap = new HashMap<>();
								apiKeysMap.put(apiKeyName, apiKeyVo);
								apiKeys.setApiKeysMap(apiKeysMap);
								availableApiCalls.getAvailableApisKeysPerIp().put(ip, apiKeys);
							}
							totalAvailableCallsNumber+=availableCallsNumber;
						}
					}
				}
			}
		}
		if(ips_keysNames != null && !ips_keysNames.isEmpty()){
			for (Entry entry : ips_keysNames.entrySet()){
				ip = (String) entry.getKey();
				keyName = (String) entry.getValue();

				// if adaptor's ip is active...
				if(BalanceConstants.API_KEY_COMMON_FOR_ALL_HOSTS.equals(ip) || activeAdaptors.contains(ip)){


					stringAvailableCallsNumber = this.queuerPoolHandler.get(keyName);
					availableCallsNumber = Integer.parseInt(stringAvailableCallsNumber);

					// if partition is the case, make sure the original interval count equal or greater than partition count
					if(partitionKeys.containsKey(keyName)){
						String originalIntervalKeyName = partitionKeys.get(keyName);
						// check that the interval still contain available calls

						int originalIntervalCallsCount = Integer.parseInt(this.queuerPoolHandler.get(originalIntervalKeyName));

						if(originalIntervalCallsCount < availableCallsNumber){
							availableCallsNumber = originalIntervalCallsCount;
						}
					}

					apiKeyVo = new ApiKeyVO("", apiName, ip, availableCallsNumber);
					apiKeyVo.getKeysQueuesNames().add(keyName);

					// if partition; add the original interval key name, so the manager would be able to inc/decrement it.
					if(partitionKeys.containsKey(keyName)){
						apiKeyVo.getKeysQueuesNames().add(partitionKeys.get(keyName));
					}

					if(availableApiCalls.getAvailableApisKeysPerIp() == null){
						availableApiCalls.setAvailableApisKeysPerIp(new HashMap<String, ApiKeys>());
					}

					apiKeys = new ApiKeys();
					apiKeysMap = new HashMap<>();
					/*
					 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					 * TODO
					 * investigate this apikey below ?!!!!!!!
					 * 
					 */
					apiKeysMap.put(apiKey, apiKeyVo);
					apiKeys.setApiKeysMap(apiKeysMap);

					availableApiCalls.getAvailableApisKeysPerIp().put(ip, apiKeys);
					totalAvailableCallsNumber+=availableCallsNumber;

				}
			}
		}
		availableApiCalls.setTotalAvailableCalls(totalAvailableCallsNumber);

		return availableApiCalls;
	}

}