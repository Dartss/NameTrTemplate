package common.balance;

import java.util.Set;

import jsmarty.core.common.balance.model.ApiSettings;

public interface BalanceLimitsProperties {

	/**
	 * 
	 * @param reload
	 * @return
	 */
	Set<ApiSettings> getApisSettingsSet(boolean reload);
	/**
	 * 
	 * @param apiName
	 * @param ip
	 * @param callLimitType
	 * @return
	 */
	String getKeyNameByIp(String apiName, String ip, CALL_LIMIT_RESTRICTION_TYPE callLimitType);
	/**
	 * 
	 * @param apiName
	 * @param apiKey
	 * @param callLimitType
	 * @return
	 */
	String getKeyNameByApiKey(String apiName, String apiKey, CALL_LIMIT_RESTRICTION_TYPE callLimitType);
	/**
	 * 
	 * @param apiName
	 * @param apiKey
	 * @param ip
	 * @param callLimitType
	 * @return
	 */
	String getKeyNameByApiKeyAndIp(String apiName, String apiKey, String ip, CALL_LIMIT_RESTRICTION_TYPE callLimitType);

}